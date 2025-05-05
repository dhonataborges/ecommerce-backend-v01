package com.backend.ecommerce.core.security.crypto;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class CriptografiaUtils {

    // Chave secreta usada para criptografar e descriptografar.
    // Deve ter 16 caracteres para funcionar com AES (128 bits).
    // Em produção, essa chave não deve ficar fixa aqui no código. O ideal é carregá-la de uma variável de ambiente ou configuração externa segura.
    private static final String CHAVE_SECRETA = "MinhaChave123456";

    // Algoritmo de criptografia utilizado
    private static final String ALGORITMO = "AES";

    // Método para criptografar um texto
    public static String criptografar(String textoOriginal) {
        try {
            // Monta a chave AES com base na string definida acima
            SecretKeySpec chave = new SecretKeySpec(CHAVE_SECRETA.getBytes(), ALGORITMO);

            // Instancia o mecanismo de criptografia
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            // Configura o modo como criptografia (ENCRYPT)
            cipher.init(Cipher.ENCRYPT_MODE, chave);

            // Executa a criptografia e retorna em formato Base64 para facilitar o armazenamento
            byte[] textoCriptografado = cipher.doFinal(textoOriginal.getBytes());
            return Base64.getEncoder().encodeToString(textoCriptografado);

        } catch (Exception e) {
            // Em caso de erro, dispara uma exceção genérica
            throw new RuntimeException("Erro ao criptografar", e);
        }
    }

    // Método para descriptografar o texto criptografado
    public static String descriptografar(String textoCriptografado) {
        try {
            // Reconstrói a chave da mesma forma
            SecretKeySpec chave = new SecretKeySpec(CHAVE_SECRETA.getBytes(), ALGORITMO);

            // Instancia novamente o Cipher, agora para DECRYPT
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, chave);

            // Decodifica a string Base64 e restaura o texto original
            byte[] bytesDecodificados = Base64.getDecoder().decode(textoCriptografado);
            byte[] textoOriginal = cipher.doFinal(bytesDecodificados);

            return new String(textoOriginal);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar", e);
        }
    }
}
