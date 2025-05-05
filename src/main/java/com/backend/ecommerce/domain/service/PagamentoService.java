package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.core.security.crypto.CriptografiaUtils;
import com.backend.ecommerce.domain.exception.FormaPagamentoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.PagamentoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.model.Pagamento;
import com.backend.ecommerce.domain.repository.FormaPagamentoRepository;
import com.backend.ecommerce.domain.repository.PagamentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Serviço responsável por gerenciar operações relacionadas a pagamentos.
 * Inclui funcionalidades como salvar, excluir e buscar pagamentos.
 * Além disso, criptografa dados sensíveis de pagamento, como o número do cartão e CVV.
 */
@Service
public class PagamentoService {

    // Mensagem de erro para quando um pagamento não pode ser removido devido a estar em uso
    private static final String MSG_PAGAMENTO_EM_USO = "Pagamento do codigo %d não pode ser removido, pois está em uso";

    // Repositório de pagamentos, utilizado para persistir e recuperar dados de pagamento
    @Autowired
    private PagamentoRepository pagamentoRepository;

    // Classe utilitária para criptografar dados sensíveis
    @Autowired
    private CriptografiaUtils criptografiaUtils;

    /**
     * Salva um pagamento no banco de dados.
     * Caso o pagamento seja via cartão, os dados sensíveis (número do cartão e CVV) são criptografados
     * antes de serem armazenados.
     *
     * @param pagamento Objeto que representa os dados do pagamento a ser salvo.
     * @return O pagamento salvo após persistência no banco de dados.
     */
    @Transactional
    public Pagamento salvar(@Valid Pagamento pagamento) {
        // Criptografa os dados sensíveis (número do cartão e CVV) se o pagamento for feito via cartão
        if (pagamento.getFormaPagamento().getTipo().equalsIgnoreCase("CARTAO")) {
            pagamento.getDadosCartao().setNumeroCartao(criptografiaUtils.criptografar(pagamento.getDadosCartao().getNumeroCartao()));
            pagamento.getDadosCartao().setCvv(criptografiaUtils.criptografar(pagamento.getDadosCartao().getCvv()));
        }

        // Salva o pagamento no repositório
        return pagamentoRepository.save(pagamento);
    }

    /**
     * Exclui um pagamento com base no ID fornecido.
     * Caso o pagamento não exista ou não possa ser excluído (se estiver em uso), uma exceção é lançada.
     *
     * @param pagamentoId O ID do pagamento a ser excluído.
     */
    @Transactional
    public void excluir(Long pagamentoId) {
        try {
            pagamentoRepository.deleteById(pagamentoId);
        } catch (EmptyResultDataAccessException e) {
            // Lança uma exceção personalizada quando o pagamento não for encontrado
            throw new PagamentoNaoEncontradoException(pagamentoId);
        } catch (DataIntegrityViolationException e) {
            // Lança uma exceção personalizada quando o pagamento não pode ser removido devido a estar em uso
            throw new EntidadeEmUsoException(String.format(MSG_PAGAMENTO_EM_USO, pagamentoId));
        }
    }

    /**
     * Busca um pagamento pelo ID. Caso o pagamento não seja encontrado, uma exceção será lançada.
     *
     * @param pagamentoId O ID do pagamento a ser buscado.
     * @return O pagamento encontrado.
     */
    public Pagamento buscarOuFalhar(Long pagamentoId) {
        return pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new PagamentoNaoEncontradoException(pagamentoId));
    }
}