package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    // Consulta que retorna o código do produto associado a um estoque, dado o ID do produto
    @Query("SELECT e.produto.codigo FROM Estoque e WHERE e.produto.id = :produtoId")
    public String codProd(@Param("produtoId") Long produtoId);

    // Consulta que retorna o valor unitário do estoque, dado o ID do estoque
    @Query("SELECT e.valorUnitario FROM Estoque e WHERE e.id = :estoqueId")
    public BigDecimal valorUnitario(@Param("estoqueId") Long estoqueId);

    /* Comentado:
    // Consulta que retorna a quantidade do produto no estoque, dado o ID do produto
    @Query("SELECT e.qtdProd FROM Estoque e WHERE e.produto.id = :produtoId")
    public Integer qtdEmEstoque(@Param("produtoId") Long produtoId);

    // Consulta que busca um estoque específico pelo seu ID
    @Query("SELECT e FROM Estoque e WHERE e.id = :estoqueId")
    Estoque buscarEstoqueId(@Param("estoqueId") Long estoqueId);
    */

}
