package com.backend.ecommerce.model;

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

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_estoque")
public class Estoque {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_entrada")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataEntrada;

    @Column(name = "data_saida")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataSaida;

    @PositiveOrZero
    @Column(name = "valor_und")
    private BigDecimal valorUnid;

    @Column(name = "qtd_prod")
    private Integer qtdProd;

    @JsonIgnoreProperties(value = "valorTotalProd", allowGetters = true)
    @PositiveOrZero
    @Column(name = "valor_total_prod")
    private BigDecimal valorTotalProd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @JsonIgnore
    @OneToMany(mappedBy = "estoque", fetch = FetchType.LAZY)
    private List<VendaProduto> vendaProdutos = new ArrayList<>();
}
