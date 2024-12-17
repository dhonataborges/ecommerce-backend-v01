package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface
ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {
     @Query("SELECT obj FROM Produto obj WHERE obj.codProd =:codProd")
     public Produto findByCodProd(@Param("codProd")String codProd);

    @Query("from FotoProduto f where f.produto.id = :produtoId")
    Optional<FotoProduto> findProdutoById(Long produtoId);

    @Query("from FotoProduto f where f.id = :fotoId")
    Optional<FotoProduto> findFotoById(Long fotoId);
}
