package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.CidadeModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.CidadeModelDisassemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EstadoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EstadoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.CidadeModelDTO;
import com.backend.ecommerce.api.modelDTO.EstadoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.CidadeInputDTO;
import com.backend.ecommerce.api.modelDTO.input.EstadoInputDTO;
import com.backend.ecommerce.domain.exception.CidadeNaoEncontradoException;
import com.backend.ecommerce.domain.exception.EstadoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Cidade;
import com.backend.ecommerce.domain.model.Estado;
import com.backend.ecommerce.domain.repository.CidadeRepository;
import com.backend.ecommerce.domain.repository.EstadosRepository;
import com.backend.ecommerce.domain.service.CidadeService;
import com.backend.ecommerce.domain.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Indica que esta classe é um controlador REST (para lidar com requisições HTTP).
@RequestMapping(value = "/cidade")  // Mapeamento de rota para o controlador de cidades.
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;  // Serviço de negócios para operações em cidades.

    @Autowired
    private CidadeRepository cidadeRepository;  // Repositório para interação com o banco de dados.

    @Autowired
    private CidadeModelAssemblerDTO cidadeModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO (Data Transfer Object).

    @Autowired
    private CidadeModelDisassemblerDTO cidadeModelDisassemblerDTO;  // Responsável por converter o DTO para o modelo de domínio.

    /**
     * Retorna todas as cidades.
     * @return Lista de cidades convertidas para DTO.
     */
    @GetMapping
    public List<CidadeModelDTO> listar() {
        return cidadeModelAssemblerDTO.toCollectionModel(cidadeRepository.findAll());  // Converte as cidades para o formato DTO.
    }

    /**
     * Retorna uma cidade específica com base no ID.
     * @param cidadeId ID da cidade.
     * @return Cidade convertida para DTO.
     */
    @GetMapping("/{cidadeId}")
    public CidadeModelDTO buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);  // Busca a cidade pelo ID.
        return cidadeModelAssemblerDTO.toModel(cidade);  // Converte a cidade para o formato DTO e retorna.
    }

    /**
     * Adiciona uma nova cidade ao sistema.
     * @param cidadeInputDTO DTO com os dados da cidade a ser adicionada.
     * @return Cidade criada, convertida para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Define o código de status HTTP 201 (Criado) após a inserção.
    public CidadeModelDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidade = cidadeModelDisassemblerDTO.toDomainObject(cidadeInputDTO);  // Converte o DTO para o modelo de domínio.
            return cidadeModelAssemblerDTO.toModel(cidadeService.salvar(cidade));  // Salva a cidade e converte para DTO.
        } catch (CidadeNaoEncontradoException e) {  // Caso a cidade não seja encontrada, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Atualiza os dados de uma cidade existente.
     * @param cidadeId ID da cidade a ser atualizada.
     * @param cidadeInputDTO DTO com os dados atualizados da cidade.
     * @return Cidade atualizada, convertida para DTO.
     */
    @PutMapping("/{cidadeId}")
    public CidadeModelDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidade = cidadeModelDisassemblerDTO.toDomainObject(cidadeInputDTO);  // Converte o DTO para o modelo de domínio.
            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);  // Busca a cidade existente.
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");  // Copia as propriedades do DTO para o objeto existente, exceto o ID.
            return cidadeModelAssemblerDTO.toModel(cidadeService.salvar(cidadeAtual));  // Salva a cidade atualizada e converte para DTO.
        } catch (CidadeNaoEncontradoException e) {  // Caso a cidade não seja encontrada, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Remove uma cidade do sistema.
     * @param cidadeId ID da cidade a ser removida.
     */
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Define o código de status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);  // Busca a cidade pelo ID.
        cidadeService.excluir(cidadeId);  // Exclui a cidade do sistema.
    }

}