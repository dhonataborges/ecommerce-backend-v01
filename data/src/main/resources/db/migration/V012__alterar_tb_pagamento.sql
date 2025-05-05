-- Altera a tabela de pagamento para incluir os novos campos opcionais
ALTER TABLE tb_pagamento
ADD COLUMN nome_titular VARCHAR(100),
ADD COLUMN numero_cartao VARCHAR(20),
ADD COLUMN validade VARCHAR(7),
ADD COLUMN cvv VARCHAR(4),
ADD COLUMN chave_pix VARCHAR(100),
ADD COLUMN codigo_boleto VARCHAR(100),
ADD COLUMN status VARCHAR(20),
ALTER COLUMN valor SET NOT NULL;
