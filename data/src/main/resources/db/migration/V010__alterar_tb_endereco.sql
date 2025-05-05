-- 1. Adiciona a coluna usuario_id (tipo BIGINT, pode ser NULL)
ALTER TABLE tb_endereco
ADD COLUMN usuario_id BIGINT;

-- 2. Cria a chave estrangeira referenciando tb_usuario(id)
ALTER TABLE tb_endereco
ADD CONSTRAINT fk_endereco_usuario
FOREIGN KEY (usuario_id)
REFERENCES tb_usuario(id)
ON DELETE SET NULL;

