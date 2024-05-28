-- Mauricio Vieira Pereira    RM553748
-- Vitor Onofre Ramos         RM553241
-- Luiz Otávio Leitão         RM553542


-- DROP TABLES PARA ELIMINAR POSSIVEIS TABELAS JA EXISTENTES
DROP TABLE gs_donations CASCADE CONSTRAINTS;
DROP TABLE gs_orderitems CASCADE CONSTRAINTS;
DROP TABLE gs_orders CASCADE CONSTRAINTS;
DROP TABLE gs_products CASCADE CONSTRAINTS;
DROP TABLE gs_ngos CASCADE CONSTRAINTS;
DROP TABLE gs_companies CASCADE CONSTRAINTS;
DROP TABLE gs_users CASCADE CONSTRAINTS;

-- CRIAÇÃO DAS TABELAS, CONFIGURAÇÃO DAS PRIMARY KEYS E FOREIGN KEYS
-- Criação da tabela GS_Users
CREATE TABLE gs_users (
    user_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR2(100) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    user_type VARCHAR2(20) NOT NULL CHECK (user_type IN ('comprador', 'admin_empresa')),
    cep VARCHAR2(10) NOT NULL,
    address_number VARCHAR2(10) NOT NULL,
    address_complement VARCHAR2(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Criação da tabela GS_Companies
CREATE TABLE gs_companies (
    company_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admin_id NUMBER,
    name VARCHAR2(100) NOT NULL UNIQUE,
    phone VARCHAR2(20) NOT NULL,
    website VARCHAR2(100),
    verification_status VARCHAR2(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_admin_id_companies FOREIGN KEY (admin_id) REFERENCES gs_users(user_id)
);

-- Criação da tabela GS_Products
CREATE TABLE gs_products (
    product_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    company_id NUMBER,
    name VARCHAR2(100) NOT NULL UNIQUE,
    description VARCHAR2(4000),
    price NUMBER(10, 2) NOT NULL,
    stock NUMBER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_company_id_products FOREIGN KEY (company_id) REFERENCES gs_companies(company_id)
);

-- Criação da tabela GS_Orders
CREATE TABLE gs_orders (
    order_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_number VARCHAR2(20) NOT NULL UNIQUE,
    buyer_id NUMBER,
    total_amount NUMBER(10, 2) NOT NULL,
    donation_amount NUMBER(10, 2) NOT NULL,
    maintenance_amount NUMBER(10, 2) NOT NULL,
    order_status VARCHAR2(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_buyer_id_orders FOREIGN KEY (buyer_id) REFERENCES gs_users(user_id)
);

-- Criação da tabela GS_OrderItems
CREATE TABLE gs_orderitems (
    order_item_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id NUMBER,
    product_id NUMBER,
    quantity NUMBER NOT NULL,
    price NUMBER(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_order_id_orderitems FOREIGN KEY (order_id) REFERENCES gs_orders(order_id),
    CONSTRAINT fk_product_id_orderitems FOREIGN KEY (product_id) REFERENCES gs_products(product_id)
);

-- Criação da tabela GS_NGOs
CREATE TABLE gs_ngos (
    ngo_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    contact_id NUMBER,
    name VARCHAR2(100) NOT NULL UNIQUE,
    mission VARCHAR2(4000),
    phone VARCHAR2(20),
    website VARCHAR2(100),
    contact_email VARCHAR2(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_contact_id_ngos FOREIGN KEY (contact_id) REFERENCES gs_users(user_id)
);

-- Criação da tabela GS_Donations
CREATE TABLE gs_donations (
    donation_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id NUMBER,
    amount NUMBER(10, 2) NOT NULL,
    ngo_id NUMBER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_order_id_donations FOREIGN KEY (order_id) REFERENCES gs_orders(order_id),
    CONSTRAINT fk_ngo_id_donations FOREIGN KEY (ngo_id) REFERENCES gs_ngos(ngo_id)
);
/
-- Trigger para atualizar updated_at na tabela gs_users
CREATE OR REPLACE TRIGGER trg_users_upd
BEFORE UPDATE ON gs_users
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
-- Trigger para atualizar updated_at na tabela gs_companies
CREATE OR REPLACE TRIGGER trg_companies_upd
BEFORE UPDATE ON gs_companies
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
-- Trigger para atualizar updated_at na tabela gs_products
CREATE OR REPLACE TRIGGER trg_products_upd
BEFORE UPDATE ON gs_products
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
-- Trigger para atualizar updated_at na tabela gs_orders
CREATE OR REPLACE TRIGGER trg_orders_upd
BEFORE UPDATE ON gs_orders
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
-- Trigger para atualizar updated_at na tabela gs_orderitems
CREATE OR REPLACE TRIGGER trg_orderitems_upd
BEFORE UPDATE ON gs_orderitems
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
-- Trigger para atualizar updated_at na tabela gs_ngos
CREATE OR REPLACE TRIGGER trg_ngos_upd
BEFORE UPDATE ON gs_ngos
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
-- Trigger para atualizar updated_at na tabela gs_donations
CREATE OR REPLACE TRIGGER trg_donations_upd
BEFORE UPDATE ON gs_donations
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
