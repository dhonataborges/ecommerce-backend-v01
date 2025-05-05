package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.CatalogoProdutoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.CatalogoProdutoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.CatalogoProdutoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.CatalogoProdutoInputDTO;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.CatalogoProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.CatalogoProduto;
import com.backend.ecommerce.domain.model.enuns.Categoria;
import com.backend.ecommerce.domain.repository.CatalogoProdutoRepository;
import com.backend.ecommerce.domain.service.CatalogoProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController
@RequestMapping(value = "admin/catalogo-produto")  // Mapeamento para o controlador de produtos do catálogo.
public class CatalogoProdutoAdminController {

    @Autowired
    private CatalogoProdutoService catalogoProdutoService;  // Serviço de negócios para operações no catálogo de produtos

    @Autowired
    private CatalogoProdutoRepository catalogoProdutoRepository;  // Repositório para interação com o banco de dados

    @Autowired
    private CatalogoProdutoModelAssemblerDTO catalogoProdutoModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO

    @Autowired
    private CatalogoProdutoModelDisassemblerDTO catalogoProdutoModelDisassemblerDTO;  // Responsável por converter o DTO para o modelo de domínio

    /**
     * Retorna todos os produtos do catálogo.
     * @return Lista de produtos como DTOs.
     */
    @GetMapping
    public List<CatalogoProdutoModelDTO> listar() {
        return catalogoProdutoModelAssemblerDTO.toCollectionModel(catalogoProdutoRepository.findAll());
    }

    /**
     * Retorna produtos filtrados por categoria (cosméticos).
     * @param categoria Código da categoria para filtro.
     * @return Lista de produtos da categoria solicitada como DTOs.
     */
    @GetMapping("cosmeticos")
    public List<CatalogoProdutoModelDTO> buscarCosmeticos(@RequestParam("categoria") Long categoria) {
        return catalogoProdutoModelAssemblerDTO.toCollectionModel(catalogoProdutoRepository.buscarTipoProd(Categoria.toEnum(categoria.intValue())));
    }

    /**
     * Retorna um produto específico do catálogo.
     * @param catalogoId ID do produto no catálogo.
     * @return Produto específico como DTO.
     */
    @GetMapping("/{catalogoId}")
    public CatalogoProdutoModelDTO buscar(@PathVariable Long catalogoId) {
        CatalogoProduto catalogoProduto = catalogoProdutoService.buscarOuFalhar(catalogoId);
        return catalogoProdutoModelAssemblerDTO.toModel(catalogoProduto);
    }

    /**
     * Adiciona um novo produto ao catálogo.
     * @param catalogoProdutoInputDTO DTO com os dados do novo produto.
     * @return Produto adicionado como DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CatalogoProdutoModelDTO adicionar(@RequestBody @Valid CatalogoProdutoInputDTO catalogoProdutoInputDTO) {
        try {
            CatalogoProduto catalogoProduto = catalogoProdutoModelDisassemblerDTO.toDomainObject(catalogoProdutoInputDTO);
            return catalogoProdutoModelAssemblerDTO.toModel(catalogoProdutoService.salvar(catalogoProduto));
        } catch (CatalogoProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um produto no catálogo.
     * @param catalogoId ID do produto a ser atualizado.
     * @param catalogoProdutoInputDTO DTO com os dados atualizados do produto.
     * @return Produto atualizado como DTO.
     */
    @PutMapping("/{catalogoId}")
    public CatalogoProdutoModelDTO atualizar(@PathVariable Long catalogoId, @RequestBody @Valid CatalogoProdutoInputDTO catalogoProdutoInputDTO) {
        try {
            CatalogoProduto catalogoProduto = catalogoProdutoModelDisassemblerDTO.toDomainObject(catalogoProdutoInputDTO);
            CatalogoProduto catalogoProdutoAtual = catalogoProdutoService.buscarOuFalhar(catalogoId);
            BeanUtils.copyProperties(catalogoProduto, catalogoProdutoAtual, "id");
            return catalogoProdutoModelAssemblerDTO.toModel(catalogoProdutoService.atualizar(catalogoProduto));
        } catch (CatalogoProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    /**
     * Remove um produto do catálogo.
     * @param catalogoId ID do produto a ser removido.
     */
    @DeleteMapping("/{catalogoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long catalogoId) {
        catalogoProdutoService.excluir(catalogoId);
    }
}