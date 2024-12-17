package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.repository.EstoqueRepository;
import com.backend.ecommerce.domain.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/teste")
public class TesteController {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public List<Estoque> listar() {
        return estoqueRepository.findAll();
    }

    /*@GetMapping("/produto-em-estoque/perfumaria")
    public List<Estoque> buscarCosmeticos(@RequestParam("tipoProdEstoque") Long tipoProd) {
        return estoqueRepository.buscarTipoProd(Math.toIntExact(tipoProd));
    }*/

}
