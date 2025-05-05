
package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.UsuarioNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.model.Usuario;
import com.backend.ecommerce.domain.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Serviço responsável pela gestão de usuários. As funcionalidades incluem salvar um novo usuário, excluir
 * um usuário, e buscar um usuário pelo ID.
 */
@Service
public class UsuarioService {

    // Mensagens de erro personalizadas
    private static final String MSG_USER_EM_USO = "Usuario %d não pode ser removido, pois está em uso";

    // Repositório para interagir com o banco de dados de usuários
    @Autowired
    private UsuarioRepository userRepository;

    /**
     * Salva um novo usuário no banco de dados.
     *
     * @param user O usuário a ser salvo.
     * @return O usuário salvo.
     */
    @Transactional
    public Usuario salvar(@Valid Usuario user) {
        // Salva e retorna o usuário após persistir no banco
        return userRepository.save(user);
    }

    /**
     * Exclui um usuário do banco de dados.
     *
     * @param userId O ID do usuário a ser excluído.
     */
    @Transactional
    public void excluir(Long userId) {
        try {
            // Tenta excluir o usuário com o ID fornecido
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            // Lança uma exceção caso o usuário não seja encontrado no banco
            throw new UsuarioNaoEncontradoException(userId);
        } catch (DataIntegrityViolationException e) {
            // Lança uma exceção caso haja violação de integridade (usuário está em uso por outro sistema)
            throw new EntidadeEmUsoException(String.format(MSG_USER_EM_USO, userId));
        }
    }

    /**
     * Busca um usuário pelo ID. Caso o usuário não seja encontrado, lança uma exceção.
     *
     * @param userId O ID do usuário a ser buscado.
     * @return O usuário encontrado.
     */
    public Usuario buscarOuFalhar(Long userId) {
        // Retorna o usuário encontrado ou lança uma exceção caso não encontrado
        return userRepository.findById(userId).orElseThrow(() -> new UsuarioNaoEncontradoException(userId));
    }
}
