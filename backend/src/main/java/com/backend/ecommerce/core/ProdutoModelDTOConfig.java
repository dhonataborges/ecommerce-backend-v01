package com.backend.ecommerce.core;

import com.backend.ecommerce.api.modelDTO.ProdutoModelDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoModelDTOConfig {

    @Bean
    public ProdutoModelDTO produtoModelSalvarDTO() {
        var produtoModelDTO = new ProdutoModelDTO();

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        return produtoModelDTO;
    }

}
