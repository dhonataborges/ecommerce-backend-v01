

---------------------------------POSTGREDSQL--------------------------------------------------

-- Criação da tabela `tb_produto`
CREATE TABLE tb_produto (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL substitui AUTO_INCREMENT
    cod_prod VARCHAR(6) NOT NULL,
    nome_prod VARCHAR(60) NOT NULL,
    descricao_prod VARCHAR(500) NOT NULL,
    categoria SMALLINT -- PostgreSQL não possui TINYINT, use SMALLINT
);

-- Criação da tabela `tb_estoque`
CREATE TABLE tb_estoque (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL substitui AUTO_INCREMENT
    data_entrada DATE NOT NULL,
    data_saida DATE,
    valor_und NUMERIC(38,2) NOT NULL, -- NUMERIC substitui DECIMAL no PostgreSQL
    qtd_prod INTEGER NOT NULL,
    valor_total_prod NUMERIC(38,2),
    produto_id BIGINT,
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES tb_produto (id)
);

-- Criação da tabela `tb_foto_produto`
CREATE TABLE tb_foto_produto (
    produto_id BIGINT PRIMARY KEY,
    nome_arquivo VARCHAR(255),
    descricao VARCHAR(100),
    tamanho BIGINT,
    content_type VARCHAR(100),
    CONSTRAINT fk_foto_produto FOREIGN KEY (produto_id) REFERENCES tb_produto (id)
);

-- Criação da tabela `tb_venda_produto`
CREATE TABLE tb_venda_produto (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL substitui AUTO_INCREMENT
    desconto_porcento NUMERIC(38,2),
    num_parcela INTEGER,
    valor_parcela NUMERIC(38,2),
    valor_venda NUMERIC(38,2),
    estoque_id BIGINT,
    CONSTRAINT fk_venda_estoque FOREIGN KEY (estoque_id) REFERENCES tb_estoque (id)
);

-----------------------------------------------------------------------------------------------
--create table tb_produto (
--id bigint not null auto_increment,
--cod_prod varchar(6) not null,
--nome_prod varchar(60) not null,
--descricao_prod varchar(500) not null,
--categoria tinyint,
--primary key (id)
--) engine=InnoDB default charset=utf8mb4;

--create table tb_estoque (
--id bigint not null auto_increment,
--data_entrada date not null,
--data_saida date,
--valor_und decimal(38,2) not null,
--qtd_prod integer not null,
--valor_total_prod decimal(38,2),
--produto_id bigint, primary key (id)
--) engine=InnoDB default charset=utf8mb4;

--create table tb_foto_produto (
--produto_id bigint not null,
--nome_arquivo varchar(255),
--descricao varchar(100),
--tamanho bigint,
--content_type varchar(100),

--primary key (produto_id)
--) engine=InnoDB default charset=utf8mb4;;

--create table tb_venda_produto (
--id bigint not null auto_increment,
--desconto_porcento decimal(38,2),
--num_parcela integer,
--valor_parcela decimal(38,2),
--valor_venda decimal(38,2),
--estoque_id bigint,

--primary key (id)
--) engine=InnoDB default charset=utf8mb4;;

--alter table tb_estoque add constraint FKsmdpeam53cbq2gjgy42rriaj7 foreign key (produto_id) references tb_produto (id);

--alter table tb_foto_produto add constraint FKtgiixjgpc87837iheye2owj4y foreign key (produto_id) references tb_produto (id);

--alter table tb_venda_produto add constraint FK384tey69s9h2bnrfw3u1iuhx9 foreign key (estoque_id) references tb_estoque (id);
