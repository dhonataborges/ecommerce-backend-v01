package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.*;
import com.backend.ecommerce.api.modelDTO.ProdutoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.ProdutoInputDTO;
import com.backend.ecommerce.domain.exception.FotoProdutoNaoEncontradaException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import com.backend.ecommerce.domain.service.CatalogoFotoProdutoService;
import com.backend.ecommerce.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define a classe como um controlador REST.
@RequestMapping(value = "admin/produtos")  // Define o caminho base para as rotas de produtos no contexto administrativo.
public class ProdutoAdminController {

    @Autowired
    private ProdutoService produtoService;  // Serviço de negócios para operações relacionadas a produtos.

    @Autowired
    private ProdutoRepository produtoRepository;  // Repositório para interagir com o banco de dados de produtos.

    @Autowired
    private ProdutoModelAssemblerDTO produtoModelAssemblerDTO;  // Converte o modelo de domínio de produto para DTO.

    @Autowired
    private ProdutoModelDTO produtoModelDTO;  // DTO utilizado para representar um produto.

    @Autowired
    private ProdutoModelDisassemblerDTO produtoModelDisassemblerDTO;  // Converte o DTO de entrada para o modelo de domínio de produto.

    @Autowired
    private ProdutoModelAssemblerDTO produtoModelAssemblerSalvarDTO;  // Converte o modelo de produto para DTO ao salvar.

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;  // Serviço relacionado ao catálogo de fotos de produtos.

    @Autowired
    private FotoProdutoModelDisassemblerDTO fotoProdutoModelDisassemblerDTO;  // Converte o DTO de foto para o modelo de domínio de foto de produto.

    /**
     * Retorna todos os produtos registrados.
     * @return Lista de produtos convertida para DTO.
     */
    @GetMapping
    public List<ProdutoModelDTO> listar() {
        return produtoModelAssemblerDTO.toCollectionModel(produtoRepository.findAll());  // Retorna todos os produtos convertidos em DTO.
    }

    /**
     * Retorna um produto específico pelo ID.
     * @param produtoId ID do produto.
     * @return Produto convertido para DTO.
     */
    @GetMapping("/{produtoId}")
    public ProdutoModelDTO buscar(@PathVariable Long produtoId) {

        Produto produto = produtoService.buscarOuFalhar(produtoId);  // Busca o produto pelo ID.

        return produtoModelAssemblerDTO.toModel(produto);  // Converte o produto para DTO e retorna.
    }

    /**
     * Adiciona um novo produto.
     * @param produtoInput DTO contendo os dados do produto a ser adicionado.
     * @return Produto adicionado convertido para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Retorna o status HTTP 201 (Criado).
    public ProdutoModelDTO adicionar(@RequestBody @Valid ProdutoInputDTO produtoInput) {
        try {
            Produto produto = produtoModelDisassemblerDTO.toDomainObject(produtoInput);  // Converte o DTO para o modelo de domínio.

            return produtoModelAssemblerSalvarDTO.toModel(produtoService.salvar(produto));  // Salva o produto e retorna convertido em DTO.
        } catch (FotoProdutoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());  // Lança exceção de negócio se foto do produto não encontrada.
        } catch (IOException e) {
            throw new RuntimeException(e);  // Lança uma exceção de runtime caso haja erro de IO.
        }
    }

    /**
     * Atualiza um produto existente.
     * @param produtoId ID do produto a ser atualizado.
     * @param produtoInput DTO contendo os dados do produto a ser atualizado.
     * @return Produto atualizado convertido para DTO.
     */
    @PutMapping("/{produtoId}")
    public ProdutoModelDTO atualizar(@PathVariable Long produtoId, @RequestBody @Valid ProdutoInputDTO produtoInput) {

        try {
            Produto produto = produtoModelDisassemblerDTO.toDomainObject(produtoInput);  // Converte o DTO para o modelo de domínio.

            Produto produtoAtual = produtoService.buscarOuFalhar(produtoId);  // Busca o produto existente pelo ID.

            BeanUtils.copyProperties(produto, produtoAtual, "id");  // Copia as propriedades do produto atualizado (exceto o ID).

            return produtoModelAssemblerDTO.toModel(produtoService.atualizar(produtoAtual));  // Atualiza o produto e retorna convertido em DTO.
        } catch (FotoProdutoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());  // Lança exceção de negócio se foto do produto não encontrada.
        } catch (IOException e) {
            throw new RuntimeException(e);  // Lança uma exceção de runtime caso haja erro de IO.
        }

    }

    /**
     * Remove um produto específico pelo ID.
     * @param produtoId ID do produto a ser removido.
     */
    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Retorna o status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long produtoId) {
        produtoService.excluir(produtoId);  // Exclui o produto pelo ID.
    }
}