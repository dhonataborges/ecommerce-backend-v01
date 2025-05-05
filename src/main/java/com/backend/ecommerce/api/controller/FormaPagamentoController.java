package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.EstadoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.EstadoModelDisassemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.FormaPagamentoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.FormaPagamentoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.EstadoModelDTO;
import com.backend.ecommerce.api.modelDTO.FormaPagamentoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.EstadoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.FormaPagamentoInputDTO;
import com.backend.ecommerce.domain.exception.EstadoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.FormaPagamentoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Estado;
import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.repository.EstadosRepository;
import com.backend.ecommerce.domain.repository.FormaPagamentoRepository;
import com.backend.ecommerce.domain.service.EstadoService;
import com.backend.ecommerce.domain.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define que esta classe é um controlador REST (para tratar requisições HTTP).
@RequestMapping(value = "/forma-de-pagamento")  // Mapeamento da URL para o controlador de forma de pagamento.
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;  // Serviço de negócios para operações de forma de pagamento.

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;  // Repositório para interação com o banco de dados.

    @Autowired
    private FormaPagamentoModelAssemblerDTO formaPagamentoModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO (Data Transfer Object).

    @Autowired
    private FormaPagamentoModelDisassemblerDTO formaPagamentoModelDisassemblerDTO;  // Responsável por converter o DTO para o modelo de domínio.

    /**
     * Retorna todas as formas de pagamento.
     * @return Lista de formas de pagamento convertidas para DTO.
     */
    @GetMapping
    public List<FormaPagamentoModelDTO> listar() {
        return formaPagamentoModelAssemblerDTO.toCollectionModel(formaPagamentoRepository.findAll());  // Converte as formas de pagamento para o formato DTO e retorna.
    }

    /**
     * Retorna uma forma de pagamento específica com base no ID.
     * @param formaPagamentoId ID da forma de pagamento.
     * @return Forma de pagamento convertida para DTO.
     */
    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoModelDTO buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);  // Busca a forma de pagamento pelo ID.
        return formaPagamentoModelAssemblerDTO.toModel(formaPagamento);  // Converte a forma de pagamento para o formato DTO e retorna.
    }

    /**
     * Adiciona uma nova forma de pagamento.
     * @param formaPagamentoInputDTO DTO com os dados da forma de pagamento a ser adicionada.
     * @return Forma de pagamento criada, convertida para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Define o código de status HTTP 201 (Criado) após a inserção.
    public FormaPagamentoModelDTO adicionar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
        try {
            FormaPagamento formaPagamento = formaPagamentoModelDisassemblerDTO.toDomainObject(formaPagamentoInputDTO);  // Converte o DTO para o modelo de domínio.
            return formaPagamentoModelAssemblerDTO.toModel(formaPagamentoService.salvar(formaPagamento));  // Salva a forma de pagamento e converte para DTO.
        } catch (FormaPagamentoNaoEncontradoException e) {  // Caso a forma de pagamento não seja encontrada, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Atualiza os dados de uma forma de pagamento existente.
     * @param formaPagamentoId ID da forma de pagamento a ser atualizada.
     * @param formaPagamentoInputDTO DTO com os dados atualizados da forma de pagamento.
     * @return Forma de pagamento atualizada, convertida para DTO.
     */
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModelDTO atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
        try {
            FormaPagamento formaPagamento = formaPagamentoModelDisassemblerDTO.toDomainObject(formaPagamentoInputDTO);  // Converte o DTO para o modelo de domínio.
            FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(formaPagamentoId);  // Busca a forma de pagamento existente.
            BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual, "id");  // Copia as propriedades do DTO para o objeto existente, exceto o ID.
            return formaPagamentoModelAssemblerDTO.toModel(formaPagamentoService.salvar(formaPagamentoAtual));  // Salva a forma de pagamento atualizada e converte para DTO.
        } catch (FormaPagamentoNaoEncontradoException e) {  // Caso a forma de pagamento não seja encontrada, lança uma exceção personalizada.
            throw new NegocioException(e.getMessage());  // Lança uma exceção genérica de negócios.
        }
    }

    /**
     * Remove uma forma de pagamento.
     * @param formaPagamentoId ID da forma de pagamento a ser removida.
     */
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Define o código de status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.excluir(formaPagamentoId);  // Exclui a forma de pagamento.
    }

}
