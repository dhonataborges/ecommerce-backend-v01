

-- Criação da tabela `tb_estado`
CREATE TABLE tb_estado (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Criação da tabela `tb_cidade`
CREATE TABLE tb_cidade (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    estado_id BIGINT NOT NULL,
    CONSTRAINT fk_estado FOREIGN KEY (estado_id) REFERENCES tb_estado (id) ON DELETE CASCADE
);

-- Tabela Cliente
CREATE TABLE tb_cliente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100)NOT NULL,
    sobrenome VARCHAR(100) NOT NULL
);

-- Criação da tabela `tb_usuario`
CREATE TABLE tb_usuario (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    permicoes SMALLINT,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES tb_cliente (id) ON DELETE CASCADE
);

-- Criação da tabela `tb_telefone`
CREATE TABLE tb_telefone (
    id BIGSERIAL PRIMARY KEY,
    nome_responsavel VARCHAR(255),
    numero_telefone VARCHAR(20) NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES tb_cliente (id) ON DELETE CASCADE
);

-- Tabela Endereco
CREATE TABLE tb_endereco (
    id BIGSERIAL PRIMARY KEY,
    nome_dono_endereco VARCHAR(100),
    cep VARCHAR(20) NOT NULL,
    bairro VARCHAR(255) NOT NULL,
    rua_avenida VARCHAR(255) NOT NULL,
    numero VARCHAR(50) NOT NULL,
    complemento VARCHAR(255),
    cidade_id BIGINT,
    cliente_id BIGINT,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES tb_cliente (id) ON DELETE CASCADE,
    CONSTRAINT fk_cidade FOREIGN KEY (cidade_id) REFERENCES tb_cidade (id) ON DELETE CASCADE
);

-- Criação da tabela `tb_produto`
CREATE TABLE tb_produto (
    id BIGSERIAL PRIMARY KEY,
    cod_prod VARCHAR(6) NOT NULL,
    nome_prod VARCHAR(100) NOT NULL,
    descricao_prod VARCHAR(500) NOT NULL,
    categoria SMALLINT
);

-- Criação da tabela `tb_estoque`
CREATE TABLE tb_estoque (
    id BIGSERIAL PRIMARY KEY,
    data_entrada DATE NOT NULL,
    data_saida DATE,
    valor_und NUMERIC(38,2) NOT NULL,
    qtd_prod INTEGER NOT NULL,
    valor_total_prod NUMERIC(38,2),
    produto_id BIGINT,
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES tb_produto (id) ON DELETE CASCADE
);

-- Criação da tabela `tb_foto_produto`
CREATE TABLE tb_foto_produto (
    produto_id BIGINT PRIMARY KEY,
    nome_arquivo VARCHAR(255),
    descricao VARCHAR(100),
    tamanho BIGINT,
    content_type VARCHAR(100),
    CONSTRAINT fk_foto_produto FOREIGN KEY (produto_id) REFERENCES tb_produto (id) ON DELETE CASCADE
);

-- Criação da tabela `tb_venda_produto`
CREATE TABLE tb_venda_produto (
    id BIGSERIAL PRIMARY KEY,
    desconto_porcento NUMERIC(38,2),
    num_parcela INTEGER,
    valor_parcela NUMERIC(38,2),
    valor_venda NUMERIC(38,2),
    estoque_id BIGINT,
    CONSTRAINT fk_venda_estoque FOREIGN KEY (estoque_id) REFERENCES tb_estoque (id) ON DELETE CASCADE
);

-- Criação da tabela `tb_pedido_cliente`
CREATE TABLE tb_pedido_cliente (
    id BIGSERIAL PRIMARY KEY,
    cod_pedido VARCHAR(6) NOT NULL,
    qtd_produto_pedido INTEGER NOT NULL,
    valor_qtd_pedido NUMERIC(38,2),
    cliente_id BIGINT,
    venda_produto_id BIGINT,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES tb_cliente (id) ON DELETE CASCADE,
    CONSTRAINT fk_venda_produto FOREIGN KEY (venda_produto_id) REFERENCES tb_venda_produto (id) ON DELETE CASCADE
);

-- Criação da tabela `tb_pedido_todos_clientes`
--CREATE TABLE tb_pedido_todos_clientes (
    --id BIGSERIAL PRIMARY KEY, -- BIGSERIAL substitui AUTO_INCREMENT
    --cod_pedido VARCHAR(6) NOT NULL,
   -- qtd_pedido INTEGER NOT Null,
   -- valor_total_pedidos NUMERIC(38,2),
   -- CONSTRAINT fk_pedido_todos_clientes FOREIGN KEY (cliente_id) REFERENCES tb_cliente (id)
    --);

------------------------------------------------------MYSQL-----------------------------------------
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
