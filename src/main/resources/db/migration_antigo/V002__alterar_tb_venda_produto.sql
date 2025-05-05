
-- Alterar a tabela `tb_venda_produto` para adicionar coluna valor_venda_com_desconto
ALTER TABLE tb_venda_produto
ADD COLUMN valor_venda_com_desconto NUMERIC(38, 2);
