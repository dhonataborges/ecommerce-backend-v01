package com.backend.ecommerce.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa um número de telefone associado a um usuário.
 * A classe é mapeada para a tabela 'tb_telefone' no banco de dados.
 * Cada instância dessa classe representa um número de telefone registrado no sistema.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_telefone") // Define a tabela no banco de dados
public class Telefone {

    /**
     * Identificador único do telefone.
     * Este campo é a chave primária da tabela 'tb_telefone'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do id com a estratégia de auto incremento
    private Long id;

    /**
     * Responsável pelo número de telefone.
     * Pode ser o nome de quem é responsável pelo número, como "Pai", "Mãe", etc.
     */
    @Column(name = "responsavel") // Define a coluna 'responsavel' no banco
    private String responsavel;

    /**
     * Número do telefone.
     * Armazenado no banco de dados como 'numero'.
     */
    @Column(name = "numero") // Define a coluna 'numero' no banco
    private String numero;

    /**
     * Usuário ao qual o telefone está associado.
     * Relacionamento Muitos para Um (ManyToOne) com a classe 'Usuario'.
     */
    @ManyToOne // Define que cada telefone está associado a um único usuário
    @JoinColumn(name = "usuario_id", nullable = false) // Define a coluna 'usuario_id' no banco, referenciando a tabela 'tb_usuario'
    private Usuario usuario;
}
