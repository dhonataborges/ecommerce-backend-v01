package com.backend.ecommerce.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Representa um estado dentro do sistema. Um estado pode ter várias cidades associadas.
 * A classe está mapeada para a tabela 'tb_estado' no banco de dados.
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "cidades") // Exclui o relacionamento com cidades do método toString para evitar loops infinitos
@Table(name = "tb_estado") // Define a tabela no banco de dados
public class Estado {

    /**
     * Identificador único do estado.
     * A coluna 'id' é a chave primária na tabela 'tb_estado'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // A geração do ID será responsabilidade do banco de dados
    private Long id;

    /**
     * Nome do estado.
     */
    @Column(nullable = false) // Define que o nome do estado é obrigatório
    private String nome;

    /**
     * Relacionamento com as cidades.
     * Um estado pode ter várias cidades associadas.
     */
    @OneToMany(mappedBy = "estado") // O mapeamento é feito pela propriedade 'estado' da classe Cidade
    private List<Cidade> cidades;
}
