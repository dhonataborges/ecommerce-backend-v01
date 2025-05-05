-- Altera a tabela onde os pedidos são armazenados
ALTER TABLE tb_pedido

-- Modifica a coluna 'endereco_id' para permitir valores nulos
ALTER COLUMN endereco_id DROP NOT NULL;
