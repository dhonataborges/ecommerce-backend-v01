package com.backend.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma cidade no sistema.
 * Cada cidade pertence a um estado específico e pode ter vários endereços associados.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "estado") // Evita que a propriedade 'estado' seja incluída na string gerada pelo toString()
@Entity
@Table(name = "tb_cidade")
public class Cidade {

    /**
     * Identificador único da cidade.
     * A chave primária é gerada automaticamente pelo banco de dados.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da cidade.
     * Não pode ser nulo, pois todas as cidades devem ter um nome associado.
     */
    @Column(nullable = false)
    private String nome;

    /**
     * Relacionamento ManyToOne com a entidade Estado.
     * Uma cidade pertence a um único estado.
     * A chave estrangeira 'estado_id' é associada a este campo.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado estado;

    /**
     * Relacionamento OneToMany com a entidade Endereco.
     * Uma cidade pode ter vários endereços associados.
     * O campo 'cidade' da entidade Endereco é mapeado como a chave estrangeira.
     * O carregamento dos endereços é feito de forma preguiçosa (Lazy).
     */
    @JsonIgnore // Ignora a serialização de endereços no JSON
    @OneToMany(mappedBy = "cidade", fetch = FetchType.LAZY)
    private List<Endereco> enderecos = new ArrayList<>();
}
