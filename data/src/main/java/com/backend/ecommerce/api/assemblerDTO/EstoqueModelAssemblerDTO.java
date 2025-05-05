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

    // Injeção das dependências necessárias
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FotoProdutoModelAssemblerDTO fotoProdutoModelAssemblerDTO;

    /**
     * Este método é responsável por converter um objeto Estoque para um objeto do tipo EstoqueModelDTO.
     * Utiliza o ModelMapper para reduzir o código e transformar os dados.
     *
     * @param estoque Objeto de domínio do tipo Estoque a ser convertido.
     * @return Retorna o objeto EstoqueModelDTO convertido.
     */
    public EstoqueModelDTO toModel(Estoque estoque) {

        // Cria uma instância do DTO EstoqueModelDTO
        EstoqueModelDTO estoqueDTO = new EstoqueModelDTO();

        // Cria uma instância do DTO ProdutoModelDTO
        ProdutoModelDTO produtoDTO = new ProdutoModelDTO();

        Produto produto = estoque.getProduto();  // Obtém o Produto associado ao Estoque

        // Verifica se o Produto não é nulo antes de acessar getFotoProduto
        if (produto != null) {
            FotoProduto foto = estoque.getProduto().getFotoProduto();  // Obtém a FotoProduto associada ao Produto

            // Preenche a foto do produto no DTO, utilizando o FotoProdutoModelAssemblerDTO para mapear a foto
            produtoDTO.setFoto(fotoProdutoModelAssemblerDTO.toModel(foto));
        } else {
            // Caso o Produto seja nulo, definimos a foto como null (ou poderia ser uma foto padrão)
            FotoProduto foto = null; // Ou você pode definir uma foto padrão, como uma URL de uma imagem genérica
        }

        // Preenche as informações do Produto no DTO ProdutoModelDTO
        produtoDTO.setId(estoque.getProduto().getId());
        produtoDTO.setCodProd(estoque.getProduto().getCodigo());
        produtoDTO.setNomeProd(estoque.getProduto().getNome());
        produtoDTO.setDescricaoProduto(estoque.getProduto().getDescricao());
        produtoDTO.setCategoria(estoque.getProduto().getCategoria().getCodCategoria());
        produtoDTO.setDescriCatedoria(estoque.getProduto().getCategoria().getDescricao());

        // Formatação das datas de entrada e saída para o formato "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Verifica se a data de entrada do estoque é nula, e se for, define como a data atual
        if (estoque.getDataEntrada() == null) {
            estoque.setDataEntrada(LocalDate.now());
        }

        // Verifica se a data de saída do estoque é nula, e se for, define como a data atual
        if (estoque.getDataSaida() == null) {
            estoque.setDataSaida(LocalDate.now());
        }

        // Preenche as informações do DTO EstoqueModelDTO
        estoqueDTO.setId(estoque.getId());
        estoqueDTO.setDataEntrada(LocalDate.parse(estoque.getDataEntrada().format(formatter)));
        estoqueDTO.setDataSaida(LocalDate.parse(estoque.getDataSaida().format(formatter)));
        estoqueDTO.setValorUnitario(estoque.getValorUnitario());
        estoqueDTO.setQuantidade(estoque.getQuantidade());
        estoqueDTO.setValorTotal(estoque.getValorTotal());

        // Define o Produto dentro do DTO EstoqueModelDTO
        estoqueDTO.setProduto(produtoDTO);

        // Retorna o EstoqueModelDTO com todas as informações preenchidas
        return estoqueDTO;
    }

    /**
     * Este método converte uma lista de objetos Estoque para uma lista de objetos EstoqueModelDTO.
     * Ele utiliza o método toModel para mapear cada objeto individualmente.
     *
     * @param estoques Lista de objetos Estoque a serem convertidos.
     * @return Retorna uma lista de objetos EstoqueModelDTO.
     */
    public List<EstoqueModelDTO> toCollectionModel(List<Estoque> estoques) {
        // Mapeia cada objeto Estoque da lista para um EstoqueModelDTO
        return estoques.stream()
                .map(estoque -> toModel(estoque))  // Converte cada Estoque para EstoqueModelDTO
                .collect(Collectors.toList());  // Coleta os resultados em uma lista
    }
}