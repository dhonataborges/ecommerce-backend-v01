/*
package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assemblerDTO.TelefoneModelAssemblerDTO;
import com.backend.ecommerce.api.assemblerDTO.TelefoneModelDisassemblerDTO;
import com.backend.ecommerce.api.modelDTO.TelefoneModelDTO;
import com.backend.ecommerce.api.modelDTO.input.TelefoneInputDTO;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.TelefoneNaoEncontradoException;
import com.backend.ecommerce.domain.model.Telefone;
import com.backend.ecommerce.domain.repository.TelefoneRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/telefones")
public class TelefoneController {

    @Autowired
    private TelefoneService telefoneService;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private TelefoneModelAssemblerDTO telefoneModelAssemblerDTO;

    @Autowired
    private TelefoneModelDisassemblerDTO telefoneModelDisassemblerDTO;

    @GetMapping
    public List<TelefoneModelDTO> listar() {
        return telefoneModelAssemblerDTO.toCollectionModel(telefoneRepository.findAll());
    }

    @GetMapping("/{telefoneId}")
    public TelefoneModelDTO buscar(@PathVariable Long telefoneId) {

        Telefone telefone = telefoneService.buscarOuFalhar(telefoneId);

        return telefoneModelAssemblerDTO.toModel(telefone);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public TelefoneModelDTO adicionar(@RequestBody @Valid TelefoneInputDTO telefoneInputDTO) {
        try {
            Telefone telefone = telefoneModelDisassemblerDTO.toDomainObject(telefoneInputDTO);


            return telefoneModelAssemblerDTO.toModel(telefoneService.salvar(telefone));
        }catch (TelefoneNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{telefoneId}")
    public  TelefoneModelDTO atualizar(@PathVariable Long telefoneId, @RequestBody @Valid TelefoneInputDTO telefoneInputDTO) {
        try {
            Telefone telefone = telefoneModelDisassemblerDTO.toDomainObject(telefoneInputDTO);

            Telefone telefoneAtual = telefoneService.buscarOuFalhar(telefoneId);

            BeanUtils.copyProperties(telefone, telefoneAtual, "id");

            return telefoneModelAssemblerDTO.toModel(telefoneService.salvar(telefoneAtual));
        } catch (TelefoneNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{telefoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long telefoneId) {

        Telefone telefone = telefoneService.buscarOuFalhar(telefoneId);

        telefoneService.excluir(telefoneId);
    }

}
*/
