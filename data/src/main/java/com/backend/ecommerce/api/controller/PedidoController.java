package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.PedidoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.PedidoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.EnderecoModelDTO;
import com.backend.ecommerce.api.modelDTO.PedidoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.PedidoInputDTO;
import com.backend.ecommerce.domain.exception.ClienteNaoEncontradoException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Pedido;
import com.backend.ecommerce.domain.repository.PedidoRepository;
import com.backend.ecommerce.domain.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define que esta classe é um controlador REST (para tratar requisições HTTP).
@RequestMapping(value = "/pedido")  // Mapeamento da URL base para o controlador de pedidos.
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;  // Serviço de negócios para operações relacionadas ao pedido.

    @Autowired
    private PedidoRepository pedidoRepository;  // Repositório para interação com o banco de dados de pedidos.

    @Autowired
    private PedidoModelAssemblerDTO pedidoModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO (Data Transfer Object).

    @Autowired
    private PedidoModelDisassemblerDTO pedidoModelDisassemblerDTO;  // Responsável por converter o DTO para o modelo de domínio.

    /**
     * Retorna uma lista de todos os pedidos registrados.
     * @return Lista de pedidos convertida para DTO.
     */
    @GetMapping
    public List<PedidoModelDTO> listar() {
        return pedidoModelAssemblerDTO.toCollectionModel(pedidoRepository.findAll());  // Busca todos os pedidos no repositório e converte para DTO.
    }

    /**
     * Retorna o endereço de um pedido específico.
     * @param pedidoId ID do pedido.
     * @return Endereço do pedido convertido para DTO.
     */
    @GetMapping("/teste-endereco/{pedidoId}")
    public EnderecoModelDTO testeEndereco(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);  // Busca o pedido pelo ID.
        return pedidoModelAssemblerDTO.toModel(pedido).getEndereco();  // Retorna o endereço do pedido.
    }

    /**
     * Retorna um pedido específico pelo ID.
     * @param pedidoId ID do pedido.
     * @return Pedido convertido para DTO.
     */
    @GetMapping("/{pedidoId}")
    public PedidoModelDTO buscar(@PathVariable Long pedidoId) {

        Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);  // Busca o pedido pelo ID.

        return pedidoModelAssemblerDTO.toModel(pedido);  // Converte o pedido para o formato DTO e retorna.
    }

    /**
     * Retorna a lista de pedidos de um cliente específico.
     * @param clienteId ID do cliente.
     * @return Lista de pedidos do cliente convertida para DTO.
     */
    @GetMapping("/clientes/{clienteId}/pedidos")
    public List<PedidoModelDTO> buscarPorCliente(@PathVariable Long clienteId) {

        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(clienteId);  // Busca todos os pedidos do cliente.

        return pedidos.stream()
                .map(pedidoModelAssemblerDTO::toModel)  // Converte cada pedido para DTO.
                .collect(Collectors.toList());
    }

    /**
     * Adiciona um novo pedido.
     * @param pedidoInputDTO DTO contendo os dados do pedido a ser adicionado.
     * @return Pedido adicionado convertido para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Retorna o status HTTP 201 (Criado).
    public PedidoModelDTO adicionar(@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        try {
            Pedido pedido = pedidoModelDisassemblerDTO.toDomainObject(pedidoInputDTO);  // Converte o DTO para o modelo de domínio.

            return pedidoModelAssemblerDTO.toModel(pedidoService.salvar(pedido));  // Salva o pedido e converte para DTO.
        } catch (ClienteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Caso o cliente não seja encontrado, lança uma exceção de negócio.
        }
    }

    /**
     * Atualiza os dados de um pedido existente.
     * @param pedidoId ID do pedido a ser atualizado.
     * @param pedidoInputDTO DTO contendo os novos dados para o pedido.
     * @return Pedido atualizado convertido para DTO.
     */
    @PutMapping("/{pedidoId}")
    public PedidoModelDTO atualizar(@PathVariable Long pedidoId, @RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        try {
            Pedido pedido = pedidoModelDisassemblerDTO.toDomainObject(pedidoInputDTO);  // Converte o DTO para o modelo de domínio.

            Pedido pedidoAtual = pedidoService.buscarOuFalhar(pedidoId);  // Busca o pedido existente.

            BeanUtils.copyProperties(pedido, pedidoAtual, "id");  // Copia as propriedades do novo pedido para o pedido atual.

            return pedidoModelAssemblerDTO.toModel(pedidoService.salvar(pedidoAtual));  // Salva o pedido atualizado e converte para DTO.
        } catch (ClienteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Caso o cliente não seja encontrado, lança uma exceção de negócio.
        }
    }

    /**
     * Atualiza parcialmente um pedido, permitindo a modificação apenas de alguns campos (exemplo: endereço).
     * @param pedidoId ID do pedido a ser atualizado parcialmente.
     * @param pedidoInputDTO DTO contendo os dados para a atualização parcial do pedido.
     * @return Pedido atualizado parcialmente convertido para DTO.
     */
    @PatchMapping("/{pedidoId}")
    public PedidoModelDTO atualizarParcial(@PathVariable Long pedidoId, @RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        try {

            Pedido pedidoAtual = pedidoService.buscarOuFalhar(pedidoId);  // Busca o pedido existente.

            // Atualiza apenas o endereço com base no DTO
            pedidoAtual.setEndereco(pedidoModelDisassemblerDTO.toDomainObject(pedidoInputDTO).getEndereco());

            return pedidoModelAssemblerDTO.toModel(pedidoService.atualizarPedidoComEndereco(pedidoAtual));  // Salva a atualização e converte para DTO.

        } catch (ClienteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Caso o cliente não seja encontrado, lança uma exceção de negócio.
        }
    }

    /**
     * Remove um pedido específico pelo ID.
     * @param pedidoId ID do pedido a ser removido.
     */
    @DeleteMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Retorna o status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long pedidoId) {

        Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);  // Busca o pedido pelo ID.

        pedidoService.excluir(pedidoId);  // Exclui o pedido.
    }

}