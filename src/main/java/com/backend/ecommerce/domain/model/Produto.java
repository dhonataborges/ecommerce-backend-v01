package com.backend.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_produto")
public class Produto {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cod_prod")
	private String codProd;

	@Column(name = "nome_prod")
	private String nomeProd;

	@Column(name = "descricao_prod")
	private String descricaoProd;

	@JoinColumn(name = "categoria")
	private Categoria categoria;

	@JsonIgnore
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
	private List<Estoque> estoques = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "produto")
	private FotoProduto fotoProduto;
}
