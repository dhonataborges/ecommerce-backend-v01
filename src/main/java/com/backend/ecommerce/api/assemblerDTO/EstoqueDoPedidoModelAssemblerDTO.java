package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.EstoqueDoPedidoModelDTO;
import com.backend.ecommerce.api.modelDTO.EstoqueModelDTO;
import com.backend.ecommerce.api.modelDTO.ProdutoDoPedidoModelDTO;
import com.backend.ecommerce.api.modelDTO.ProdutoModelDTO;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstoqueDoPedidoModelAssemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto Estoque para um objeto do tipo EstoqueDoPedidoModelDTO.
     * Ele utiliza o ModelMapper para reduzir o código e realizar a transformação de dados.
     *
     * @param estoque Objeto de domínio do tipo Estoque a ser convertido.
     * @return Retorna o objeto EstoqueDoPedidoModelDTO convertido.
     */
    public EstoqueDoPedidoModelDTO toModel(Estoque estoque) {

        // Criação de uma instância do DTO EstoqueDoPedidoModelDTO
        EstoqueDoPedidoModelDTO estoqueDTO = new EstoqueDoPedidoModelDTO();

        // Criação de uma instância do DTO ProdutoModelDTO
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO();

        Produto produto = estoque.getProduto();  // Obtém o Produto associado ao Estoque

        // Preenche as informações do Produto no DTO ProdutoModelDTO
        produtoDTO.setId(estoque.getProduto().getId());
        produtoDTO.setCodProd(estoque.getProduto().getCodigo());
        produtoDTO.setNomeProd(estoque.getProduto().getNome());

        // Preenche o DTO EstoqueDoPedidoModelDTO com as informações do Estoque
        estoqueDTO.setId(estoque.getId());
        estoqueDTO.setProduto(produtoDTO);

        // Retorna o DTO EstoqueDoPedidoModelDTO preenchido
        return estoqueDTO;
    }

    /**
     * Este método converte uma lista de objetos Estoque para uma lista de objetos EstoqueDoPedidoModelDTO.
     * Ele utiliza o método toModel para mapear cada objeto individualmente.
     *
     * @param estoques Lista de objetos Estoque a serem convertidos.
     * @return Retorna uma lista de objetos EstoqueDoPedidoModelDTO.
     */
    public List<EstoqueDoPedidoModelDTO> toCollectionModel(List<Estoque> estoques) {
        // Mapeia cada objeto Estoque da lista para um EstoqueDoPedidoModelDTO
        return estoques.stream()
                .map(estoque -> toModel(estoque))  // Converte cada Estoque para EstoqueDoPedidoModelDTO
                .collect(Collectors.toList());  // Coleta os resultados em uma lista
    }

}