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

@CrossOrigin("*")
@RestController
@RequestMapping(value = "admin/produtos")
public class ProdutoAdminController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoModelAssemblerDTO produtoModelAssemblerDTO;

    @Autowired
    private ProdutoModelDTO produtoModelDTO;

    @Autowired
    private ProdutoModelDisassemblerDTO produtoModelDisassemblerDTO;

    @Autowired
    private ProdutoModelAssemblerDTO produtoModelAssemblerSalvarDTO;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoProdutoModelDisassemblerDTO fotoProdutoModelDisassemblerDTO;


    @GetMapping
    public List<ProdutoModelDTO> listar() {
        return produtoModelAssemblerDTO.toCollectionModel(produtoRepository.findAll());
    }

    @GetMapping("/{produtoId}")
    public ProdutoModelDTO buscar(@PathVariable Long produtoId) {

        Produto produto = produtoService.buscarOuFalhar(produtoId);

        return produtoModelAssemblerDTO.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProdutoModelDTO adicionar(@RequestBody @Valid ProdutoInputDTO produtoInput) {
        try {
            Produto produto = produtoModelDisassemblerDTO.toDomainObject(produtoInput);

            return produtoModelAssemblerSalvarDTO.toModel(produtoService.salvar(produto));
        } catch (FotoProdutoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{produtoId}")
    public ProdutoModelDTO atualizar(@PathVariable Long produtoId, @RequestBody @Valid ProdutoInputDTO produtoInput) {

        try {
            Produto produto = produtoModelDisassemblerDTO.toDomainObject(produtoInput);

            Produto produtoAtual = produtoService.buscarOuFalhar(produtoId);

            BeanUtils.copyProperties(produto, produtoAtual, "id");

            return produtoModelAssemblerDTO.toModel(produtoService.atualizar(produtoAtual));

        } catch (FotoProdutoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long produtoId) {
        produtoService.excluir(produtoId);
    }

}
