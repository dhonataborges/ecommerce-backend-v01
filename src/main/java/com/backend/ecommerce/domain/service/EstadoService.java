package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.EstadoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.model.Estado;
import com.backend.ecommerce.domain.repository.EstadosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por encapsular a lógica de negócio relacionada à entidade Estado.
 * Oferece operações de cadastro, exclusão e recuperação com validação de existência.
 */
@Service
public class EstadoService {

    private static final String MSG_ESTADO_EM_USO =
            "Estado do código %d não pode ser removido, pois está em uso.";

    @Autowired
    private EstadosRepository estadosRepository;

    /**
     * Salva ou atualiza um estado no banco de dados.
     *
     * @param estado Objeto contendo os dados do estado.
     * @return Estado salvo ou atualizado.
     */
    @Transactional
    public Estado salvar(@Valid Estado estado) {
        return estadosRepository.save(estado);
    }

    /**
     * Exclui um estado pelo seu ID, tratando exceções relacionadas a integridade referencial
     * ou inexistência do registro.
     *
     * @param estadoId ID do estado a ser excluído.
     */
    @Transactional
    public void excluir(Long estadoId) {
        try {
            estadosRepository.deleteById(estadoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId)
            );
        }
    }

    /**
     * Recupera um estado pelo seu ID ou lança exceção caso não encontrado.
     *
     * @param estadoId ID do estado.
     * @return Estado encontrado.
     */
    public Estado buscarOuFalhar(Long estadoId) {
        return estadosRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }
}