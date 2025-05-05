package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.PedidoItemModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.PedidoItemModelDisassemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.PedidoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.PedidoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.PedidoItemModelDTO;
import com.backend.ecommerce.api.modelDTO.PedidoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.PedidoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PedidoItemInputDTO;
import com.backend.ecommerce.domain.exception.ClienteNaoEncontradoException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.PedidoItemNaoEncontradoException;
import com.backend.ecommerce.domain.model.CatalogoProduto;
import com.backend.ecommerce.domain.model.Pedido;
import com.backend.ecommerce.domain.model.PedidoItem;
import com.backend.ecommerce.domain.repository.PedidoItemRepository;
import com.backend.ecommerce.domain.repository.PedidoRepository;
import com.backend.ecommerce.domain.service.CatalogoProdutoService;
import com.backend.ecommerce.domain.service.PedidoItemService;
import com.backend.ecommerce.domain.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define que esta classe é um controlador REST para tratar requisições HTTP.
@RequestMapping(value = "/pedido-item")  // Mapeia a URL base para o controlador de itens de pedidos.
public class PedidoItemController {

    @Autowired
    private PedidoItemService pedidoItemService;  // Serviço de negócios para operações relacionadas a itens de pedidos.

    @Autowired
    private PedidoService pedidoService;  // Serviço de negócios para operações relacionadas a pedidos.

    @Autowired
    private CatalogoProdutoService catalogoProdutoService;  // Serviço de negócios para operações relacionadas ao catálogo de produtos.

    @Autowired
    private PedidoItemRepository pedidoItemRepository;  // Repositório que interage diretamente com o banco de dados de itens de pedidos.

    @Autowired
    private PedidoItemModelAssemblerDTO pedidoItemModelAssemblerDTO;  // Converte o modelo de domínio de pedido item para DTO.

    @Autowired
    private PedidoItemModelDisassemblerDTO pedidoItemModelDisassemblerDTO;  // Converte o DTO de pedido item para o modelo de domínio.

    /**
     * Retorna a lista de todos os itens de pedido registrados.
     * @return Lista de itens de pedido convertida para DTO.
     */
    @GetMapping
    public List<PedidoItemModelDTO> listar() {
        return pedidoItemModelAssemblerDTO.toCollectionModel(pedidoItemRepository.findAll());  // Busca todos os itens de pedido no repositório e converte para DTO.
    }

    /**
     * Retorna um item de pedido específico pelo ID.
     * @param pedidoItemId ID do item de pedido.
     * @return Item de pedido convertido para DTO.
     */
    @GetMapping("/{pedidoItemId}")
    public PedidoItemModelDTO buscar(@PathVariable Long pedidoItemId) {
        PedidoItem pedidoItem = pedidoItemService.buscarOuFalhar(pedidoItemId);  // Busca o item de pedido pelo ID.
        return pedidoItemModelAssemblerDTO.toModel(pedidoItem);  // Converte o item de pedido para DTO e retorna.
    }

    /**
     * Adiciona um novo item de pedido.
     * @param pedidoItemInputDTO DTO contendo os dados do item de pedido a ser adicionado.
     * @return Item de pedido adicionado convertido para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Retorna o status HTTP 201 (Criado).
    public PedidoItemModelDTO adicionar(@RequestBody @Valid PedidoItemInputDTO pedidoItemInputDTO) {
        try {
            PedidoItem pedidoItem = pedidoItemModelDisassemblerDTO.toDomainObject(pedidoItemInputDTO);  // Converte o DTO para o modelo de domínio.

            return pedidoItemModelAssemblerDTO.toModel(pedidoItemService.salvar(pedidoItem));  // Salva o item de pedido e converte para DTO.
        } catch (PedidoItemNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Caso o item de pedido não seja encontrado, lança uma exceção de negócio.
        }
    }

    /**
     * Atualiza um item de pedido existente.
     * @param pedidoItemId ID do item de pedido a ser atualizado.
     * @param pedidoItemInputDTO DTO contendo os dados para atualização.
     * @return Item de pedido atualizado convertido para DTO.
     */
    @PutMapping("/{pedidoItemId}")
    public PedidoItemModelDTO atualizar(@PathVariable Long pedidoItemId, @RequestBody @Valid PedidoItemInputDTO pedidoItemInputDTO) {
        try {
            PedidoItem pedidoItem = pedidoItemModelDisassemblerDTO.toDomainObject(pedidoItemInputDTO);  // Converte o DTO para o modelo de domínio.

            PedidoItem pedidoItemAtual = pedidoItemService.buscarOuFalhar(pedidoItemId);  // Busca o item de pedido existente.

            BeanUtils.copyProperties(pedidoItem, pedidoItemAtual, "id");  // Copia as propriedades do novo item para o item atual.

            return pedidoItemModelAssemblerDTO.toModel(pedidoItemService.salvar(pedidoItemAtual));  // Salva o item de pedido atualizado e converte para DTO.
        } catch (PedidoItemNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Caso o item de pedido não seja encontrado, lança uma exceção de negócio.
        }
    }

    /**
     * Atualiza parcialmente um item de pedido.
     * @param pedidoItemId ID do item de pedido a ser atualizado parcialmente.
     * @param pedidoItemInputDTO DTO contendo os dados para a atualização parcial.
     * @return Item de pedido atualizado parcialmente convertido para DTO.
     */
    @PatchMapping("/{pedidoItemId}")
    public PedidoItemModelDTO atualizarParcial(@PathVariable Long pedidoItemId, @RequestBody @Valid PedidoItemInputDTO pedidoItemInputDTO) {
        try {

            PedidoItem pedidoItem = pedidoItemModelDisassemblerDTO.toDomainObject(pedidoItemInputDTO);  // Converte o DTO para o modelo de domínio.

            PedidoItem pedidoItemAtual = pedidoItemService.buscarOuFalhar(pedidoItemId);  // Busca o item de pedido existente.

            BeanUtils.copyProperties(pedidoItem, "id");  // Copia as propriedades do DTO para o item atual, exceto o ID.

            return pedidoItemModelAssemblerDTO.toModel(pedidoItemService.salvar(pedidoItem));  // Salva a atualização e converte para DTO.
        } catch (ClienteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Caso o cliente não seja encontrado, lança uma exceção de negócio.
        }
    }

    /**
     * Atualiza a quantidade de um item de pedido.
     * @param id ID do item de pedido a ser atualizado.
     * @param pedidoItemInputDTO DTO contendo a nova quantidade do item de pedido.
     * @return Item de pedido com a quantidade atualizada convertido para DTO.
     */
    @PatchMapping("/quantidade/{id}")
    public PedidoItemModelDTO atualizarQuantidade(@PathVariable Long id, @RequestBody PedidoItemInputDTO pedidoItemInputDTO) {
        int novaQuantidade = pedidoItemInputDTO.getQuantidade();  // Obtém a nova quantidade do DTO.

        PedidoItem pedidoItemQtdAtualizada = pedidoItemService.atualizarQuantidade(id, novaQuantidade);  // Atualiza a quantidade do item de pedido.

        return pedidoItemModelAssemblerDTO.toModel(pedidoItemQtdAtualizada);  // Retorna o item de pedido atualizado convertido para DTO.
    }

    /**
     * Remove um item de pedido específico pelo ID.
     * @param pedidoItemId ID do item de pedido a ser removido.
     */
    @DeleteMapping("/{pedidoItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Retorna o status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long pedidoItemId) {

        PedidoItem pedidoItem = pedidoItemService.buscarOuFalhar(pedidoItemId);  // Busca o item de pedido pelo ID.

        pedidoItemService.excluir(pedidoItemId);  // Exclui o item de pedido.
    }
}