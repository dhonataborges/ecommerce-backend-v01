-- Atualizando o valor 'ABERTO' para 0
UPDATE tb_pedido SET status = 0 WHERE status = 'ABERTO';

-- Atualizando o valor 'PENDENTE' para 1
UPDATE tb_pedido SET status = 1 WHERE status = 'PENDENTE';

-- Atualizando o valor 'FECHADO' para 3
UPDATE tb_pedido SET status = 3 WHERE status = 'FECHADO';

-- Caso existam outros valores inesperados, defina um valor padrão
UPDATE tb_pedido SET status = 0 WHERE status NOT IN ('ABERTO', 'PENDENTE', 'FECHADO');

