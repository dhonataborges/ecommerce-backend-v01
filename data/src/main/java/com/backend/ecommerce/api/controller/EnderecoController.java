package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.EnderecoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EnderecoModelDisassemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EstadoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EstadoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.EnderecoModelDTO;
import com.backend.ecommerce.api.modelDTO.EstadoModelDTO;
import com.backend.ecommerce.api.modelDTO.PedidoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.EnderecoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.EstadoInputDTO;
import com.backend.ecommerce.domain.exception.EnderecoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.EstadoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Endereco;
import com.backend.ecommerce.domain.model.Estado;
import com.backend.ecommerce.domain.model.Pedido;
import com.backend.ecommerce.domain.repository.EnderecoRepository;
import com.backend.ecommerce.domain.repository.EstadosRepository;
import com.backend.ecommerce.domain.service.EnderecoService;
import com.backend.ecommerce.domain.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define que esta classe é um controlador REST (para lidar com requisições HTTP).
@RequestMapping(value = "/endereco")  // Mapeamento de rota para o controlador de endereços.
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;  // Serviço de negócios para operações em endereços.

    @Autowired
    private EnderecoRepository enderecoRepository;  // Repositório para interação com o banco de dados.

    @Autowired
    private EnderecoModelAssemblerDTO enderecoModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO (Data Transfer Object).

    @Autowired
    private EnderecoModelDisassemblerDTO enderecoModelDisassemblerDTO;  // Responsável por converter o DTO para o modelo de domínio.

    /**
     * Retorna todos os endereços.
     * @return Lista de endereços convertidos para DTO.
     */
    @GetMapping
    public List<EnderecoModelDTO> listar() {
        return enderecoModelAssemblerDTO.toCollectionModel(enderecoRepository.findAll());  // Converte os endereços para o formato DTO.
    }

    /**
     * Retorna um endereço específico com base no ID.
     * @param enderecoId ID do endereço.
     * @return Endereço convertido para DTO.
     */
    @GetMapping("/{enderecoId}")
    public EnderecoModelDTO buscar(@PathVariable Long enderecoId) {
        Endereco endereco = enderecoService.buscarOuFalhar(enderecoId);  // Busca o endereço pelo ID.
        return enderecoModelAssemblerDTO.toModel(endereco);  // Converte o endereço para o formato DTO e retorna.
    }

    /**
     * Retorna os endereços de um cliente com base no ID do usuário.
     * @param usuarioId ID do cliente.
     * @return Lista de endereços do cliente, convertidos para DTO.
     */
    @GetMapping("/clientes/{usuarioId}/enderecos")
    public List<EnderecoModelDTO> buscarPorEnderecoClientePedidos(@PathVariable Long usuarioId) {
        List<Endereco> enderecos = enderecoRepository.buscarEnderecosPorUsuarioId(usuarioId);  // Busca os endereços do cliente pelo ID.
        return enderecos.stream()
                .map(enderecoModelAssemblerDTO::toModel)  // Converte cada endereço para DTO.
                .collect(Collectors.toList());  // Retorna a lista de endereços convertidos.
    }

    /**
     * Adiciona um novo endereço ao sistema.
     * @param enderecoInputDTO DTO com os dados do endereço a ser adicionado.
     * @return Endereço criado, convertido para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Define o código de status HTTP 201 (Criado) após a inserção.
    public EnderecoModelDTO adicionar(@RequestBody @Valid EnderecoInputDTO enderecoInputDTO) {
        try {
            Endereco endereco = enderecoModelDisassemblerDTO.toDomainObject(enderecoInputDTO);  // Converte o DTO para o modelo de domínio.
            return enderecoModelAssemblerDTO.toModel(enderecoService.salvar(endereco));  // Salva o endereço e converte para DTO.
        } catch (EnderecoNaoEncontradoException e) {  // Caso o endereço não seja encontrado, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Atualiza os dados de um endereço existente.
     * @param enderecoId ID do endereço a ser atualizado.
     * @param enderecoInputDTO DTO com os dados atualizados do endereço.
     * @return Endereço atualizado, convertido para DTO.
     */
    @PutMapping("/{enderecoId}")
    public EnderecoModelDTO atualizar(@PathVariable Long enderecoId, @RequestBody @Valid EnderecoInputDTO enderecoInputDTO) {
        try {
            Endereco endereco = enderecoModelDisassemblerDTO.toDomainObject(enderecoInputDTO);  // Converte o DTO para o modelo de domínio.
            Endereco enderecoAtual = enderecoService.buscarOuFalhar(enderecoId);  // Busca o endereço existente.
            BeanUtils.copyProperties(endereco, enderecoAtual, "id");  // Copia as propriedades do DTO para o objeto existente, exceto o ID.
            return enderecoModelAssemblerDTO.toModel(enderecoService.salvar(enderecoAtual));  // Salva o endereço atualizado e converte para DTO.
        } catch (EnderecoNaoEncontradoException e) {  // Caso o endereço não seja encontrado, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Remove um endereço do sistema.
     * @param enderecoId ID do endereço a ser removido.
     */
    @DeleteMapping("/{enderecoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Define o código de status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long enderecoId) {
        Endereco endereco = enderecoService.buscarOuFalhar(enderecoId);  // Busca o endereço pelo ID.
        enderecoService.excluir(enderecoId);  // Exclui o endereço do sistema.
    }

}