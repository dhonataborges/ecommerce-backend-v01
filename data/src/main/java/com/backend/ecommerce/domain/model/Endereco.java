package com.backend.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

/**
 * Representa um endereço dentro do sistema. Cada endereço pode estar associado a um usuário
 * e pode ser utilizado em vários pedidos. A classe também está mapeada para a tabela
 * 'tb_endereco' no banco de dados.
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"cidade", "pedidos", "usuario"}) // Exclui relacionamentos do método toString
@Table(name ="tb_endereco") // Define a tabela no banco de dados
public class Endereco {

    /**
     * Identificador único do endereço.
     * A coluna 'id' é a chave primária na tabela 'tb_endereco'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // A geração do ID será responsabilidade do banco de dados
    private Long id;

    /**
     * Relacionamento com a entidade Usuario.
     * Cada endereço está associado a um único usuário.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id") // Nome da coluna que fará referência ao ID do usuário
    private Usuario usuario;

    /**
     * Nome do responsável pelo endereço.
     */
    @Column(name = "nome_dono", nullable = false) // Define que o nome do dono é obrigatório
    private String nomeDono;

    /**
     * CEP do endereço.
     */
    @Column(name = "cep", nullable = false) // Define que o CEP é obrigatório
    private String cep;

    /**
     * Bairro do endereço.
     */
    @Column(name = "bairro", nullable = false) // Define que o bairro é obrigatório
    private String bairro;

    /**
     * Rua do endereço.
     */
    @Column(name = "rua", nullable = false) // Define que a rua é obrigatória
    private String rua;

    /**
     * Número do endereço.
     */
    @Column(name = "numero", nullable = false) // Define que o número é obrigatório
    private String numero;

    /**
     * Complemento do endereço (se houver).
     */
    @Column(name = "complemento") // Não é obrigatório
    private String complemento;

    /**
     * Relacionamento com a cidade do endereço.
     * Cada endereço está associado a uma cidade.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id") // Nome da coluna que fará referência ao ID da cidade
    private Cidade cidade;

    /**
     * Relacionamento com os pedidos.
     * Cada endereço pode estar associado a vários pedidos.
     */
    @OneToMany(mappedBy = "endereco") // O mapeamento é feito pela propriedade 'endereco' da classe Pedido
    private List<Pedido> pedidos;
}
