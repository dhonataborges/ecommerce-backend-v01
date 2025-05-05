package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.FormaPagamentoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.FormaPagamentoModelDisassemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.PagamentoModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.PagamentoModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.FormaPagamentoModelDTO;
import com.backend.ecommerce.api.modelDTO.PagamentoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.FormaPagamentoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoInputDTO;
import com.backend.ecommerce.domain.exception.FormaPagamentoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.PagamentoNaoEncontradoException;
import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.model.Pagamento;
import com.backend.ecommerce.domain.repository.FormaPagamentoRepository;
import com.backend.ecommerce.domain.repository.PagamentoRepository;
import com.backend.ecommerce.domain.service.FormaPagamentoService;
import com.backend.ecommerce.domain.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")  // Permite que qualquer origem (domínio) acesse essa API.
@RestController  // Define que esta classe é um controlador REST (para tratar requisições HTTP).
@RequestMapping(value = "/pagamento")  // Mapeamento da URL base para o controlador de pagamento.
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;  // Serviço de negócios para operações relacionadas ao pagamento.

    @Autowired
    private PagamentoRepository pagamentoRepository;  // Repositório para interação com o banco de dados de pagamentos.

    @Autowired
    private PagamentoModelAssemblerDTO pagamentoModelAssemblerDTO;  // Responsável por converter o modelo de domínio para o DTO (Data Transfer Object).

    @Autowired
    private PagamentoModelDisassemblerDTO pagamentoModelDisassemblerDTO;  // Responsável por converter o DTO para o modelo de domínio.

    /**
     * Retorna uma lista de todos os pagamentos registrados.
     * @return Lista de pagamentos convertida para DTO.
     */
    @GetMapping
    public List<PagamentoModelDTO> listar() {
        return pagamentoModelAssemblerDTO.toCollectionModel(pagamentoRepository.findAll());  // Busca todos os pagamentos no repositório e converte para DTO.
    }

    /**
     * Retorna um pagamento específico pelo ID.
     * @param pagamentoId ID do pagamento.
     * @return Pagamento convertido para DTO.
     */
    @GetMapping("/{pagamentoId}")
    public PagamentoModelDTO buscar(@PathVariable Long pagamentoId) {

        Pagamento pagamento = pagamentoService.buscarOuFalhar(pagamentoId);  // Busca o pagamento pelo ID.

        return pagamentoModelAssemblerDTO.toModel(pagamento);  // Converte o pagamento para o formato DTO e retorna.
    }

    /**
     * Adiciona um novo pagamento.
     * @param pagamentoInputDTO DTO contendo os dados do pagamento a ser adicionado.
     * @return Pagamento adicionado convertido para DTO.
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)  // Retorna o status HTTP 201 (Criado).
    public PagamentoModelDTO adicionar(@RequestBody @Valid PagamentoInputDTO pagamentoInputDTO) {
        try {
            Pagamento pagamento = pagamentoModelDisassemblerDTO.toDomainObject(pagamentoInputDTO);  // Converte o DTO para o modelo de domínio.

            return pagamentoModelAssemblerDTO.toModel(pagamentoService.salvar(pagamento));  // Salva o pagamento e converte para DTO.
        } catch (PagamentoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Caso o pagamento não seja encontrado, lança uma exceção de negócio.
        }
    }

    /**
     * Atualiza os dados de um pagamento existente.
     * @param pagamentoId ID do pagamento a ser atualizado.
     * @param pagamentoInputDTO DTO contendo os novos dados para o pagamento.
     * @return Pagamento atualizado convertido para DTO.
     */
    @PutMapping("/{pagamentoId}")
    public PagamentoModelDTO atualizar(@PathVariable Long pagamentoId, @RequestBody @Valid PagamentoInputDTO pagamentoInputDTO) {
        try {
            Pagamento pagamento = pagamentoModelDisassemblerDTO.toDomainObject(pagamentoInputDTO);  // Converte o DTO para o modelo de domínio.

            Pagamento pagamentoAtual = pagamentoService.buscarOuFalhar(pagamentoId);  // Busca o pagamento existente.

            BeanUtils.copyProperties(pagamento, pagamentoAtual, "id");  // Copia as propriedades do novo pagamento para o pagamento atual.

            return pagamentoModelAssemblerDTO.toModel(pagamentoService.salvar(pagamentoAtual));  // Salva o pagamento atualizado e converte para DTO.
        } catch (PagamentoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());  // Caso o pagamento não seja encontrado, lança uma exceção de negócio.
        }
    }

    /**
     * Remove um pagamento específico pelo ID.
     * @param pagamentoId ID do pagamento a ser removido.
     */
    @DeleteMapping("/{pagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Retorna o status HTTP 204 (Sem Conteúdo) após a exclusão.
    public void remover(@PathVariable Long pagamentoId) {

        Pagamento pagamento = pagamentoService.buscarOuFalhar(pagamentoId);  // Busca o pagamento pelo ID.

        pagamentoService.excluir(pagamentoId);  // Exclui o pagamento.
    }

}