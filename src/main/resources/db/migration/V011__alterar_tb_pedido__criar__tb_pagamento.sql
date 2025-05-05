-- Cria a tabela de formas de pagamento realizadas para um pedido.
CREATE TABLE tb_pagamento (
    id SERIAL PRIMARY KEY,
    data_pagamento DATE,
    valor NUMERIC(10, 2),
    forma_pagamento_id INTEGER,
    FOREIGN KEY (forma_pagamento_id)
        REFERENCES tb_forma_pagamento(id)
);

-- Remove a coluna antiga de forma de pagamento da tabela de pedidos
ALTER TABLE tb_pedido
DROP COLUMN IF EXISTS forma_pagamento_id;

-- Adiciona uma coluna na tabela de pedidos para vincular o pagamento feito
ALTER TABLE tb_pedido
ADD COLUMN pagamento_id INTEGER;

-- Cria a restrição de chave estrangeira na tabela de pedidos
ALTER TABLE tb_pedido
ADD CONSTRAINT fk_pagamento
FOREIGN KEY (pagamento_id)
REFERENCES tb_pagamento(id);
