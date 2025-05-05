-- Esse script altera a tabela 'tb_pedido_item' para remover os relacionamentos com as tabelas de estoque e produto,
-- Ele remove as chaves estrangeiras relacionadas e as colunas 'produto_id' e 'estoque_id' caso existam.
ALTER TABLE tb_pedido_item
DROP CONSTRAINT IF EXISTS fk_pedido_item_estoque,
DROP CONSTRAINT IF EXISTS fk_pedido_item_catalogo,
DROP COLUMN IF EXISTS produto_id,
DROP COLUMN IF EXISTS estoque_id;
