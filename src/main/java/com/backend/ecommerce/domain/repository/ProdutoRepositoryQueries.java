package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    void delete(FotoProduto foto);

}