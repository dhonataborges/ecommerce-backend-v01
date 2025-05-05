package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.EstadoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EstadoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.EstadoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.EstadoInputDTO;
import com.backend.ecommerce.domain.exception.EstadoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Estado;
import com.backend.ecommerce.domain.repository.EstadosRepository;
import com.backend.ecommerce.domain.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define que esta classe é um controlador REST (para lidar com requisições HTTP).
@RequestMapping(value = "/estados")  // Mapeamento de rota para o controlador de estados.
public class EstadoController {

    @Autowired
    private EstadoService estadoService;  // Serviço de negócios para operações em estados.

    @Autowired
    private EstadosRepository estadosRepository;  // Repositório para interação com o banco de dados.

    @Autowired
    private EstadoModelAssemblerDTO estadoModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO (Data Transfer Object).

    @Autowired
    private EstadoModelDisassemblerDTO estadoModelDisassemblerDTO;  // Responsável por converter o DTO para o modelo de domínio.

    /**
     * Retorna todos os estados.
     * @return Lista de estados convertidos para DTO.
     */
    @GetMapping
    public List<EstadoModelDTO> listar() {
        return estadoModelAssemblerDTO.toCollectionModel(estadosRepository.findAll());  // Converte os estados para o formato DTO.
    }

    /**
     * Retorna um estado específico com base no ID.
     * @param estadoId ID do estado.
     * @return Estado convertido para DTO.
     */
    @GetMapping("/{estadoId}")
    public EstadoModelDTO buscar(@PathVariable Long estadoId) {
        Estado estado = estadoService.buscarOuFalhar(estadoId);  // Busca o estado pelo ID.
        return estadoModelAssemblerDTO.toModel(estado);  // Converte o estado para o formato DTO e retorna.
    }

    /**
     * Adiciona um novo estado ao sistema.
     * @param estadoInputDTO DTO com os dados do estado a ser adicionado.
     * @return Estado criado, convertido para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Define o código de status HTTP 201 (Criado) após a inserção.
    public EstadoModelDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        try {
            Estado estado = estadoModelDisassemblerDTO.toDomainObject(estadoInputDTO);  // Converte o DTO para o modelo de domínio.
            return estadoModelAssemblerDTO.toModel(estadoService.salvar(estado));  // Salva o estado e converte para DTO.
        } catch (EstadoNaoEncontradoException e) {  // Caso o estado não seja encontrado, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Atualiza os dados de um estado existente.
     * @param estadoId ID do estado a ser atualizado.
     * @param estadoInputDTO DTO com os dados atualizados do estado.
     * @return Estado atualizado, convertido para DTO.
     */
    @PutMapping("/{estadoId}")
    public EstadoModelDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        try {
            Estado estado = estadoModelDisassemblerDTO.toDomainObject(estadoInputDTO);  // Converte o DTO para o modelo de domínio.
            Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);  // Busca o estado existente.
            BeanUtils.copyProperties(estado, estadoAtual, "id");  // Copia as propriedades do DTO para o objeto existente, exceto o ID.
            return estadoModelAssemblerDTO.toModel(estadoService.salvar(estadoAtual));  // Salva o estado atualizado e converte para DTO.
        } catch (EstadoNaoEncontradoException e) {  // Caso o estado não seja encontrado, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Remove um estado do sistema.
     * @param estadoId ID do estado a ser removido.
     */
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Define o código de status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long estadoId) {
        Estado estado = estadoService.buscarOuFalhar(estadoId);  // Busca o estado pelo ID.
        estadoService.excluir(estadoId);  // Exclui o estado do sistema.
    }

}