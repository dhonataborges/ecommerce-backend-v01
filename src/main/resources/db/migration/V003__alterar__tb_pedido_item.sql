-- Remove o relacionamento direto entre pedido_item e produto
ALTER TABLE tb_pedido_item
    DROP CONSTRAINT tb_pedido_item_produto_id_fkey;

-- Adiciona a coluna estoque_id para referenciar uma entrada específica do estoque
ALTER TABLE tb_pedido_item
    ADD COLUMN estoque_id bigint;

-- Cria a nova foreign key vinculando o item do pedido a uma entrada de estoque
-- Isso permite rastrear exatamente qual lote foi utilizado na venda
ALTER TABLE tb_pedido_item
    ADD CONSTRAINT fk_pedido_item_estoque FOREIGN KEY (estoque_id)
    REFERENCES tb_estoque (id);