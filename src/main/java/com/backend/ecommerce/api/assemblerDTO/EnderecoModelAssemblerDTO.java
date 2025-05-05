package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.CidadeModelDTO;
import com.backend.ecommerce.api.modelDTO.EnderecoModelDTO;
import com.backend.ecommerce.api.modelDTO.EstadoModelDTO;
import com.backend.ecommerce.api.modelDTO.UsuarioModelDTO;
import com.backend.ecommerce.domain.model.Cidade;
import com.backend.ecommerce.domain.model.Endereco;
import com.backend.ecommerce.domain.model.enuns.Permissoes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnderecoModelAssemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto Endereco para um objeto do tipo EnderecoModelDTO.
     * Ele utiliza a biblioteca ModelMapper para simplificar a conversão entre os objetos e reduzir a quantidade de código.
     *
     * Além disso, ele realiza o mapeamento das informações de um usuário e as informações de cidade e estado.
     *
     * @param endereco Objeto de entrada do tipo Endereco a ser convertido.
     * @return Retorna o objeto EnderecoModelDTO convertido.
     */
    public EnderecoModelDTO toModel(Endereco endereco) {

        // Cria o DTO de usuário
        UsuarioModelDTO usuarioDTO = new UsuarioModelDTO();

        // Verifica se o usuário associado ao endereço não é nulo
        if (endereco.getUsuario() != null) {
            // Verifica se as permissões do usuário estão definidas e mapeia as permissões
            if (endereco.getUsuario().getPermissoes() == null) {
                usuarioDTO.setCodPermicoes(Permissoes.CLIENTE.getCodigo());
                usuarioDTO.setDescriPermicoes(Permissoes.CLIENTE.getDescricao());
            } else {
                usuarioDTO.setCodPermicoes(endereco.getUsuario().getPermissoes().getCodigo());
                usuarioDTO.setDescriPermicoes(endereco.getUsuario().getPermissoes().getDescricao());
            }

            // Mapeia as informações básicas do usuário
            usuarioDTO.setId(endereco.getUsuario().getId());
            usuarioDTO.setNome(endereco.getUsuario().getNome());
            usuarioDTO.setSobrenome(endereco.getUsuario().getSobrenome());
            usuarioDTO.setEmail(endereco.getUsuario().getEmail());
        } else {
            throw new IllegalArgumentException("ERRO: Usuário retornado do banco de dados está null.");
        }

        // Cria o DTO de endereço e mapeia as informações
        EnderecoModelDTO enderecoDTO = new EnderecoModelDTO();
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setUsuario(usuarioDTO);
        enderecoDTO.setNomeDono(endereco.getNomeDono());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setRua(endereco.getRua());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());

        // Mapeia as informações de cidade e estado
        if (endereco.getCidade() != null && endereco.getCidade().getEstado() != null) {
            EstadoModelDTO estadoDTO = new EstadoModelDTO();
            CidadeModelDTO cidadeDTO = new CidadeModelDTO();

            estadoDTO.setId(endereco.getCidade().getEstado().getId());
            estadoDTO.setNome(endereco.getCidade().getEstado().getNome());

            cidadeDTO.setId(endereco.getCidade().getId());
            cidadeDTO.setNome(endereco.getCidade().getNome());
            cidadeDTO.setEstado(estadoDTO);

            enderecoDTO.setCidade(cidadeDTO);
        } else {
            // Log de erro caso cidade ou estado estejam nulos
            System.out.println("Cidade ou Estado nulos no endereço com id: " + endereco.getId());
        }

        return enderecoDTO;
    }

    /**
     * Este método converte uma lista de objetos Endereco para uma lista de objetos do tipo EnderecoModelDTO.
     * Utiliza o método `toModel` para fazer o mapeamento de cada item da lista.
     *
     * @param enderecos Lista de objetos Endereco a ser convertida.
     * @return Lista de objetos EnderecoModelDTO convertidos.
     */
    public List<EnderecoModelDTO> toCollectionModel(List<Endereco> enderecos) {
        return enderecos.stream()
                .map(endereco -> toModel(endereco))  // Chama o método toModel para cada objeto
                .collect(Collectors.toList());  // Coleta os resultados em uma lista
    }
}