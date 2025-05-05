package com.backend.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_endereco")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_dono_endereco", nullable = false)
    private String nomeDonoEndereco;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "rua_avenida", nullable = false)
    private String ruaAvenida;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}