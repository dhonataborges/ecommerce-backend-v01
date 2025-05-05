package com.backend.ecommerce.model;

import com.backend.ecommerce.model.enuns.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

	@Column(name = "marca")
	private String marca;

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
