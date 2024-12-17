package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

   /*@Query("SELECT obj FROM Estoque obj WHERE obj.produto.tipoProd.codTipoProd=:codTipoProd")
   public List<Estoque> buscarTipoProd(@Param("codTipoProd")Integer tipoProd);*/

  // List<Estoque> tipoProdEstoque(Integer tipoProdEstoque);

}
