package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.CidadeNaoEncontradoException;
import com.backend.ecommerce.domain.exception.EstadoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.model.Cidade;
import com.backend.ecommerce.domain.model.Estado;
import com.backend.ecommerce.domain.repository.CidadeRepository;
import com.backend.ecommerce.domain.repository.EstadosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável pela gestão de cidades, incluindo persistência,
 * validações de integridade e associação com estados.
 */
@Service
public class CidadeService {

    private static final String MSG_CIDADE_EM_USO = "Cidade do código %d não pode ser removida, pois está em uso.";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    /**
     * Salva uma nova cidade, garantindo que o estado informado esteja válido e associado corretamente.
     *
     * @param cidade Objeto contendo os dados da cidade.
     * @return Cidade persistida no banco de dados.
     */
    @Transactional
    public Cidade salvar(@Valid Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoService.buscarOuFalhar(estadoId); // Garante que o estado exista

        cidade.setEstado(estado); // Associação explícita com estado persistido

        return cidadeRepository.save(cidade);
    }

    /**
     * Remove uma cidade pelo ID, tratando erros de integridade referencial e ausência da entidade.
     *
     * @param cidadeId Identificador da cidade a ser excluída.
     * @throws CidadeNaoEncontradoException Se a cidade não existir.
     * @throws EntidadeEmUsoException Se a cidade estiver vinculada a outra entidade (ex: endereço).
     */
    @Transactional
    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradoException(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }

    /**
     * Busca uma cidade por ID ou lança uma exceção se não for encontrada.
     *
     * @param cidadeId Identificador da cidade.
     * @return Cidade encontrada.
     */
    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(cidadeId)); // Obs: verificar se a exceção correta deveria ser CidadeNaoEncontradoException
    }
}
