package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.EstoqueModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EstoqueModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.EstoqueModelDTO;
import com.backend.ecommerce.api.modelDTO.input.EstoqueInputDTO;
import com.backend.ecommerce.domain.exception.FotoProdutoNaoEncontradaException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.estoqueException.EstoqueNaoEncontradoException;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.repository.EstoqueRepository;
import com.backend.ecommerce.domain.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "admin/produto-em-estoque")
public class EstoqueAdminController {

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueModelAssemblerDTO estoqueModelAssemblerDTO;

    @Autowired
    private EstoqueModelDisassemblerDTO estoqueModelDisassemblerDTO;

    @GetMapping
    public List<EstoqueModelDTO> listar() {
        return estoqueModelAssemblerDTO.toCollectionModel(estoqueRepository.findAll());
    }

    @GetMapping("/{estoqueId}")
    public EstoqueModelDTO buscar(@PathVariable Long estoqueId) {

        Estoque estoque = estoqueService.buscarOuFalhar(estoqueId);

        return estoqueModelAssemblerDTO.toModel(estoque);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EstoqueModelDTO adicionar(@RequestBody @Valid EstoqueInputDTO estoqueInputDTO) {
        try {


           /* LocalDate dataRecebida = Instant.parse(estoqueInputDTO.toString())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            estoqueInputDTO.setDataEntrada(dataRecebida);*/

            Estoque estoque = estoqueModelDisassemblerDTO.toDomainObject(estoqueInputDTO);

            return estoqueModelAssemblerDTO.toModel(estoqueService.salvar(estoque));
        }catch (EstoqueNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{estoqueId}")
    public EstoqueModelDTO atualizar(@PathVariable Long estoqueId, @RequestBody @Valid EstoqueInputDTO estoqueInputDTO) {
        try {
            Estoque estoque = estoqueModelDisassemblerDTO.toDomainObject(estoqueInputDTO);

            Estoque estoqueAtual = estoqueService.buscarOuFalhar(estoqueId);

            BeanUtils.copyProperties(estoque, estoqueAtual, "id");

            return estoqueModelAssemblerDTO.toModel(estoqueService.salvar(estoqueAtual));
        } catch (EstoqueNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{estoqueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estoqueId) {
        estoqueService.excluir(estoqueId);
    }

}
