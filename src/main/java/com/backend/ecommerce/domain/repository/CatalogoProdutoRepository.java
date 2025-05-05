package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.PedidoItem;
import com.backend.ecommerce.domain.model.enuns.Categoria;
import com.backend.ecommerce.domain.model.CatalogoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogoProdutoRepository extends JpaRepository<CatalogoProduto, Long> {

    // Consulta que busca todos os produtos de um catálogo por categoria
    @Query("SELECT obj FROM CatalogoProduto obj WHERE obj.estoque.produto.categoria=:categoria")
    public List<CatalogoProduto> buscarTipoProd(@Param("categoria") Categoria categoria);

    // Consulta que busca o valor com desconto de um produto no catálogo
    @Query("SELECT catalogo.valorComDesconto FROM CatalogoProduto catalogo WHERE catalogo.id =:catalogoId")
    public BigDecimal buscarValorComDesconto(@Param("catalogoId") Long catalogoId);

    // Consulta que busca o valor unitário de um produto no catálogo
    @Query("SELECT catalogo.estoque.valorUnitario FROM CatalogoProduto catalogo WHERE catalogo.id =:catalogoId")
    public BigDecimal buscarValorUnitario(@Param("catalogoId") Long catalogoId);

    // Consulta que busca o ID do produto associado a um item do catálogo
    @Query("SELECT catalogo.estoque.produto.id FROM CatalogoProduto catalogo WHERE catalogo.id = :catalogoId")
    Long findProdutoIdByVendaId(@Param("catalogoId") Long catalogoId);

    // Consulta que busca o código do produto a partir do seu ID
    @Query("SELECT catalogo.estoque.produto.codigo FROM CatalogoProduto catalogo WHERE catalogo.estoque.produto.id = :produtoId")
    public String codProd(@Param("produtoId") Long produtoId);

    // Consulta que busca o catálogo do produto junto com informações do estoque e produto associado
    @Query("""
    SELECT c FROM CatalogoProduto c
    JOIN FETCH c.estoque e
    JOIN FETCH e.produto p
    WHERE c.id = :catalogoId
    """)
    CatalogoProduto buscarCatalogoComProduto(@Param("catalogoId") Long catalogoId);

}
