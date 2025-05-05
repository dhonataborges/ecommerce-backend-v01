/*
package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.TelefoneNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.model.Telefone;
import com.backend.ecommerce.domain.repository.TelefoneRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// @Service
// public class TelefoneService {
//     private static final String MSG_TELEFONE_EM_USO = "Telefone %d não pode ser removido, pois está em uso";

//     // Repositório para interagir com o banco de dados de telefones
//     // @Autowired
//     private TelefoneRepository telefoneRepository;

//     // Serviço para interagir com os clientes
//     // @Autowired
//     private ClienteService clienteService;

//     /**
//      * Salva um novo telefone associado a um cliente.
//      *
//      * @param telefone O telefone a ser salvo.
//      * @return O telefone salvo, após ser persistido no banco de dados.
//      */
//     // @Transactional
//     public Telefone salvar(@Valid Telefone telefone) {

//         // Obtém o ID do cliente associado ao telefone
//         Long clienteId = telefone.getCliente().getId();

//         // Busca o cliente no banco de dados
//         Cliente cliente = clienteService.buscarOuFalhar(clienteId);

//         // Associa o cliente ao telefone
//         telefone.setCliente(cliente);

//         // Salva e retorna o telefone associado ao cliente
//         return telefoneRepository.save(telefone);
//     }

//     /**
//      * Exclui um telefone do banco de dados.
//      *
//      * @param telefoneId O ID do telefone a ser excluído.
//      */
//     // @Transactional
//     public void excluir(Long telefoneId) {
//         try {
//             // Tenta excluir o telefone com o ID fornecido
//             telefoneRepository.deleteById(telefoneId);
//         } catch (EmptyResultDataAccessException e) {
//             // Lança uma exceção caso o telefone não seja encontrado no banco
//             throw new TelefoneNaoEncontradoException(telefoneId);

//         } catch (DataIntegrityViolationException e) {
//             // Lança uma exceção caso haja violação de integridade (telefone em uso)
//             throw new EntidadeEmUsoException(String.format(MSG_TELEFONE_EM_USO, telefoneId));
//         }
//     }

//     /**
//      * Busca um telefone pelo ID. Caso o telefone não seja encontrado, lança uma exceção.
//      *
//      * @param telefoneId O ID do telefone a ser buscado.
//      * @return O telefone encontrado.
//      */
//     public Telefone buscarOuFalhar(Long telefoneId) {
//         // Retorna o telefone encontrado ou lança uma exceção caso não encontrado
//         return telefoneRepository.findById(telefoneId).orElseThrow(() -> new TelefoneNaoEncontradoException(telefoneId));
//     }
// }
