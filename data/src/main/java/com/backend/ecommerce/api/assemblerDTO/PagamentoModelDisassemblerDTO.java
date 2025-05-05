package com.backend.ecommerce.api.assemblerDTO;

import com.backend.ecommerce.api.modelDTO.input.PagamentoBoletoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoCartaoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoPixInputDTO;
import com.backend.ecommerce.core.security.crypto.CriptografiaUtils;
import com.backend.ecommerce.domain.model.DadosCartao;
import com.backend.ecommerce.domain.model.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.Normalizer;

import static com.backend.ecommerce.core.security.crypto.CriptografiaUtils.criptografar;

import com.backend.ecommerce.api.modelDTO.input.PagamentoBoletoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoCartaoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoPixInputDTO;
import com.backend.ecommerce.core.security.crypto.CriptografiaUtils;
import com.backend.ecommerce.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

import static com.backend.ecommerce.core.security.crypto.CriptografiaUtils.criptografar;
@Component
public class PagamentoModelDisassemblerDTO {

    @Autowired
    private CriptografiaUtils criptografiaUtils; // Utilitário para criptografia de dados sensíveis, como o número do cartão e CVV.

    // Converte um DTO de pagamento para o modelo de domínio Pagamento.
    public Pagamento toDomainObject(PagamentoInputDTO pagamentoInputDTO) {
        // Verifica se o DTO de pagamento é nulo, lançando uma exceção caso seja.
        if (pagamentoInputDTO == null) {
            throw new IllegalArgumentException("DTO de pagamento não pode ser nulo.");
        }

        // Obtém o tipo de pagamento e normaliza para um formato padronizado.
        String tipo = pagamentoInputDTO.getFormaPagamento() != null
                ? pagamentoInputDTO.getFormaPagamento().getTipo()
                : null;

        // Verifica se o tipo de pagamento é válido.
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de forma de pagamento inválido.");
        }

        tipo = normalizar(tipo).toUpperCase(); // Normaliza e coloca o tipo em maiúsculas.

        // Cria o objeto Pagamento e define seus valores a partir do DTO de entrada.
        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoInputDTO.getId());
        pagamento.setDataPagamento(pagamentoInputDTO.getDataPagamento());
        pagamento.setValor(pagamentoInputDTO.getValor());
        pagamento.setFormaPagamento(pagamentoInputDTO.getFormaPagamento());
        pagamento.setStatus(pagamentoInputDTO.getStatus());

        // Verifica o tipo de pagamento e realiza o mapeamento correspondente.
        if (tipo.equals("CARTAO")) {
            // Se o tipo de pagamento for "CARTÃO", mapeia os dados do cartão.
            if (pagamentoInputDTO instanceof PagamentoCartaoInputDTO) {
                PagamentoCartaoInputDTO cartaoInput = (PagamentoCartaoInputDTO) pagamentoInputDTO;

                // Criptografa os dados do cartão de crédito antes de salvar.
                pagamento.setDadosCartao(new DadosCartao());
                pagamento.getDadosCartao().setNomeTitular(cartaoInput.getNomeTitular());
                pagamento.getDadosCartao().setNumeroCartao(criptografar(cartaoInput.getNumeroCartao()));
                pagamento.getDadosCartao().setCvv(criptografar(cartaoInput.getCvv()));
                pagamento.getDadosCartao().setValidade(cartaoInput.getValidade());
            } else {
                throw new IllegalArgumentException("Dados do cartão inválidos.");
            }
        } else if (tipo.equals("PIX")) {
            // Se o tipo de pagamento for "PIX", mapeia os dados do PIX.
            if (pagamentoInputDTO instanceof PagamentoPixInputDTO) {
                PagamentoPixInputDTO pixInput = (PagamentoPixInputDTO) pagamentoInputDTO;

                pagamento.setDadosPix(new DadosPix());
                pagamento.getDadosPix().setChavePix(pixInput.getChavePix());
            } else {
                throw new IllegalArgumentException("Dados do PIX inválidos.");
            }
        } else if (tipo.equals("BOLETO")) {
            // Se o tipo de pagamento for "BOLETO", mapeia os dados do boleto.
            if (pagamentoInputDTO instanceof PagamentoBoletoInputDTO) {
                PagamentoBoletoInputDTO boletoInput = (PagamentoBoletoInputDTO) pagamentoInputDTO;

                pagamento.setDadosBoleto(new DadosBoleto());
                pagamento.getDadosBoleto().setCodigoBoleto(boletoInput.getCodigoBoleto());
            } else {
                throw new IllegalArgumentException("Dados do boleto inválidos.");
            }
        } else {
            // Caso o tipo de pagamento não seja reconhecido, lança uma exceção.
            throw new IllegalArgumentException("Tipo de pagamento desconhecido: " + tipo);
        }

        return pagamento; // Retorna o objeto Pagamento mapeado.
    }

    // Método auxiliar para normalizar uma string, removendo caracteres não ASCII.
    String normalizar(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    // Método para criptografar dados sensíveis, como o número do cartão e CVV.
    private String criptografar(String valor) {
        return criptografiaUtils.criptografar(valor);
    }
}