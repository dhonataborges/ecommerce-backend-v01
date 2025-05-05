-- Remove a restrição de chave estrangeira existente na coluna cliente_id
ALTER TABLE tb_pedido_sub_total_itens
DROP CONSTRAINT fk_cliente,

-- Adiciona novamente a chave estrangeira para cliente_id, garantindo que,
-- caso um subtotal seja excluído, o cliente associado NÃO seja removido.
-- Em vez disso, o campo cliente_id será definido como NULL.
ADD CONSTRAINT fk_cliente FOREIGN KEY (cliente_id)
REFERENCES tb_cliente(id) ON DELETE SET NULL;