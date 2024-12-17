package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.VendaProdutoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.VendaProdutoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.VendaProdutoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.VendaProdutoInputDTO;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.vendaProdutoException.VendaProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.Categoria;
import com.backend.ecommerce.domain.model.VendaProduto;
import com.backend.ecommerce.domain.repository.VendaProdutoRepository;
import com.backend.ecommerce.domain.service.VendaProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/produto-a-venda")
public class VendaProdutoController {

    @Autowired
    private VendaProdutoService vendaProdutoService;

    @Autowired
    private VendaProdutoRepository vendaProdutoRepository;

    @Autowired
    private VendaProdutoModelAssemblerDTO vendaProdutoModelAssemblerDTO;

    @Autowired
    private VendaProdutoModelDisassemblerDTO vendaProdutoModelDisassemblerDTO;

    @GetMapping
    public List<VendaProdutoModelDTO> listar() {
        return vendaProdutoModelAssemblerDTO.toCollectionModel(vendaProdutoRepository.findAll());
    }
    /*
    * @GetMapping
	public List<ProdutoModelDTO> listar() {
		return produtoModelAssemblerDTO.toCollectionModel(produtoRepository.findAll());
	}
    * */

    @GetMapping("cosmeticos")
    public List<VendaProdutoModelDTO> buscarCosmeticos(@RequestParam("categoria") Long categoria) {
        return vendaProdutoModelAssemblerDTO.toCollectionModel(vendaProdutoRepository.buscarTipoProd(Categoria.toEnum(categoria.intValue())));
    }

    @GetMapping("/{vendaId}")
    public  VendaProdutoModelDTO buscar(@PathVariable Long vendaId) {

        VendaProduto vendaProduto = vendaProdutoService.buscarOuFalhar(vendaId);

        return vendaProdutoModelAssemblerDTO.toModel(vendaProduto);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public VendaProdutoModelDTO adicionar(@RequestBody @Valid VendaProdutoInputDTO vendaProdutoInputDTO) {
        try {
            VendaProduto vendaProduto = vendaProdutoModelDisassemblerDTO.toDomainObject(vendaProdutoInputDTO);

            return vendaProdutoModelAssemblerDTO.toModel(vendaProdutoService.salvar(vendaProduto));
        } catch (VendaProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{vendaId}")
    public VendaProdutoModelDTO atualizar(@PathVariable Long vendaId, @RequestBody @Valid VendaProdutoInputDTO vendaProdutoInputDTO) {
        try {
            VendaProduto vendaProduto = vendaProdutoModelDisassemblerDTO.toDomainObject(vendaProdutoInputDTO);

            VendaProduto vendaProdutoAtual = vendaProdutoService.buscarOuFalhar(vendaId);

            BeanUtils.copyProperties(vendaProduto, vendaProdutoAtual, "id");

            return vendaProdutoModelAssemblerDTO.toModel(vendaProdutoService.salvar(vendaProdutoAtual));
        } catch (VendaProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{vendaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long vendaId) {
        vendaProdutoService.excluir(vendaId);
    }

}
