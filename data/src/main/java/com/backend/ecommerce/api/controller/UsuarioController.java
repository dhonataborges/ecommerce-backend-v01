package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.UsuarioModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.UsuarioModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.UsuarioModelDTO;
import com.backend.ecommerce.api.modelDTO.input.UsuarioInputDTO;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.UsuarioNaoEncontradoException;
import com.backend.ecommerce.domain.model.Usuario;
import com.backend.ecommerce.domain.repository.UsuarioRepository;
import com.backend.ecommerce.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define a classe como um controlador REST.
@RequestMapping(value = "/usuarios")  // Define o caminho base para as rotas de usuários.
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;  // Serviço de negócios para operações relacionadas a usuários.

    @Autowired
    private UsuarioRepository usuarioRepository;  // Repositório para interagir com o banco de dados de usuários.

    @Autowired
    private UsuarioModelAssemblerDTO usuarioModelAssemblerDTO;  // Converte o modelo de domínio de usuário para DTO.

    @Autowired
    private UsuarioModelDisassemblerDTO usuarioModelDisassemblerDTO;  // Converte o DTO de entrada para o modelo de domínio de usuário.

    /**
     * Retorna todos os usuários registrados.
     * @return Lista de usuários convertida para DTO.
     */
    @GetMapping
    public List<UsuarioModelDTO> listar() {
        return usuarioModelAssemblerDTO.toCollectionModel(usuarioRepository.findAll());  // Retorna todos os usuários convertidos em DTO.
    }

    /**
     * Retorna um usuário específico pelo ID.
     * @param usuarioId ID do usuário.
     * @return Usuário convertido para DTO.
     */
    @GetMapping("/{usuarioId}")
    public UsuarioModelDTO buscar(@PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);  // Busca o usuário pelo ID.

        return usuarioModelAssemblerDTO.toModel(usuario);  // Converte o usuário para DTO e retorna.
    }

    /**
     * Adiciona um novo usuário.
     * @param usuarioInputDTO DTO contendo os dados do usuário a ser adicionado.
     * @return Usuário adicionado convertido para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Retorna o status HTTP 201 (Criado).
    public UsuarioModelDTO adicionar(@RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
        try {
            Usuario usuario = usuarioModelDisassemblerDTO.toDomainObject(usuarioInputDTO);  // Converte o DTO para o modelo de domínio.

            return usuarioModelAssemblerDTO.toModel(usuarioService.salvar(usuario));  // Salva o usuário e retorna convertido em DTO.
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Lança exceção de negócio se ocorrer algum erro.
        }
    }

    /**
     * Atualiza um usuário existente.
     * @param usuarioId ID do usuário a ser atualizado.
     * @param usuarioInputDTO DTO contendo os dados do usuário a ser atualizado.
     * @return Usuário atualizado convertido para DTO.
     */
    @PutMapping("/{usuarioId}")
    public UsuarioModelDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
        try {
            Usuario usuario = usuarioModelDisassemblerDTO.toDomainObject(usuarioInputDTO);  // Converte o DTO para o modelo de domínio.

            Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);  // Busca o usuário existente pelo ID.

            BeanUtils.copyProperties(usuario, usuarioAtual, "id");  // Copia as propriedades do usuário atualizado (exceto o ID).

            return usuarioModelAssemblerDTO.toModel(usuarioService.salvar(usuarioAtual));  // Atualiza o usuário e retorna convertido em DTO.
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Lança exceção de negócio se ocorrer erro.
        }
    }

    /**
     * Remove um usuário específico pelo ID.
     * @param usuarioId ID do usuário a ser removido.
     */
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Retorna o status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);  // Busca o usuário pelo ID.

        usuarioService.excluir(usuarioId);  // Exclui o usuário do banco de dados.
    }
}