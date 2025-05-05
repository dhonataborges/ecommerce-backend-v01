package com.backend.ecommerce.api.assemblerDTO;

import com.backend.ecommerce.api.modelDTO.UsuarioModelDTO;
import com.backend.ecommerce.api.modelDTO.input.UsuarioInputDTO;
import com.backend.ecommerce.domain.model.Usuario;
import com.backend.ecommerce.domain.model.enuns.Permissoes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class UsuarioModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    // Esse método converte um DTO de entrada (UsuarioInputDTO) para a entidade de domínio (Usuario).
    // A ideia é preparar o objeto que será persistido ou processado na regra de negócio.
    // Embora o ModelMapper esteja injetado, aqui a conversão está sendo feita manualmente campo a campo.
    // Isso pode ser útil quando você precisa de mais controle, como na lógica da permissão abaixo.

    public Usuario toDomainObject(UsuarioInputDTO usuarioInputDTO) {
        Usuario usuario = new Usuario();

        // Mapeia os campos simples diretamente
        usuario.setNome(usuarioInputDTO.getNome());
        usuario.setSobrenome(usuarioInputDTO.getSobrenome());
        usuario.setEmail(usuarioInputDTO.getEmail());
        usuario.setSenha(usuarioInputDTO.getSenha());

        // Caso o código da permissão venha nulo, define como CLIENTE por padrão
        if (usuarioInputDTO.getCodPermissoes() == null) {
            usuario.setPermissoes(Permissoes.CLIENTE);
        } else {
            // Converte o código numérico para o enum correspondente
            usuario.setPermissoes(Permissoes.toEnum(usuarioInputDTO.getCodPermissoes()));
        }

        return usuario;
    }
}
