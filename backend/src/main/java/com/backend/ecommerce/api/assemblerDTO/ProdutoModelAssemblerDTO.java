package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.FotoProdutoModelDTO;
import com.backend.ecommerce.api.modelDTO.ProdutoModelDTO;
import com.backend.ecommerce.domain.model.Categoria;
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
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    //Esse metódo é responsavel por pegar uma objeto Produto e convertelo para um objeto tipo ProdutoModelDTO.
    //Aqui está sendo usado uma biblioteca por nome modelMapper ela ajuda a reduzir o tamanho do codigo.

   /* public ProdutoModelDTO toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModelDTO.class);
    }*/

    // Esse metódo é responsavel por pegar uma objeto Produto e convertelo para um objeto tipo ProdutoModelDTO.
    public ProdutoModelDTO toModel(Produto produto) {

        FotoProdutoModelDTO foto = new FotoProdutoModelDTO();
        if (Objects.nonNull(produto.getFotoProduto())) {

            foto.setId(produto.getFotoProduto().getId());
            foto.setNomeArquivo(produto.getFotoProduto().getNomeArquivo());
            foto.setDescricao(produto.getFotoProduto().getDescricao());
            foto.setContentType(produto.getFotoProduto().getContentType());
            foto.setTamanho(produto.getFotoProduto().getTamanho());

        }

        ProdutoModelDTO produtoDTO = new ProdutoModelDTO();

        // Verifica se a categoria não é nula antes de acessar getCodCategoria
        if (produto.getCategoria() != null) {
            produtoDTO.setCategoria(produto.getCategoria().getCodCategoria());
        } else {
            // Define a categoria como um valor padrão caso seja nula
            Categoria categoria = (produto.getCategoria() != null) ? produto.getCategoria() : Categoria.GENERICO;
            produtoDTO.setCategoria(categoria.getCodCategoria());
        }

        produtoDTO.setId(produto.getId());
        produtoDTO.setCodProd(produto.getCodProd());
        produtoDTO.setNomeProd(produto.getNomeProd());
        produtoDTO.setDescricao(produto.getDescricaoProd());
        produtoDTO.setCategoria(produto.getCategoria().getCodCategoria());
        produtoDTO.setDescriCatedoria(produto.getCategoria().getDescricao());

        produtoDTO.setFoto(foto);

        return produtoDTO;
    }

    public List<ProdutoModelDTO> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto))
                .collect(Collectors.toList());
    }

}
