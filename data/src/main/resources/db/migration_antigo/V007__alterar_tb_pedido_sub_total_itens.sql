-- Alterando a tabela tb_pedido_sub_total_itens para colocar
---a coluna cliente_id e fazer o relacionamento one to one
ALTER TABLE tb_pedido_sub_total_itens
ADD COLUMN cliente_id BIGINT UNIQUE, -- Garante que seja One-to-One
ADD CONSTRAINT fk_cliente
FOREIGN KEY (cliente_id)
REFERENCES tb_cliente(id)
ON DELETE CASCADE;
