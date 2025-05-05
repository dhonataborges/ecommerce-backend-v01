package com.backend.ecommerce.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
// Indica que esta classe é uma classe de configuração do Spring.
// Ela será processada pelo Spring Container para registrar beans.
@Configuration
public class ValidationConfig {

    /**
     * Define um bean do tipo LocalValidatorFactoryBean, que será responsável
     * por realizar validações em toda a aplicação com suporte a mensagens customizadas.
     *
     * @param messageSource o bean que fornece as mensagens traduzidas ou personalizadas,
     *                      geralmente configurado como ReloadableResourceBundleMessageSource.
     * @return uma instância de LocalValidatorFactoryBean configurada com suporte a i18n.
     */
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        // Cria a fábrica de validadores com suporte à especificação Bean Validation (JSR-380).
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

        // Define a fonte de mensagens para validação, permitindo internacionalização
        // e personalização de mensagens com arquivos messages.properties.
        bean.setValidationMessageSource(messageSource);

        // Retorna o bean para ser gerenciado pelo Spring.
        return bean;
    }
}
