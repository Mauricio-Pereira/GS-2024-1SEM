DROP TABLE gs_donations CASCADE CONSTRAINTS;
DROP TABLE gs_orderitems CASCADE CONSTRAINTS;
DROP TABLE gs_orders CASCADE CONSTRAINTS;
DROP TABLE gs_products CASCADE CONSTRAINTS;
DROP TABLE gs_ngos CASCADE CONSTRAINTS;
DROP TABLE gs_companies CASCADE CONSTRAINTS;
DROP TABLE gs_users CASCADE CONSTRAINTS;

-- Criação da tabela GS_Users
CREATE TABLE gs_users (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR2(50) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    email VARCHAR2(100) NOT NULL UNIQUE,
    user_type VARCHAR2(20) NOT NULL,
    cep VARCHAR2(10) NOT NULL,
    address_number VARCHAR2(10) NOT NULL,
    address_complement VARCHAR2(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Criação da tabela GS_Companies
CREATE TABLE gs_companies (
    company_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admin_id INT,
    name VARCHAR2(100) NOT NULL UNIQUE,
    phone VARCHAR2(20) NOT NULL,
    website VARCHAR2(100),
    verification_status VARCHAR2(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES gs_users(user_id)
);

-- Criação da tabela GS_Products
CREATE TABLE gs_products (
    product_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    company_id INT,
    name VARCHAR2(100) NOT NULL UNIQUE,
    description CLOB,
    price NUMBER(10, 2) NOT NULL,
    stock INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (company_id) REFERENCES gs_companies(company_id)
);

-- Criação da tabela GS_Orders
CREATE TABLE gs_orders (
    order_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    buyer_id INT,
    total_amount NUMBER(10, 2) NOT NULL,
    donation_amount NUMBER(10, 2) NOT NULL,
    maintenance_amount NUMBER(10, 2) NOT NULL,
    order_status VARCHAR2(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (buyer_id) REFERENCES gs_users(user_id)
);

-- Criação da tabela GS_OrderItems
CREATE TABLE gs_orderitems (
    order_item_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price NUMBER(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (order_id) REFERENCES gs_orders(order_id),
    FOREIGN KEY (product_id) REFERENCES gs_products(product_id)
);

-- Criação da tabela GS_NGOs
CREATE TABLE gs_ngos (
    ngo_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL UNIQUE,
    mission CLOB,
    website VARCHAR2(100),
    contact_email VARCHAR2(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Criação da tabela GS_Donations
CREATE TABLE gs_donations (
    donation_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id INT,
    amount NUMBER(10, 2) NOT NULL,
    ngo_id INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (order_id) REFERENCES gs_orders(order_id),
    FOREIGN KEY (ngo_id) REFERENCES gs_ngos(ngo_id)
);



