package com.backend.ecommerce.domain.model;

import com.backend.ecommerce.domain.model.enuns.Permissoes;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Representa um usuário do sistema.
 * Cada instância dessa classe corresponde a um usuário registrado, com dados pessoais e permissões.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"enderecos", "pedidos"}) // Exclui os campos 'enderecos' e 'pedidos' ao gerar a representação em string do objeto
@Entity // Marca a classe como uma entidade JPA
@Table(name = "tb_usuario") // Define a tabela 'tb_usuario' no banco de dados
public class Usuario {

    /**
     * Identificador único do usuário.
     * Este campo é a chave primária da tabela 'tb_usuario'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // A chave primária será gerada automaticamente (auto incremento)
    private Long id;

    /**
     * Nome do usuário.
     * O campo 'nome' não pode ser nulo, conforme o parâmetro 'nullable = false'.
     */
    @Column(name = "nome", nullable = false) // Define a coluna 'nome' como não nula
    private String nome;

    /**
     * Sobrenome do usuário.
     * O campo 'sobrenome' não pode ser nulo, conforme o parâmetro 'nullable = false'.
     */
    @Column(name = "sobrenome", nullable = false) // Define a coluna 'sobrenome' como não nula
    private String sobrenome;

    /**
     * Endereço de e-mail do usuário.
     * O campo 'email' é único e não pode ser nulo.
     */
    @Column(name = "email", nullable = false, unique = true) // Define a coluna 'email' como única e não nula
    private String email;

    /**
     * Senha do usuário.
     * O campo 'senha' não pode ser nulo.
     */
    @Column(name = "senha", nullable = false) // Define a coluna 'senha' como não nula
    private String senha;

    /**
     * Permissão do usuário no sistema.
     * A permissão é representada por um enum 'Permissoes' e é armazenada na coluna 'permicoes'.
     */
    @JoinColumn(name = "permicoes") // Define a coluna 'permicoes' no banco de dados
    private Permissoes permissoes;

    /**
     * Lista de endereços associados ao usuário.
     * Relacionamento Um para Muitos (OneToMany) com a classe 'Endereco'.
     */
    @OneToMany(mappedBy = "usuario") // Define o relacionamento com a classe 'Endereco' e o mapeamento pelo campo 'usuario' em 'Endereco'
    private List<Endereco> enderecos;

    /**
     * Lista de pedidos feitos pelo usuário.
     * Relacionamento Um para Muitos (OneToMany) com a classe 'Pedido'.
     */
    @OneToMany(mappedBy = "usuario") // Define o relacionamento com a classe 'Pedido' e o mapeamento pelo campo 'usuario' em 'Pedido'
    private List<Pedido> pedidos;
}
