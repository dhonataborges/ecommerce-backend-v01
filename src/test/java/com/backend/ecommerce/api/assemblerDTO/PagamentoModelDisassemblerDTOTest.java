package com.backend.ecommerce.api.assemblerDTO;

import com.backend.ecommerce.api.modelDTO.input.PagamentoBoletoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoCartaoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PagamentoPixInputDTO;
import com.backend.ecommerce.core.security.crypto.CriptografiaUtils;
import com.backend.ecommerce.domain.model.DadosCartao;
import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.model.Pagamento;
import com.backend.ecommerce.domain.model.enuns.StatusPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoModelDisassemblerDTOTest {

    private PagamentoModelDisassemblerDTO disassemblerDTO;

    @BeforeEach
    void setUp() {
        disassemblerDTO = new PagamentoModelDisassemblerDTO();
        CriptografiaUtils criptografiaUtils = new CriptografiaUtils();
        ReflectionTestUtils.setField(disassemblerDTO, "criptografiaUtils", criptografiaUtils);
    }

    // Testa a criptografia de dados do cartão
    @Test
    void deveCriptografarDadosDoCartao() {
        // Arrange
        PagamentoCartaoInputDTO cartaoInputDTO = new PagamentoCartaoInputDTO();
        cartaoInputDTO.setId(1L);
        cartaoInputDTO.setNomeTitular("João da Silva");
        cartaoInputDTO.setNumeroCartao("1234567812345678");
        cartaoInputDTO.setCvv("123");
        cartaoInputDTO.setValidade("12/25");

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo("CARTAO");
        cartaoInputDTO.setFormaPagamento(formaPagamento);

        // Act
        Pagamento pagamento = disassemblerDTO.toDomainObject(cartaoInputDTO);

        // Assert
        DadosCartao dadosCartao = pagamento.getDadosCartao();
        assertNotNull(dadosCartao);
        assertEquals("João da Silva", dadosCartao.getNomeTitular());
        assertEquals("12/25", dadosCartao.getValidade());

        // Verifica se o número do cartão e o CVV foram criptografados
        assertNotEquals("1234567812345678", dadosCartao.getNumeroCartao());
        assertNotEquals("123", dadosCartao.getCvv());

        // Verifica se os dados estão criptografados (não vazios)
        assertFalse(dadosCartao.getNumeroCartao().isEmpty());
        assertFalse(dadosCartao.getCvv().isEmpty());
    }

    // Testa a criação dos dados do Pix
    @Test
    void deveMapearDadosDoPixCorretamente() {
        PagamentoPixInputDTO pixDTO = new PagamentoPixInputDTO();
        pixDTO.setId(2L);
        pixDTO.setValor(new BigDecimal("50.00"));
        pixDTO.setStatus(StatusPagamento.APROVADO);
        pixDTO.setDataPagamento(LocalDate.now());

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo("PIX");
        pixDTO.setFormaPagamento(formaPagamento);

        pixDTO.setChavePix("cliente@email.com");

        Pagamento pagamento = disassemblerDTO.toDomainObject(pixDTO);

        assertNotNull(pagamento.getDadosPix());
        assertEquals("cliente@email.com", pagamento.getDadosPix().getChavePix());
    }

    // Testa a criação dos dados do Boleto
    @Test
    void deveMapearDadosDoBoletoCorretamente() {
        PagamentoBoletoInputDTO boletoDTO = new PagamentoBoletoInputDTO();
        boletoDTO.setId(3L);
        boletoDTO.setValor(new BigDecimal("75.00"));
        boletoDTO.setStatus(StatusPagamento.APROVADO);
        boletoDTO.setDataPagamento(LocalDate.now());

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo("BOLETO");
        boletoDTO.setFormaPagamento(formaPagamento);

        boletoDTO.setCodigoBoleto("23793.38127 60002.345678 91000.100009 1 90000000010000");

        Pagamento pagamento = disassemblerDTO.toDomainObject(boletoDTO);

        assertNotNull(pagamento.getDadosBoleto());
        assertEquals("23793.38127 60002.345678 91000.100009 1 90000000010000", pagamento.getDadosBoleto().getCodigoBoleto());
    }

    // Testa quando o DTO é inválido (com tipo de pagamento nulo)
    @Test
    void deveLancarExcecaoQuandoTipoDePagamentoForNulo() {
        // Arrange
        PagamentoCartaoInputDTO cartaoInputDTO = new PagamentoCartaoInputDTO();
        cartaoInputDTO.setId(1L);
        cartaoInputDTO.setNomeTitular("João da Silva");
        cartaoInputDTO.setNumeroCartao("1234567812345678");
        cartaoInputDTO.setCvv("123");
        cartaoInputDTO.setValidade("12/25");

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo(null); // Tipo de pagamento nulo
        cartaoInputDTO.setFormaPagamento(formaPagamento);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            disassemblerDTO.toDomainObject(cartaoInputDTO);
        });
        assertEquals("Tipo de forma de pagamento inválido.", exception.getMessage());
    }

    // Testa quando o DTO de pagamento é nulo
    @Test
    void deveLancarExcecaoQuandoPagamentoInputDTOForNulo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            disassemblerDTO.toDomainObject(null);
        });
        assertEquals("DTO de pagamento não pode ser nulo.", exception.getMessage());
    }

    // Testa quando o tipo de pagamento não é reconhecido
    @Test
    void deveLancarExcecaoQuandoTipoDePagamentoForDesconhecido() {
        // Arrange
        PagamentoCartaoInputDTO cartaoInputDTO = new PagamentoCartaoInputDTO();
        cartaoInputDTO.setId(1L);
        cartaoInputDTO.setNomeTitular("João da Silva");
        cartaoInputDTO.setNumeroCartao("1234567812345678");
        cartaoInputDTO.setCvv("123");
        cartaoInputDTO.setValidade("12/25");

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo("DESCONHECIDO"); // Tipo inválido
        cartaoInputDTO.setFormaPagamento(formaPagamento);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            disassemblerDTO.toDomainObject(cartaoInputDTO);
        });
        assertEquals("Tipo de pagamento desconhecido: DESCONHECIDO", exception.getMessage());
    }

    // Testa a normalização da string (remover acentuação)
    @Test
    void deveNormalizarStringCorretamente() {
        String input = "João da Silva";
        String normalized = disassemblerDTO.normalizar(input);

        assertEquals("Joao da Silva", normalized);
    }
}
