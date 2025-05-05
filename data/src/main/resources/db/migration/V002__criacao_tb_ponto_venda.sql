CREATE TABLE tb_ponto_venda (
    id SERIAL PRIMARY KEY,
    estoque_id BIGINT NOT NULL,
    desconto_porcento NUMERIC(5,2),
    valor_parcela NUMERIC(10,2),
    num_parcela INTEGER,
    valor_com_desconto NUMERIC(10,2),

    CONSTRAINT fk_ponto_venda_estoque
        FOREIGN KEY (estoque_id)
        REFERENCES tb_estoque(id)
        ON DELETE CASCADE
);