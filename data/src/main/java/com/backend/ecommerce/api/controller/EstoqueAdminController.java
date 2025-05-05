package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.EstoqueModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EstoqueModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.EstoqueModelDTO;
import com.backend.ecommerce.api.modelDTO.input.EstoqueInputDTO;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.EstoqueNaoEncontradoException;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.repository.EstoqueRepository;
import com.backend.ecommerce.domain.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define que esta classe é um controlador REST (para tratar requisições HTTP).
@RequestMapping(value = "admin/produto-em-estoque")  // Mapeamento da URL para o controlador de estoque.
public class EstoqueAdminController {

    @Autowired
    private EstoqueService estoqueService;  // Serviço de negócios para operações no estoque.

    @Autowired
    private EstoqueRepository estoqueRepository;  // Repositório para interação com o banco de dados.

    @Autowired
    private EstoqueModelAssemblerDTO estoqueModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO (Data Transfer Object).

    @Autowired
    private EstoqueModelDisassemblerDTO estoqueModelDisassemblerDTO;  // Responsável por converter o DTO para o modelo de domínio.

    /**
     * Retorna todos os itens de estoque.
     * @return Lista de itens de estoque convertidos para DTO.
     */
    @GetMapping
    public List<EstoqueModelDTO> listar() {
        return estoqueModelAssemblerDTO.toCollectionModel(estoqueRepository.findAll());  // Converte os itens de estoque para o formato DTO e retorna.
    }

    /**
     * Retorna um item de estoque específico com base no ID.
     * @param estoqueId ID do item de estoque.
     * @return Item de estoque convertido para DTO.
     */
    @GetMapping("/{estoqueId}")
    public EstoqueModelDTO buscar(@PathVariable Long estoqueId) {
        Estoque estoque = estoqueService.buscarOuFalhar(estoqueId);  // Busca o item de estoque pelo ID.
        return estoqueModelAssemblerDTO.toModel(estoque);  // Converte o item de estoque para o formato DTO e retorna.
    }

    /**
     * Adiciona um novo item ao estoque.
     * @param estoqueInputDTO DTO com os dados do item de estoque a ser adicionado.
     * @return Item de estoque criado, convertido para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Define o código de status HTTP 201 (Criado) após a inserção.
    public EstoqueModelDTO adicionar(@RequestBody @Valid EstoqueInputDTO estoqueInputDTO) {
        try {
            Estoque estoque = estoqueModelDisassemblerDTO.toDomainObject(estoqueInputDTO);  // Converte o DTO para o modelo de domínio.
            return estoqueModelAssemblerDTO.toModel(estoqueService.salvar(estoque));  // Salva o item no estoque e converte para DTO.
        } catch (EstoqueNaoEncontradoException e) {  // Caso o item de estoque não seja encontrado, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Atualiza os dados de um item de estoque existente.
     * @param estoqueId ID do item de estoque a ser atualizado.
     * @param estoqueInputDTO DTO com os dados atualizados do item de estoque.
     * @return Item de estoque atualizado, convertido para DTO.
     */
    @PutMapping("/{estoqueId}")
    public EstoqueModelDTO atualizar(@PathVariable Long estoqueId, @RequestBody @Valid EstoqueInputDTO estoqueInputDTO) {
        try {
            Estoque estoque = estoqueModelDisassemblerDTO.toDomainObject(estoqueInputDTO);  // Converte o DTO para o modelo de domínio.
            Estoque estoqueAtual = estoqueService.buscarOuFalhar(estoqueId);  // Busca o item de estoque existente.
            BeanUtils.copyProperties(estoque, estoqueAtual, "id");  // Copia as propriedades do DTO para o objeto existente, exceto o ID.
            return estoqueModelAssemblerDTO.toModel(estoqueService.atualizar(estoqueAtual));  // Salva o item de estoque atualizado e converte para DTO.
        } catch (EstoqueNaoEncontradoException e) {  // Caso o item de estoque não seja encontrado, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Remove um item do estoque.
     * @param estoqueId ID do item a ser removido.
     */
    @DeleteMapping("/{estoqueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Define o código de status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long estoqueId) {
        estoqueService.excluir(estoqueId);  // Exclui o item do estoque.
    }

}