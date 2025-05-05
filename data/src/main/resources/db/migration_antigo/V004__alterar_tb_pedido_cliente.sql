BEGIN;

-- Adiciona as novas colunas
ALTER TABLE tb_pedido_cliente
ADD COLUMN qtd_Unid_produto INTEGER,
ADD COLUMN qtd_itens INTEGER,
ADD COLUMN valor_total_itens DECIMAL(10,2);

-- Remove as colunas desnecessárias
ALTER TABLE tb_pedido_cliente
DROP COLUMN cod_pedido,
DROP COLUMN qtd_produto_pedido,
DROP COLUMN valor_qtd_pedido;

COMMIT;
