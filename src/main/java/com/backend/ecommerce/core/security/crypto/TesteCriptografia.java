package com.backend.ecommerce.core.security.crypto;

public class TesteCriptografia {

    public static void main(String[] args) {
        String numeroCartao = "4111111111111111"; // Número de exemplo

        // Criptografando
        String criptografado = CriptografiaUtils.criptografar(numeroCartao);
        System.out.println("Criptografado: " + criptografado);

        // Descriptografando
        String descriptografado = CriptografiaUtils.descriptografar(criptografado);
        System.out.println("Descriptografado: " + descriptografado);
    }
}