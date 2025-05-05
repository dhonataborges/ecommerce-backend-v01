-- Esquema de banco refatorado para facilitar o desenvolvimento de um e-commerce
-- Nomes simplificados, tabelas otimizadas, e melhores práticas aplicadas

-- ===================== Tabelas de Endereço =====================
CREATE TABLE tb_estado (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE tb_cidade (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    estado_id BIGINT REFERENCES tb_estado(id)
);

CREATE TABLE tb_endereco (
    id BIGSERIAL PRIMARY KEY,
    nome_dono VARCHAR(100),
    cep VARCHAR(20),
    bairro VARCHAR(255),
    rua VARCHAR(255),
    numero VARCHAR(50),
    complemento VARCHAR(255),
    cidade_id BIGINT REFERENCES tb_cidade(id)
);

-- ===================== Usuários e Autenticação =====================
CREATE TABLE tb_usuario (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    permissoes SMALLINT NOT NULL,
    nome VARCHAR(100),
    sobrenome VARCHAR(100)
);

CREATE TABLE tb_telefone (
    id BIGSERIAL PRIMARY KEY,
    responsavel VARCHAR(255),
    numero VARCHAR(20),
    usuario_id BIGINT REFERENCES tb_usuario(id)
);

-- ===================== Produtos e Estoque =====================
CREATE TABLE tb_produto (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(6) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    categoria SMALLINT,
    marca VARCHAR(50)
);

-- Mantida a versão original da tabela de fotos
CREATE TABLE tb_foto_produto (
    produto_id BIGINT,
    nome_arquivo VARCHAR(255),
    descricao VARCHAR(100),
    tamanho BIGINT,
    content_type VARCHAR(100)
);

CREATE TABLE tb_estoque (
    id BIGSERIAL PRIMARY KEY,
    produto_id BIGINT REFERENCES tb_produto(id),
    data_entrada DATE NOT NULL,
    data_saida DATE,
    valor_unitario NUMERIC(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_total NUMERIC(10, 2)
);

-- ===================== Pedidos =====================
CREATE TABLE tb_forma_pagamento (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    descricao VARCHAR(255)
);

CREATE TABLE tb_pedido (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT REFERENCES tb_usuario(id),
    endereco_id BIGINT REFERENCES tb_endereco(id),
    forma_pagamento_id BIGINT REFERENCES tb_forma_pagamento(id),
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'PENDENTE',
    valor_total NUMERIC(10,2)
);

CREATE TABLE tb_pedido_item (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT REFERENCES tb_pedido(id),
    produto_id BIGINT REFERENCES tb_produto(id),
    quantidade INTEGER NOT NULL,
    valor_unitario NUMERIC(10,2),
    valor_total NUMERIC(10,2)
);
