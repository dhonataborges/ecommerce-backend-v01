-- Altera a tabela onde os pedidos são armazenados
ALTER TABLE tb_pedido
-- Adicionar a coluna 'quantidade_item' do tipo inteiro
ADD COLUMN quantidade_item INT NOT NULL;
