package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.ProdutoInputDTO;
import com.backend.ecommerce.domain.model.enuns.Categoria;
import com.backend.ecommerce.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ProdutoModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper; // Utiliza o ModelMapper para mapear dados entre objetos DTO e entidades.

    // Este método converte um ProdutoInputDTO em uma entidade Produto.
    // O objetivo é facilitar a transformação de um objeto de entrada (DTO) para uma entidade de domínio (Produto).
    public Produto toDomainObject(ProdutoInputDTO produtoInput) {

        Produto produto = new Produto(); // Cria uma nova instância de Produto, que será preenchida com os dados do DTO.

        // Mapeia cada campo do ProdutoInputDTO para o respectivo campo do Produto.
        produto.setCodigo(produtoInput.getCodProd()); // Atribui o código do produto.
        produto.setNome(produtoInput.getNomeProd());  // Atribui o nome do produto.
        produto.setMarca(produtoInput.getMarca());    // Atribui a marca do produto.
        produto.setDescricao(produtoInput.getDescricaoProduto()); // Atribui a descrição do produto.

        // Converte o código da categoria para o tipo Categoria usando o método estático toEnum.
        // Esse método transforma o código num valor enum, garantindo que a categoria esteja sempre validada.
        produto.setCategoria(Categoria.toEnum(produtoInput.getCategoria()));

        return produto; // Retorna o objeto Produto preenchido.

    }
}
