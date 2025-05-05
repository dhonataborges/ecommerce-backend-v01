package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.EstadoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.FormaPagamentoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.model.Estado;
import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.repository.EstadosRepository;
import com.backend.ecommerce.domain.repository.FormaPagamentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Serviço responsável pela gestão das formas de pagamento.
 * Oferece operações de cadastro, exclusão e consulta com validações apropriadas.
 */
@Service
public class FormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO =
            "Forma de pagamento do código %d não pode ser removida, pois está em uso.";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    /**
     * Cadastra uma nova forma de pagamento.
     *
     * @param formaPagamento Entidade a ser salva.
     * @return Forma de pagamento salva.
     */
    @Transactional
    public FormaPagamento salvar(@Valid FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    /**
     * Remove uma forma de pagamento pelo ID, tratando possíveis exceções de integridade.
     *
     * @param formaPagamentoId ID da forma de pagamento a ser removida.
     */
    @Transactional
    public void excluir(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradoException(formaPagamentoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId)
            );
        }
    }

    /**
     * Busca uma forma de pagamento pelo ID. Lança exceção se não encontrada.
     *
     * @param formaPagamentoId ID da forma de pagamento.
     * @return Forma de pagamento correspondente.
     */
    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(formaPagamentoId));
    }
}
