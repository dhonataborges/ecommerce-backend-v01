package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.Categoria;
import com.backend.ecommerce.domain.model.VendaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long> {
   /* @Query("SELECT * FROM VendaProduto obj WHERE obj.codProd =:codProd")
    public Produto findByCodProd(@Param("codProd")S*//*tring codProd);*/

    @Query("SELECT obj FROM VendaProduto obj WHERE obj.estoque.produto.categoria=:categoria")
    public List<VendaProduto> buscarTipoProd(@Param("categoria") Categoria categoria);
}
