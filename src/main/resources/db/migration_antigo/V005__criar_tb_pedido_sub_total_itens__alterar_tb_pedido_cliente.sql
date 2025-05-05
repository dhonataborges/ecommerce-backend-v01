-- Criação da tabela PedidoSubTotalItens
CREATE TABLE tb_pedido_sub_total_itens (
    id BIGSERIAL PRIMARY KEY,
    qtd_itens INT NOT NULL,
    valor_total_itens DECIMAL(10, 2) NOT NULL,
    
    -- Relacionamento One-to-One com PedidoCliente
    pedido_cliente_id BIGINT UNIQUE, -- Unique constraint, já que é One-to-One
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (pedido_cliente_id) REFERENCES tb_pedido_cliente(id) ON DELETE CASCADE
);

-- Remover colunas tb_pedido_cliente
ALTER TABLE tb_pedido_cliente
DROP COLUMN qtd_itens,
DROP COLUMN valor_total_itens;

-- Adicionando a coluna valor_total_produto na tabela tb_pedido_cliente
ALTER TABLE tb_pedido_cliente
ADD COLUMN valor_total_produto DECIMAL(10, 2) NOT NULL;

-- Adicionando a coluna pedido_sub_total_itens_id na tabela tb_pedido_cliente (relacionamento One-to-One)
ALTER TABLE tb_pedido_cliente
ADD COLUMN pedido_sub_total_itens_id BIGINT NOT NULL;

-- Criando o relacionamento One-to-One com a tabela tb_pedido_sub_total_itens
ALTER TABLE tb_pedido_cliente
ADD CONSTRAINT fk_pedido_sub_total_itens FOREIGN KEY (pedido_sub_total_itens_id) REFERENCES tb_pedido_sub_total_itens(id) ON DELETE CASCADE;
