package com.backend.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o estoque de um produto no sistema. A classe é mapeada para a tabela 'tb_estoque' no banco de dados.
 * O estoque é responsável por armazenar a quantidade e o valor unitário de um produto.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_estoque") // Define a tabela no banco de dados
public class Estoque {

    /**
     * Identificador único do estoque.
     * A coluna 'id' é a chave primária na tabela 'tb_estoque'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID pelo banco de dados
    private Long id;

    /**
     * Data de entrada do produto no estoque.
     * O formato da data será 'yyyy-MM-dd' durante a serialização.
     */
    @Column(name = "data_entrada")
    @JsonFormat(pattern = "yyyy-MM-dd") // Define o formato da data ao ser serializada
    private LocalDate dataEntrada;

    /**
     * Data de saída do produto do estoque.
     * O formato da data será 'yyyy-MM-dd' durante a serialização.
     */
    @Column(name = "data_saida")
    @JsonFormat(pattern = "yyyy-MM-dd") // Define o formato da data ao ser serializada
    private LocalDate dataSaida;

    /**
     * Valor unitário do produto no estoque.
     * O valor deve ser maior ou igual a zero.
     */
    @PositiveOrZero // Valida que o valor é maior ou igual a zero
    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    /**
     * Quantidade de produtos disponíveis no estoque.
     */
    @Column(name = "quantidade")
    private Integer quantidade;

    /**
     * Valor total do estoque (quantidade * valor unitário).
     * O valor deve ser maior ou igual a zero.
     */
    @JsonIgnoreProperties(value = "valorTotal", allowGetters = true) // Ignora o campo 'valorTotal' ao serializar
    @PositiveOrZero // Valida que o valor total é maior ou igual a zero
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    /**
     * Relacionamento com o produto. Cada estoque está associado a um produto específico.
     */
    @ManyToOne(fetch = FetchType.LAZY) // O carregamento do produto é feito de forma preguiçosa
    @JoinColumn(name = "produto_id") // Define a coluna que faz o relacionamento com a tabela 'produto'
    private Produto produto;

    /**
     * Relacionamento com o catálogo de produtos para vendas.
     * Um estoque pode estar associado a vários produtos no catálogo.
     */
    @JsonIgnore // Ignora este relacionamento ao serializar
    @OneToMany(mappedBy = "estoque", fetch = FetchType.LAZY) // O mapeamento é feito pela propriedade 'estoque' em CatalogoProduto
    private List<CatalogoProduto> vendaProdutos = new ArrayList<>();
}
