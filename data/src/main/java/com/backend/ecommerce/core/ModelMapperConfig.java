package com.backend.ecommerce.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Indica ao Spring que esta classe contém definições de beans.
@Configuration
public class ModelMapperConfig {

    /**
     * Define um bean singleton do ModelMapper que pode ser injetado em qualquer componente do Spring.
     *
     * @return uma instância configurada de ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        // Cria uma nova instância do ModelMapper
        var modelMapper = new ModelMapper();

        /*
        // Exemplo de mapeamento customizado entre duas classes com nomes de atributos diferentes.
        // Aqui, taxaFrete da entidade Restaurante será mapeado para precoFrete no DTO RestauranteModel.
        //
        // Isso é útil quando não há correspondência direta entre os nomes dos atributos
        // ou quando é necessário converter/ajustar dados entre os objetos.

        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
            .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
        */

        // Retorna o bean do ModelMapper configurado
        return modelMapper;
    }
}
