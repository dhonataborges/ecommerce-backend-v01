package com.backend.ecommerce.core;

import com.backend.ecommerce.api.modelDTO.ProdutoModelDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Classe de configuração responsável por definir beans do tipo DTO e mapeamentos, se necessário.
@Configuration
public class ProdutoModelDTOConfig {

    /**
     * Este método fornece uma instância de ProdutoModelDTO como bean.
     *
     * ⚠️ Observação: geralmente, DTOs são instanciados diretamente onde são usados.
     * Só faça isso se o DTO tiver configurações fixas que serão reutilizadas.
     */
    @Bean
    public ProdutoModelDTO produtoModelDTO() {
        return new ProdutoModelDTO();
    }

    /*
    // Exemplo de configuração de um bean do ModelMapper com mapeamento customizado.
    // Descomente se for utilizar o ModelMapper no projeto.

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configura um mapeamento personalizado entre Restaurante e RestauranteModel,
        // mapeando taxaFrete -> precoFrete (caso os nomes sejam diferentes).
        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
            .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        return modelMapper;
    }
    */
}
