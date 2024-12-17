package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.EstoqueModelDTO;
import com.backend.ecommerce.api.modelDTO.ProdutoModelDTO;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.EnumUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstoqueModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FotoProdutoModelAssemblerDTO fotoProdutoModelAssemblerDTO;

    //Esse metódo é responsavel por pegar uma objeto Produto e convertelo para um objeto tipo ProdutoModelDTO.
    //Aqui está sendo usado uma biblioteca por nome modelMapper ela ajuda a reduzir o tamanho do codigo.

    public EstoqueModelDTO toModel(Estoque estoque) {


        EstoqueModelDTO estoqueDTO = new EstoqueModelDTO();

        ProdutoModelDTO produtoDTO = new ProdutoModelDTO();

        Produto produto = estoque.getProduto();  // Obtém o Produto associado ao Estoque

        // Verifica se o Produto não é nulo antes de acessar getFotoProduto
        if (produto != null) {
            FotoProduto foto = estoque.getProduto().getFotoProduto();  // Atribui a fotoProduto se o Produto não for nulo

            produtoDTO.setFoto(fotoProdutoModelAssemblerDTO.toModel(foto));

        } else {
            // Se o Produto for nulo, você pode definir a fotoProduto como null ou como algum valor padrão
            FotoProduto foto = null;// Ou você pode definir uma foto padrão, como uma URL de uma imagem genérica
        }



        produtoDTO.setId(estoque.getProduto().getId());
        produtoDTO.setCodProd(estoque.getProduto().getCodProd());
        produtoDTO.setNomeProd(estoque.getProduto().getNomeProd());
        produtoDTO.setDescricao(estoque.getProduto().getDescricaoProd());
        produtoDTO.setCategoria(estoque.getProduto().getCategoria().getCodCategoria());
        produtoDTO.setDescriCatedoria(estoque.getProduto().getCategoria().getDescricao());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        if (estoque.getDataEntrada() == null) {
            estoque.setDataEntrada(LocalDate.now());
        }

        if (estoque.getDataSaida() == null) {
            estoque.setDataSaida(LocalDate.now());
        }

        // Definindo valores no DTO
        estoqueDTO.setId(estoque.getId());
        estoqueDTO.setDataEntrada(LocalDate.parse(estoque.getDataEntrada().format(formatter)));
        estoqueDTO.setDataSaida(LocalDate.parse(estoque.getDataSaida().format(formatter)));
        estoqueDTO.setValorUnid(estoque.getValorUnid());
        estoqueDTO.setQtdProd(estoque.getQtdProd());
        estoqueDTO.setValorTotalProd(estoque.getValorTotalProd());

        estoqueDTO.setProduto(produtoDTO);
        return estoqueDTO;
        //return modelMapper.map(estoque, EstoqueModelDTO.class);
    }

    // Esse metódo é responsavel por pegar uma objeto Produto e convertelo para um objeto tipo ProdutoModelDTO.

    public List<EstoqueModelDTO> toCollectionModel(List<Estoque> estoques) {
        return estoques.stream()
                .map(estoque -> toModel(estoque))
                .collect(Collectors.toList());
    }

}
