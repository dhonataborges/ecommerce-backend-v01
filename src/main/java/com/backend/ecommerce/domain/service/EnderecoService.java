package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.EnderecoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.EstadoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.model.Cidade;
import com.backend.ecommerce.domain.model.Endereco;
import com.backend.ecommerce.domain.model.Pedido;
import com.backend.ecommerce.domain.model.Usuario;
import com.backend.ecommerce.domain.repository.EnderecoRepository;
import com.backend.ecommerce.domain.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio relacionada a endereços de usuários.
 * Inclui operações de persistência, exclusão e associação com cidade e usuário.
 */
@Service
public class EnderecoService {

    private static final String MSG_ENDERECO_EM_USO =
            "Endereço do código %d não pode ser removido, pois está em uso.";

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CidadeService cidadeService;

    /**
     * Retorna todos os endereços associados a um determinado usuário.
     *
     * @param usuarioId ID do usuário.
     * @return Lista de endereços pertencentes ao usuário.
     */
    public List<Endereco> buscarPorCliente(Long usuarioId) {
        return enderecoRepository.buscarEnderecosPorUsuarioId(usuarioId);
    }

    /**
     * Salva um novo endereço no banco de dados, associando-o corretamente ao usuário e à cidade.
     *
     * @param endereco Objeto contendo os dados do endereço.
     * @return Endereço salvo.
     */
    @Transactional
    public Endereco salvar(@Valid Endereco endereco) {
        // Obtém o ID do usuário associado
        Long usuarioId = endereco.getUsuario().getId();

        // Busca o usuário completo com todos os dados necessários
        Usuario usuarioCompleto = usuarioRepository.buscarUsuarioCompleto(usuarioId);

        // Garante que o usuário existe
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioCompleto.getId());

        // Concatena nome e sobrenome para compor o nome do dono do endereço
        String usuarioNomeCompleto = usuario.getNome() + " " + usuario.getSobrenome();

        // Obtém o ID da cidade e garante que ela existe
        Long cidadeId = endereco.getCidade().getId();
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

        // Se o nome do dono do endereço não foi informado, define o nome do cliente como padrão
        if (endereco.getNomeDono() == null || endereco.getNomeDono().isEmpty()) {
            endereco.setNomeDono(usuarioNomeCompleto);
        }

        // Salva e retorna o endereço atualizado
        return enderecoRepository.save(endereco);
    }

    /**
     * Exclui um endereço pelo seu ID, tratando possíveis exceções de integridade e inexistência.
     *
     * @param enderecoId ID do endereço a ser removido.
     */
    @Transactional
    public void excluir(Long enderecoId) {
        try {
            enderecoRepository.deleteById(enderecoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EnderecoNaoEncontradoException(enderecoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ENDERECO_EM_USO, enderecoId)
            );
        }
    }

    /**
     * Busca um endereço por ID ou lança exceção se não for encontrado.
     *
     * @param enderecoId ID do endereço.
     * @return Endereço encontrado.
     */
    public Endereco buscarOuFalhar(Long enderecoId) {
        return enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(enderecoId)); // Corrigida a exceção lançada
    }
}