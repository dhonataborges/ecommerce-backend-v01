package com.backend.ecommerce.api.assemblerDTO;

import com.backend.ecommerce.api.modelDTO.UsuarioModelDTO;
import com.backend.ecommerce.domain.model.*;
import com.backend.ecommerce.domain.model.enuns.Categoria;
import com.backend.ecommerce.domain.model.enuns.Permissoes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UsuarioModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    // Converte a entidade Usuario para o DTO de saída UsuarioModelDTO
    public UsuarioModelDTO toModel(Usuario usuario) {

        UsuarioModelDTO usuarioDTO = new UsuarioModelDTO();

        // Se a permissão for nula, assume que o usuário é um CLIENTE
        if (usuario.getPermissoes() == null) {
            usuarioDTO.setCodPermicoes(Permissoes.CLIENTE.getCodigo());
        } else {
            usuarioDTO.setCodPermicoes(usuario.getPermissoes().getCodigo());
        }

        // Copia os demais dados do usuário para o DTO
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setSobrenome(usuario.getSobrenome());
        usuarioDTO.setEmail(usuario.getEmail());

        // Define a descrição da permissão (ex: ADMINISTRADOR, CLIENTE, etc.)
        usuarioDTO.setDescriPermicoes(usuario.getPermissoes().getDescricao());

        return usuarioDTO;
    }

    // Converte uma lista de entidades Usuario para uma lista de DTOs
    public List<UsuarioModelDTO> toCollectionModel(List<Usuario> users) {
        return users.stream()
                .map(user -> toModel(user)) // Reaproveita a conversão individual
                .collect(Collectors.toList());
    }
}
