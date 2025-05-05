package com.backend.ecommerce.infrastructure.repository.service.storage;

/**
 * Exceção customizada para representar falhas relacionadas a operações de armazenamento de arquivos.
 * Pode ser utilizada tanto para operações locais quanto em serviços externos (como Google Drive).
 */
public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Versão da classe para fins de serialização

    /**
     * Construtor que permite informar uma mensagem e a causa raiz da exceção.
     *
     * @param message mensagem explicativa sobre o erro ocorrido.
     * @param cause exceção original que causou o erro.
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor que permite informar apenas uma mensagem explicativa.
     *
     * @param message mensagem descrevendo o erro ocorrido.
     */
    public StorageException(String message) {
        super(message);
    }

}
