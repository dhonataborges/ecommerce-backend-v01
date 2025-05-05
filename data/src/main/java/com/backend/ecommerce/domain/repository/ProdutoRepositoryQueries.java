package com.backend.ecommerce.domain.repository;

import com.backend.ecommerce.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    // Método responsável por salvar uma foto de produto.
    // Esse método retorna o objeto `FotoProduto` após a persistência, o que é útil para saber o estado final da entidade após a inserção.
    FotoProduto save(FotoProduto foto);

    // Método responsável por excluir uma foto de produto.
    // Esse método recebe o objeto `FotoProduto` como parâmetro e a exclusão será realizada no banco de dados.
    void delete(FotoProduto foto);

}
