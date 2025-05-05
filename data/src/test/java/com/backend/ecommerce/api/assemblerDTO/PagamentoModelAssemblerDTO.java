package com.backend.ecommerce.api.assemblerDTO;

import com.backend.ecommerce.api.modelDTO.PagamentoBoletoModelDTO;
import com.backend.ecommerce.api.modelDTO.PagamentoCartaoModelDTO;
import com.backend.ecommerce.api.modelDTO.PagamentoModelDTO;
import com.backend.ecommerce.api.modelDTO.PagamentoPixModelDTO;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Pagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PagamentoModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    public PagamentoModelDTO toModel(Pagamento pagamento) {
        String tipo = pagamento.getFormaPagamento().getTipo();

        if (tipo == null || tipo.trim().isEmpty()) {
            throw new NegocioException("Forma de pagamento inválida");
        }

        tipo = normalizar(tipo).toUpperCase(); // Remove acentos e converte para maiúsculas

        switch (tipo) {
            case "CARTAO":
                return preencherCartao(pagamento);
            case "PIX":
                return preencherPix(pagamento);
            case "BOLETO":
                return preencherBoleto(pagamento);
            default:
                throw new NegocioException("Tipo de pagamento desconhecido: " + tipo);
        }
    }

    private PagamentoCartaoModelDTO preencherCartao(Pagamento pagamento) {
        PagamentoCartaoModelDTO pagamentoCartaoDTO = new PagamentoCartaoModelDTO();
        preencherBase(pagamentoCartaoDTO, pagamento);
        pagamentoCartaoDTO.setNomeTitular(pagamento.getDadosCartao().getNomeTitular());
        pagamentoCartaoDTO.setNumeroCartaoMascarado(mascararNumero(pagamento.getDadosCartao().getNumeroCartao()));
        pagamentoCartaoDTO.setValidade(pagamento.getDadosCartao().getValidade());
        // Nunca retornar o CVV por segurança
        return pagamentoCartaoDTO;
    }

    private PagamentoPixModelDTO preencherPix(Pagamento pagamento) {
        PagamentoPixModelDTO pagamentoPixDTO = new PagamentoPixModelDTO();
        preencherBase(pagamentoPixDTO, pagamento);
        pagamentoPixDTO.setChavePix(pagamento.getDadosPix().getChavePix());
        return pagamentoPixDTO;
    }

    private PagamentoBoletoModelDTO preencherBoleto(Pagamento pagamento) {
        PagamentoBoletoModelDTO pagamentoBoletoDTO = new PagamentoBoletoModelDTO();
        preencherBase(pagamentoBoletoDTO, pagamento);
        pagamentoBoletoDTO.setCodigoBoleto(pagamento.getDadosBoleto().getCodigoBoleto());
        return pagamentoBoletoDTO;
    }

    // Preenche dados comuns a todos os tipos de pagamento
    private void preencherBase(PagamentoModelDTO dto, Pagamento pagamento) {
        dto.setId(pagamento.getId());
        dto.setDataPagamento(pagamento.getDataPagamento());
        dto.setValor(pagamento.getValor());
        dto.setFormaPagamento(pagamento.getFormaPagamento());
        dto.setStatus(pagamento.getStatus());
    }

    // Mascarar número do cartão (exibe apenas os últimos 4 dígitos)
    private String mascararNumero(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.length() < 4) {
            return "****";
        }
        String ultimos4 = numeroCartao.substring(numeroCartao.length() - 4);
        return "**** **** **** " + ultimos4;
    }

    // Remove acentos e caracteres especiais
    private String normalizar(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    // Converte lista de entidades para lista de DTOs
    public List<PagamentoModelDTO> toCollectionModel(List<Pagamento> pagamentos) {
        return pagamentos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
