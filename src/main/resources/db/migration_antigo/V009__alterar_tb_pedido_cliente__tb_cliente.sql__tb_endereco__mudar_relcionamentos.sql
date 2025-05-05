-- 1. Remover a coluna cliente_id da tabela tb_endereco
ALTER TABLE tb_endereco
DROP COLUMN IF EXISTS cliente_id;

-- 2. Adicionar a coluna endereco_id na tabela tb_pedido_cliente
ALTER TABLE tb_pedido_cliente
ADD COLUMN endereco_id BIGINT NOT NULL;

-- 3. Criar a constraint de chave estrangeira (One-to-One)
ALTER TABLE tb_pedido_cliente
ADD CONSTRAINT fk_pedido_endereco
FOREIGN KEY (endereco_id)
REFERENCES tb_endereco(id);