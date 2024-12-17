package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.EstoqueModelDTO;
import com.backend.ecommerce.api.modelDTO.VendaProdutoModelDTO;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.model.VendaProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendaProdutoModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoModelAssemblerDTO produtoModelAssemblerDTO;

    //Esse metódo é responsavel por pegar uma objeto Produto e convertelo para um objeto tipo ProdutoModelDTO.
    //Aqui está sendo usado uma biblioteca por nome modelMapper ela ajuda a reduzir o tamanho do codigo.

    public VendaProdutoModelDTO toModel(VendaProduto vendaProduto) {

        Produto produto = vendaProduto.getEstoque().getProduto();

        EstoqueModelDTO estoqueDTO = new EstoqueModelDTO();
        estoqueDTO.setId(vendaProduto.getEstoque().getId());
        estoqueDTO.setValorUnid(vendaProduto.getEstoque().getValorUnid());
        estoqueDTO.setQtdProd(vendaProduto.getEstoque().getQtdProd());
        estoqueDTO.setProduto(produtoModelAssemblerDTO.toModel(produto));

        VendaProdutoModelDTO vendaProdutoDTO = new VendaProdutoModelDTO();
        vendaProdutoDTO.setId(vendaProduto.getId());
        vendaProdutoDTO.setValorVenda(vendaProduto.getValorVenda());
        vendaProdutoDTO.setNumParcela(vendaProduto.getNumParcela());
        vendaProdutoDTO.setValorParcela(vendaProduto.getValorParcela());
        vendaProdutoDTO.setDescontoPorcento(vendaProduto.getDescontoPorcento());


        vendaProdutoDTO.setEstoque(estoqueDTO);

        return vendaProdutoDTO;
       // return modelMapper.map(vendaProduto, VendaProdutoModelDTO.class);
    }

    // Esse metódo é responsavel por pegar uma objeto Produto e convertelo para um objeto tipo ProdutoModelDTO.

    public List<VendaProdutoModelDTO> toCollectionModel(List<VendaProduto> vendaProdutos) {
        return vendaProdutos.stream()
                .map(vendaProduto -> toModel(vendaProduto))
                .collect(Collectors.toList());
    }

}
