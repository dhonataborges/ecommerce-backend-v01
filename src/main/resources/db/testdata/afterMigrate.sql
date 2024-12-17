-- 1️⃣ Desabilitar a verificação de FOREIGN KEYs
ALTER TABLE tb_produto DISABLE TRIGGER ALL;
ALTER TABLE tb_estoque DISABLE TRIGGER ALL;
ALTER TABLE tb_venda_produto DISABLE TRIGGER ALL;
ALTER TABLE tb_foto_produto DISABLE TRIGGER ALL;

-- 2️⃣ Excluir os dados de todas as tabelas
DELETE FROM tb_produto;
DELETE FROM tb_estoque;
DELETE FROM tb_venda_produto;
DELETE FROM tb_foto_produto;

-- 3️⃣ Habilitar a verificação de FOREIGN KEYs novamente
ALTER TABLE tb_produto ENABLE TRIGGER ALL;
ALTER TABLE tb_estoque ENABLE TRIGGER ALL;
ALTER TABLE tb_venda_produto ENABLE TRIGGER ALL;
ALTER TABLE tb_foto_produto ENABLE TRIGGER ALL;

-- 4️⃣ Reiniciar o contador de IDs das tabelas (sequências)
-- Em PostgreSQL, o auto_increment é tratado com SEQUENCES
-- O comando abaixo reinicia a sequência para cada tabela
DO $$
BEGIN
    -- Reinicia a sequência de tb_produto
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tb_produto' AND column_name = 'id') THEN
        EXECUTE 'ALTER SEQUENCE tb_produto_id_seq RESTART WITH 1;';
    END IF;

    -- Reinicia a sequência de tb_estoque
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tb_estoque' AND column_name = 'id') THEN
        EXECUTE 'ALTER SEQUENCE tb_estoque_id_seq RESTART WITH 1;';
    END IF;

    -- Reinicia a sequência de tb_venda_produto
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tb_venda_produto' AND column_name = 'id') THEN
        EXECUTE 'ALTER SEQUENCE tb_venda_produto_id_seq RESTART WITH 1;';
    END IF;
END $$;

--- 5️⃣ Inserir os registros de produto
--INSERT INTO tb_produto (cod_prod, nome_prod, descricao_prod, categoria)
--VALUES
--('p00120', 'Creme de Pele', 'O Creme que vai te surpreender.', 2),
--('p00121', 'Perfume kaik', 'O perfume do momento.', 1),
--('p00122', 'Creme para o rosto', 'O Creme que vai te surpreender.', 2);

-- 6️⃣ Inserir os registros de fotos de produtos
--INSERT INTO tb_foto_produto (produto_id, tamanho, content_type, descricao, nome_arquivo)
--VALUES
  --  (1, 40608, 'image/jpeg', '2024-08-22', '03a3054c-b831-46ad-9800-7602bf2ba5fb_carrossel_img4.jpg'),
 --   (2, 40608, 'image/jpeg', '2024-08-22', '03a3054c-b831-46ad-9800-7602bf2ba5fb_carrossel_img4.jpg');

-- 7️⃣ Inserir os registros de estoque
--INSERT INTO tb_estoque (produto_id, data_entrada, data_saida, valor_und, qtd_prod, valor_total_prod)
--VALUES
  --  (1, '2024-04-12', '2024-04-12', 56.99, 50, 2849.50),
   -- (1, '2024-04-12', '2024-04-12', 40.98, 75, 3073.50),
   -- (2, '2024-04-12', '2024-04-12', 56.99, 50, 2849.50),
    --(2, '2024-04-12', '2024-04-12', 56.99, 50, 2849.50),
  --  (3, '2024-04-12', '2024-04-12', 40.98, 75, 3073.50),
   -- (3, '2024-04-12', '2024-04-12', 56.99, 50, 2849.50);

-- 8️⃣ Inserir os registros de vendas de produtos
--INSERT INTO tb_venda_produto (estoque_id, valor_venda, num_parcela, valor_parcela, desconto_porcento)
--VALUES
 --   (1, 68.50, 3, 22.83, 5),
 --   (2, 72.00, 2, 36.00, 10),
  --  (3, 175.00, 3, 58.33, 15);
