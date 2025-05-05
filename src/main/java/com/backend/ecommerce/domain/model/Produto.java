package com.backend.ecommerce.domain.model;

import com.backend.ecommerce.domain.model.enuns.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um produto no catálogo.
 * A classe é mapeada para a tabela 'tb_produto' no banco de dados.
 * Cada instância dessa classe representa um produto disponível no sistema.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_produto") // Define a tabela no banco de dados
public class Produto {

	/**
	 * Identificador único do produto.
	 * Este campo é a chave primária da tabela 'tb_produto'.
	 */
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do id com a estratégia de auto incremento
	private Long id;

	/**
	 * Código único do produto.
	 * Armazenado no banco de dados como 'codigo'.
	 */
	@Column(name = "codigo") // Define a coluna 'codigo' no banco
	private String codigo;

	/**
	 * Nome do produto.
	 * Armazenado no banco de dados como 'nome'.
	 */
	@Column(name = "nome") // Define a coluna 'nome' no banco
	private String nome;

	/**
	 * Marca do produto.
	 * Armazenado no banco de dados como 'marca'.
	 */
	@Column(name = "marca") // Define a coluna 'marca' no banco
	private String marca;

	/**
	 * Descrição do produto.
	 * Armazenado no banco de dados como 'descricao'.
	 */
	@Column(name = "descricao") // Define a coluna 'descricao' no banco
	private String descricao;

	/**
	 * Categoria do produto.
	 * Este campo faz referência à enum 'Categoria', que define a categoria do produto.
	 * A categoria é armazenada na coluna 'categoria' do banco de dados.
	 */
	@JoinColumn(name = "categoria") // Define a coluna 'categoria' no banco, que faz referência à enum Categoria
	private Categoria categoria;

	/**
	 * Foto do produto.
	 * Cada produto pode ter uma foto associada.
	 * A associação é feita através da relação 'um para um' com a classe FotoProduto.
	 */
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "produto") // Relacionamento um para um com a classe FotoProduto
	private FotoProduto fotoProduto;
}
