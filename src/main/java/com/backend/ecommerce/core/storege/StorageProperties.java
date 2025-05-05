package com.backend.ecommerce.core.storege;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ecommerce.storage")
// Essa anotação diz: "Pega tudo do application.properties ou application.yml que começa com 'ecommerce.storage'"
public class StorageProperties {

    // Propriedades para armazenamento no Google Drive
    private Drive drive = new Drive();

    // Propriedades para armazenamento local
    private Local local = new Local();

    // ============================
    // Armazenamento local (no disco da máquina)
    // ============================
    @Getter
    @Setter
    public class Local {
        // Caminho para onde as fotos serão salvas localmente
        private Path diretorioFotos;
    }

    // ============================
    // Armazenamento no Google Drive
    // ============================
    @Getter
    @Setter
    public class Drive {
        // Caminho do arquivo JSON com as credenciais do Google (gerado na conta de serviço)
        private String credentialsPath;

        // Nome da aplicação que será mostrada nas requisições da API
        private String applicationName;

        // Caminho ou pasta no Google Drive onde as fotos vão ser salvas
        private String diretorioFotos;
    }
}
