package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    // Consulta para buscar um produto pelo código (codigo) - geralmente usado para procurar um produto específico por seu código único
    @Query("SELECT obj FROM Produto obj WHERE obj.codigo =:codigo")
    public Produto findByCodProd(@Param("codigo") String codigo);

    // Consulta que retorna a foto de um produto baseado no ID do produto - usa a associação entre Produto e FotoProduto
    @Query("from FotoProduto f where f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoDoProdutoById(Long produtoId);

    // Consulta que retorna a foto pelo ID da foto - pode ser usada para obter uma foto específica se soubermos seu ID
    @Query("from FotoProduto f where f.id = :fotoId")
    Optional<FotoProduto> findFotoById(Long fotoId);

    // Consulta para buscar um produto pelo seu ID - padrão para carregar um produto específico com base no seu ID
    @Query("from Produto p where p.id = :produtoId")
    Produto findProdutoById(Long produtoId);

}
