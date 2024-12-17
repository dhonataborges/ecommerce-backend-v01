package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.ProdutoInputDTO;
import com.backend.ecommerce.domain.model.Categoria;
import com.backend.ecommerce.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    //Esse metódo é responsavel por pegar uma objeto ProdutoInputDTO e convertelo para um objeto tipo Produto.
    //Aqui está sendo usado uma biblioteca por nome modelMapper ela ajuda a reduzir o tamanho do codigo.

    public Produto toDomainObject(ProdutoInputDTO produtoInput) {


        Produto produto = new Produto();

       /* // Verifica se a categoria não é nula antes de acessar getCodCategoria
        if (produto.getCategoria() != null) {
            produtoInput.setCategoria(produto.getCategoria().getCodCategoria());
        } else {
            // Define a categoria como um valor padrão caso seja nula
            Categoria categoria = (produto.getCategoria() != null) ? produto.getCategoria() : Categoria.GENERICO;
            produtoInput.setCategoria(categoria.getCodCategoria());
        }
*/
        produto.setCodProd(produtoInput.getCodProd());
        produto.setNomeProd(produtoInput.getNomeProd());
        produto.setDescricaoProd(produtoInput.getDescricao());
        produto.setCategoria(Categoria.toEnum(produtoInput.getCategoria()));

        return produto;

      /*FotoProduto foto = new FotoProduto();

        return modelMapper.map(produtoInput, Produto.class);*/
    }


}
