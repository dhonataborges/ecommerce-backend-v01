package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.*;
import com.backend.ecommerce.domain.model.CatalogoProduto;
import com.backend.ecommerce.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CatalogoProdutoModelAssemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    // Injeção do assembler para conversão de objetos ProdutoDoPedidoModelDTO
    @Autowired
    private ProdutoDoPedidoModelAssemblerDTO produtoModelAssemblerDTO;

    /**
     * Este método é responsável por converter um objeto do tipo CatalogoProduto para um objeto do tipo CatalogoProdutoModelDTO.
     * Ele usa o ModelMapper para a conversão e faz o mapeamento de diferentes entidades como Produto, Estoque e FotoProduto.
     *
     * @param catalogoProduto Objeto do tipo CatalogoProduto a ser convertido.
     * @return Retorna o objeto CatalogoProdutoModelDTO convertido.
     */
    public CatalogoProdutoModelDTO toModel(CatalogoProduto catalogoProduto) {

        // Cria um DTO para a FotoProduto associado ao CatalogoProduto
        FotoProdutoModelDTO fotoDTO = new FotoProdutoModelDTO();

        // Verifica se o Produto do Estoque possui uma FotoProduto antes de mapear
        if (Objects.nonNull(catalogoProduto.getEstoque().getProduto().getFotoProduto())) {
            fotoDTO.setId(catalogoProduto.getEstoque().getProduto().getFotoProduto().getId());
            fotoDTO.setNomeArquivo(catalogoProduto.getEstoque().getProduto().getFotoProduto().getNomeArquivo());
            fotoDTO.setDescricao(catalogoProduto.getEstoque().getProduto().getFotoProduto().getDescricao());
            fotoDTO.setContentType(catalogoProduto.getEstoque().getProduto().getFotoProduto().getContentType());
            fotoDTO.setTamanho(catalogoProduto.getEstoque().getProduto().getFotoProduto().getTamanho());
        }

        // Cria um DTO para o Produto associado ao Estoque
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO();
        produtoDTO.setId(catalogoProduto.getEstoque().getProduto().getId());
        produtoDTO.setCodProd(catalogoProduto.getEstoque().getProduto().getCodigo());
        produtoDTO.setNomeProd(catalogoProduto.getEstoque().getProduto().getNome());
        produtoDTO.setMarca(catalogoProduto.getEstoque().getProduto().getMarca());
        produtoDTO.setDescricaoProduto(catalogoProduto.getEstoque().getProduto().getDescricao());
        produtoDTO.setCategoria(catalogoProduto.getEstoque().getProduto().getCategoria().getCodCategoria());
        produtoDTO.setDescriCatedoria(catalogoProduto.getEstoque().getProduto().getCategoria().getDescricao());
        produtoDTO.setFoto(fotoDTO);  // Define a foto do produto no DTO

        // Cria o DTO para o Estoque associado ao Produto
        EstoqueDoPedidoModelDTO estoqueDTO = new EstoqueDoPedidoModelDTO();
        estoqueDTO.setId(catalogoProduto.getEstoque().getId());
        estoqueDTO.setValorUnitario(catalogoProduto.getEstoque().getValorUnitario());
        estoqueDTO.setQuantidade(catalogoProduto.getEstoque().getQuantidade());
        estoqueDTO.setProduto(produtoDTO);  // Associa o produto no estoque

        // Cria o DTO para o CatalogoProduto
        CatalogoProdutoModelDTO catalogoProdutoDTO = new CatalogoProdutoModelDTO();
        catalogoProdutoDTO.setId(catalogoProduto.getId());
        catalogoProdutoDTO.setEstoque(estoqueDTO);  // Associa o estoque no catálogo
        catalogoProdutoDTO.setValorItemComDesconto(catalogoProduto.getValorComDesconto());  // Aplica o desconto
        catalogoProdutoDTO.setNumParcela(catalogoProduto.getNumParcela());
        catalogoProdutoDTO.setValorParcela(catalogoProduto.getValorParcela());
        catalogoProdutoDTO.setDescontoPorcento(catalogoProduto.getDescontoPorcento());  // Aplica a porcentagem de desconto

        // Retorna o DTO do CatalogoProduto
        return catalogoProdutoDTO;
    }

    /**
     * Este método é responsável por pegar uma lista de objetos CatalogoProduto e convertê-los para uma lista de objetos do tipo CatalogoProdutoModelDTO.
     *
     * @param catalogoProdutos Lista de objetos CatalogoProduto a ser convertida.
     * @return Retorna uma lista de objetos CatalogoProdutoModelDTO.
     */
    public List<CatalogoProdutoModelDTO> toCollectionModel(List<CatalogoProduto> catalogoProdutos) {
        return catalogoProdutos.stream()
                .map(this::toModel)  // Mapeia cada item da lista para o DTO correspondente
                .collect(Collectors.toList());
    }
}