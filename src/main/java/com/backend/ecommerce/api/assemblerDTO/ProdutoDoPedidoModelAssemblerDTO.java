package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.FotoProdutoModelDTO;
import com.backend.ecommerce.api.modelDTO.ProdutoDoPedidoModelDTO;
import com.backend.ecommerce.api.modelDTO.ProdutoModelDTO;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.model.enuns.Categoria;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import com.backend.ecommerce.domain.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProdutoDoPedidoModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper; // Utiliza o ModelMapper para facilitar o mapeamento entre entidades e DTOs.

    @Autowired
    private ProdutoRepository produtoRepository; // Repositório de produtos, responsável por acessar os dados persistidos.

    @Autowired
    private ProdutoService produtoService; // Serviço de produtos, que contém a lógica de negócios associada aos produtos.

    // Método que converte um Produto em um ProdutoDoPedidoModelDTO.
    // Este método é usado para criar o DTO necessário para representar o produto dentro de um pedido.
    public ProdutoDoPedidoModelDTO toModel(Produto produto) {

        FotoProdutoModelDTO foto = new FotoProdutoModelDTO(); // Cria o DTO para armazenar as informações da foto do produto.

        // Verifica se o produto tem uma foto associada e preenche os dados no DTO de FotoProdutoModelDTO.
        if (Objects.nonNull(produto.getFotoProduto())) {
            foto.setId(produto.getFotoProduto().getId()); // Atribui o ID da foto
            foto.setNomeArquivo(produto.getFotoProduto().getNomeArquivo()); // Atribui o nome do arquivo da foto
            foto.setDescricao(produto.getFotoProduto().getDescricao()); // Atribui a descrição da foto
            foto.setContentType(produto.getFotoProduto().getContentType()); // Atribui o tipo de conteúdo da foto
            foto.setTamanho(produto.getFotoProduto().getTamanho()); // Atribui o tamanho do arquivo da foto
        }

        ProdutoDoPedidoModelDTO produtoDTO = new ProdutoDoPedidoModelDTO(); // Cria a instância do DTO a ser retornado.

        // Mapeia os campos principais do produto para o ProdutoDoPedidoModelDTO.
        produtoDTO.setId(produto.getId()); // Atribui o ID do produto.
        produtoDTO.setCodProd(produto.getCodigo()); // Atribui o código do produto.
        produtoDTO.setNomeProd(produto.getNome()); // Atribui o nome do produto.

        produtoDTO.setFoto(foto); // Atribui o DTO de foto ao ProdutoDoPedidoModelDTO.

        return produtoDTO; // Retorna o DTO contendo as informações do produto e sua foto.
    }

    // Método que converte uma lista de produtos em uma lista de ProdutoDoPedidoModelDTO.
    // Ele utiliza o stream para iterar sobre os produtos e aplicar o mapeamento de cada um para o DTO.
    public List<ProdutoDoPedidoModelDTO> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto)) // Mapeia cada produto para um ProdutoDoPedidoModelDTO.
                .collect(Collectors.toList()); // Coleta os elementos mapeados em uma lista.
    }

}