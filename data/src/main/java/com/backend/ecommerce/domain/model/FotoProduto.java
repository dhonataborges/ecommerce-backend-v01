package com.backend.ecommerce.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa a foto de um produto no sistema. A classe é mapeada para a tabela 'tb_foto_produto' no banco de dados.
 * Cada foto está associada a um produto específico, e contém informações sobre o arquivo de imagem.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_foto_produto") // Define a tabela no banco de dados
public class FotoProduto {

    /**
     * Identificador único da foto do produto.
     * A coluna 'produto_id' é a chave primária e também está associada ao produto correspondente.
     */
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "produto_id") // Define o nome da coluna como 'produto_id' no banco de dados
    private Long id;

    /**
     * Associação do produto com a foto. Cada foto é vinculada a um único produto.
     * O mapeamento de id é feito através da anotação @MapsId, o que significa que o id da foto será o mesmo do produto.
     */
    @OneToOne
    @MapsId // Mapeia o id da foto para o id do produto
    private Produto produto;

    /**
     * Nome do arquivo da foto (ex: 'produto1.jpg').
     */
    private String nomeArquivo;

    /**
     * Descrição da foto (ex: 'Foto frontal do produto').
     */
    private String descricao;

    /**
     * Tipo de conteúdo (MIME type) do arquivo da foto (ex: 'image/jpeg').
     */
    private String contentType;

    /**
     * Tamanho do arquivo da foto, em bytes.
     */
    private Long tamanho;
}
