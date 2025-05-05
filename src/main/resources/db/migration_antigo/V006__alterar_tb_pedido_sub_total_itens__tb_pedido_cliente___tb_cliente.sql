
-- Remover colunas tb_pedido_cliente
ALTER TABLE tb_pedido_cliente
DROP COLUMN pedido_sub_total_itens_id;

-- Remover colunas tb_pedido_sub_total_itens
ALTER TABLE tb_pedido_sub_total_itens
DROP COLUMN pedido_cliente_id;

-- Adicionando a coluna pedido_sub_total_itens_id na tabela tb_cliente (relacionamento One-to-One)
ALTER TABLE tb_cliente
ADD COLUMN pedido_sub_total_itens_id BIGINT NULL;

-- Criando o relacionamento One-to-One com a tabela tb_pedido_sub_total_itens
ALTER TABLE tb_cliente
ADD CONSTRAINT fk_pedido_sub_total_itens FOREIGN KEY (pedido_sub_total_itens_id) REFERENCES tb_pedido_sub_total_itens(id) ON DELETE CASCADE;