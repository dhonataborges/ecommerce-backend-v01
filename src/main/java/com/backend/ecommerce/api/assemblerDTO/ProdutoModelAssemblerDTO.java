package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.FotoProdutoModelDTO;
import com.backend.ecommerce.api.modelDTO.ProdutoModelDTO;
import com.backend.ecommerce.domain.model.enuns.Categoria;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import com.backend.ecommerce.domain.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class ProdutoModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper; // Utiliza o ModelMapper para realizar o mapeamento de objetos entre DTO e entidades.

    @Autowired
    private ProdutoRepository produtoRepository; // Repositório de produtos, utilizado para acessar dados persistidos.

    @Autowired
    private ProdutoService produtoService; // Serviço de produtos, responsável pela lógica de negócios relacionada a produtos.

    // Método que converte um Produto para um ProdutoModelDTO, com lógica personalizada para a foto e categoria do produto.
    public ProdutoModelDTO toModel(Produto produto) {

        FotoProdutoModelDTO foto = new FotoProdutoModelDTO(); // Cria uma instância de FotoProdutoModelDTO para armazenar dados da foto do produto.

        // Verifica se o produto tem uma foto associada e preenche os dados da foto no DTO.
        if (Objects.nonNull(produto.getFotoProduto())) {
            foto.setId(produto.getFotoProduto().getId()); // Atribui o ID da foto
            foto.setNomeArquivo(produto.getFotoProduto().getNomeArquivo()); // Atribui o nome do arquivo da foto
            foto.setDescricao(produto.getFotoProduto().getDescricao()); // Atribui a descrição da foto
            foto.setContentType(produto.getFotoProduto().getContentType()); // Atribui o tipo de conteúdo da foto
            foto.setTamanho(produto.getFotoProduto().getTamanho()); // Atribui o tamanho do arquivo da foto
        }

        ProdutoModelDTO produtoDTO = new ProdutoModelDTO(); // Cria o objeto ProdutoModelDTO que será retornado.

        // Verifica se a categoria do produto não é nula antes de acessar seu código.
        // Caso contrário, utiliza uma categoria padrão.
        if (produto.getCategoria() != null) {
            produtoDTO.setCategoria(produto.getCategoria().getCodCategoria()); // Atribui o código da categoria do produto.
        } else {
            // Se a categoria for nula, define uma categoria genérica como padrão.
            Categoria categoria = (produto.getCategoria() != null) ? produto.getCategoria() : Categoria.GENERICO;
            produtoDTO.setCategoria(categoria.getCodCategoria()); // Atribui o código da categoria genérica.
        }

        // Mapeia os campos do produto para o DTO.
        produtoDTO.setId(produto.getId()); // Atribui o ID do produto.
        produtoDTO.setCodProd(produto.getCodigo()); // Atribui o código do produto.
        produtoDTO.setNomeProd(produto.getNome()); // Atribui o nome do produto.
        produtoDTO.setMarca(produto.getMarca()); // Atribui a marca do produto.
        produtoDTO.setDescricaoProduto(produto.getDescricao()); // Atribui a descrição do produto.
        produtoDTO.setDescriCatedoria(produto.getCategoria().getDescricao()); // Atribui a descrição da categoria do produto.

        produtoDTO.setFoto(foto); // Atribui o DTO da foto ao produtoDTO.

        return produtoDTO; // Retorna o ProdutoModelDTO preenchido.
    }

    // Método que converte uma lista de produtos em uma lista de ProdutoModelDTO.
    // Ele utiliza um stream para iterar sobre os produtos e aplicar o mapeamento de cada um para o DTO correspondente.
    public List<ProdutoModelDTO> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto)) // Mapeia cada produto para um ProdutoModelDTO.
                .collect(Collectors.toList()); // Coleta todos os elementos mapeados em uma lista.
    }

}