package com.backend.ecommerce.domain.model;

import com.backend.ecommerce.domain.model.enuns.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um pedido realizado por um usuário no sistema.
 * A classe é mapeada para a tabela 'tb_pedido' no banco de dados.
 * Contém informações sobre o usuário, endereço de entrega, status do pedido, itens do pedido, entre outros.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"usuario", "endereco"}) // Exclui 'usuario' e 'endereco' da representação em string da classe
@Entity
@Table(name = "tb_pedido") // Define a tabela no banco de dados
public class Pedido {

    /**
     * Identificador único do pedido.
     * Este campo é a chave primária da tabela 'tb_pedido'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do id com a estratégia de auto incremento
    private Long id;

    /**
     * Usuário que realizou o pedido.
     * Este campo está associado a uma instância da entidade 'Usuario', representando o dono do pedido.
     */
    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento 'Muitos para um' com 'Usuario'
    @JoinColumn(name = "usuario_id") // Define a coluna no banco que referencia o 'Usuario'
    private Usuario usuario;

    /**
     * Endereço de entrega do pedido.
     * Este campo está associado a uma instância da entidade 'Endereco', representando o endereço onde o pedido será entregue.
     */
    @ManyToOne(fetch = FetchType.EAGER) // Relacionamento 'Muitos para um' com 'Endereco'
    @JoinColumn(name = "endereco_id") // Define a coluna no banco que referencia o 'Endereco'
    private Endereco endereco;

    /**
     * Informações de pagamento do pedido.
     * Está associada a uma instância da entidade 'Pagamento', que contém os detalhes de como o pagamento foi realizado.
     */
    @OneToOne(cascade = CascadeType.ALL) // Relacionamento de 'Um para um' com 'Pagamento', com cascata de operações
    @JoinColumn(name = "pagamento_id", referencedColumnName = "id") // Define a coluna que referencia o 'Pagamento'
    private Pagamento pagamento;

    /**
     * Data em que o pedido foi realizado.
     * Armazenada no formato de data (yyyy-MM-dd).
     */
    @Column(name = "data_pedido") // Define o nome da coluna no banco de dados
    @JsonFormat(pattern="yyyy-MM-dd") // Define o formato da data no JSON
    private LocalDate dataPedido;

    /**
     * Quantidade total de itens no pedido.
     * Representa o número total de produtos (itens) comprados no pedido.
     */
    @Column(name = "quantidade_item") // Define o nome da coluna no banco de dados
    private Integer quantidadeItem;

    /**
     * Valor total do pedido.
     * Representa o custo total do pedido, com base no valor dos itens e descontos (se houver).
     */
    @Column(name = "valor_total") // Define o nome da coluna no banco de dados
    private BigDecimal valorTotal;

    /**
     * Status atual do pedido.
     * O status pode ser 'Aberto', 'Pendente', ou 'Fechado', conforme o enum 'StatusPedido'.
     */
    @Enumerated(EnumType.STRING) // Armazena o nome do status no banco em vez de um valor numérico
    @Column(name = "status") // Define o nome da coluna no banco de dados
    private StatusPedido status;

    /**
     * Itens que compõem o pedido.
     * Este campo contém a lista de instâncias de 'PedidoItem', representando os produtos comprados no pedido.
     * O relacionamento é de um para muitos com a entidade 'PedidoItem'.
     */
    @OneToMany(mappedBy = "pedido") // Relacionamento de 'Um para muitos' com 'PedidoItem'
    private List<PedidoItem> pedidoItens = new ArrayList<>(); // Lista de itens do pedido
}
