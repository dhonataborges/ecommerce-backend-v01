-- Adiciona a coluna de chave estrangeira
ALTER TABLE tb_pedido_item
ADD COLUMN catalogo_id BIGINT;

-- Define a restrição de chave estrangeira para catalogo_produto
ALTER TABLE tb_pedido_item
ADD CONSTRAINT fk_pedido_item_catalogo
FOREIGN KEY (catalogo_id)
REFERENCES tb_catalogo_produto(id);
