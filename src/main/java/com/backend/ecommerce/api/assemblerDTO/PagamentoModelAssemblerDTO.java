package com.backend.ecommerce.api.assemblerDTO;

import com.backend.ecommerce.api.modelDTO.*;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Pagamento;
import com.backend.ecommerce.domain.repository.EnderecoRepository;
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

    // Método principal para converter um objeto de domínio Pagamento em um DTO
    public PagamentoModelDTO toModel(Pagamento pagamento) {
        // Obtém o tipo da forma de pagamento
        String tipo = pagamento.getFormaPagamento().getTipo();

        // Valida se o tipo da forma de pagamento é válido
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new NegocioException("Forma de pagamento inválida");
        }

        // Normaliza a string para remover acentos e converte para maiúsculas
        tipo = normalizar(tipo).toUpperCase();

        // Verifica o tipo de pagamento e direciona para o método correspondente
        switch (tipo) {
            case "CARTAO":
                return preencherCartao(pagamento); // Processa pagamento via cartão
            case "PIX":
                return preencherPix(pagamento); // Processa pagamento via PIX
            case "BOLETO":
                return preencherBoleto(pagamento); // Processa pagamento via boleto
            default:
                throw new NegocioException("Tipo de pagamento desconhecido: " + tipo); // Caso tipo não seja reconhecido
        }
    }

    // Método para preencher DTO específico de pagamento via Cartão
    private PagamentoCartaoModelDTO preencherCartao(Pagamento pagamento) {
        // Cria o DTO de pagamento com cartão
        PagamentoCartaoModelDTO pagamentoCartaoDTO = new PagamentoCartaoModelDTO();

        // Preenche os dados comuns a todos os tipos de pagamento
        preencherBase(pagamentoCartaoDTO, pagamento);

        // Preenche dados específicos para pagamento com cartão
        pagamentoCartaoDTO.setNomeTitular(pagamento.getDadosCartao().getNomeTitular());
        pagamentoCartaoDTO.setNumeroCartaoMascarado(mascararNumero(pagamento.getDadosCartao().getNumeroCartao())); // Mascarando o número do cartão
        pagamentoCartaoDTO.setValidade(pagamento.getDadosCartao().getValidade());

        // Nunca retorna o CVV por questões de segurança
        return pagamentoCartaoDTO;
    }

    // Método para preencher DTO específico de pagamento via PIX
    private PagamentoPixModelDTO preencherPix(Pagamento pagamento) {
        // Cria o DTO de pagamento com PIX
        PagamentoPixModelDTO pagamentoPixDTO = new PagamentoPixModelDTO();

        // Preenche os dados comuns a todos os tipos de pagamento
        preencherBase(pagamentoPixDTO, pagamento);

        // Preenche dados específicos para pagamento com PIX
        pagamentoPixDTO.setChavePix(pagamento.getDadosPix().getChavePix());

        return pagamentoPixDTO;
    }

    // Método para preencher DTO específico de pagamento via Boleto
    private PagamentoBoletoModelDTO preencherBoleto(Pagamento pagamento) {
        // Cria o DTO de pagamento com boleto
        PagamentoBoletoModelDTO pagamentoBoletoDTO = new PagamentoBoletoModelDTO();

        // Preenche os dados comuns a todos os tipos de pagamento
        preencherBase(pagamentoBoletoDTO, pagamento);

        // Preenche dados específicos para pagamento com boleto
        pagamentoBoletoDTO.setCodigoBoleto(pagamento.getDadosBoleto().getCodigoBoleto());

        return pagamentoBoletoDTO;
    }

    // Método comum para preencher dados que são compartilhados entre todos os tipos de pagamento
    private void preencherBase(PagamentoModelDTO pagamentoDTO, Pagamento pagamento) {
        // Preenche os dados básicos do pagamento
        pagamentoDTO.setId(pagamento.getId());
        pagamentoDTO.setDataPagamento(pagamento.getDataPagamento());
        pagamentoDTO.setValor(pagamento.getValor());
        pagamentoDTO.setFormaPagamento(pagamento.getFormaPagamento());
        pagamentoDTO.setStatus(pagamento.getStatus());
    }

    // Método para mascarar o número do cartão, exibindo apenas os últimos 4 dígitos
    private String mascararNumero(String numeroCartao) {
        // Verifica se o número do cartão é válido
        if (numeroCartao == null || numeroCartao.length() < 4) {
            return "****"; // Retorna máscara se número do cartão for inválido
        }

        // Exibe apenas os últimos 4 dígitos do número do cartão
        String ultimos4 = numeroCartao.substring(numeroCartao.length() - 4);
        return "**** **** **** " + ultimos4;
    }

    // Método para remover acentos e caracteres especiais de uma string
    private String normalizar(String input) {
        // Normaliza a string para remover acentos e converte para uma versão ASCII
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    // Método para converter uma lista de objetos de domínio Pagamento em uma lista de DTOs
    public List<PagamentoModelDTO> toCollectionModel(List<Pagamento> pagamentos) {
        // Converte cada pagamento em um DTO e retorna a lista
        return pagamentos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}