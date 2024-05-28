-- Inserir dados na tabela gs_users com tipos ajustados e novos usuários admin_ngo
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user1@example.com', 'password123', 'buyer', '12345-678', '100', 'Apt 101');
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user2@example.com', 'password123', 'admin_company', '23456-789', '200', 'Apt 202');
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user3@example.com', 'password123', 'buyer', '34567-890', '300', 'Apt 303');
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user4@example.com', 'password123', 'admin_company', '45678-901', '400', 'Apt 404');
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user5@example.com', 'password123', 'admin_ngo', '56789-012', '500', 'Apt 505');
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user6@example.com', 'password123', 'admin_ngo', '67890-123', '600', 'Apt 606');
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user7@example.com', 'password123', 'admin_ngo', '78901-234', '700', 'Apt 707');
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user8@example.com', 'password123', 'admin_ngo', '89012-345', '800', 'Apt 808');
INSERT INTO gs_users (email, password, user_type, cep, address_number, address_complement)
VALUES ('user9@example.com', 'password123', 'admin_ngo', '90123-456', '900', 'Apt 909');


-- Inserir dados na tabela gs_companies
INSERT INTO gs_companies (admin_id, name, phone, website, verification_status)
VALUES (2, 'Company A', '123-456-7890', 'www.companya.com', 'verified');
INSERT INTO gs_companies (admin_id, name, phone, website, verification_status)
VALUES (4, 'Company B', '234-567-8901', 'www.companyb.com', 'pending');
INSERT INTO gs_companies (admin_id, name, phone, website, verification_status)
VALUES (2, 'Company C', '345-678-9012', 'www.companyc.com', 'verified');
INSERT INTO gs_companies (admin_id, name, phone, website, verification_status)
VALUES (4, 'Company D', '456-789-0123', 'www.companyd.com', 'verified');
INSERT INTO gs_companies (admin_id, name, phone, website, verification_status)
VALUES (2, 'Company E', '567-890-1234', 'www.companye.com', 'pending');


-- Inserir dados na tabela gs_products
INSERT INTO gs_products (company_id, name, description, price, stock)
VALUES (1, 'Product A', 'Description A', 10.00, 100);
INSERT INTO gs_products (company_id, name, description, price, stock)
VALUES (2, 'Product B', 'Description B', 20.00, 200);
INSERT INTO gs_products (company_id, name, description, price, stock)
VALUES (3, 'Product C', 'Description C', 30.00, 300);
INSERT INTO gs_products (company_id, name, description, price, stock)
VALUES (4, 'Product D', 'Description D', 40.00, 400);
INSERT INTO gs_products (company_id, name, description, price, stock)
VALUES (5, 'Product E', 'Description E', 50.00, 500);


-- Inserir dados na tabela gs_orders
INSERT INTO gs_orders (order_number, buyer_id, total_amount, donation_amount, maintenance_amount, order_status)
VALUES ('ORD001', 1, 100.00, 8.00, 4.00, 'paid');
INSERT INTO gs_orders (order_number, buyer_id, total_amount, donation_amount, maintenance_amount, order_status)
VALUES ('ORD002', 3, 200.00, 16.00, 8.00, 'pending');
INSERT INTO gs_orders (order_number, buyer_id, total_amount, donation_amount, maintenance_amount, order_status)
VALUES ('ORD003', 5, 300.00, 24.00, 12.00, 'canceled');
INSERT INTO gs_orders (order_number, buyer_id, total_amount, donation_amount, maintenance_amount, order_status)
VALUES ('ORD004', 1, 400.00, 32.00, 16.00, 'paid');
INSERT INTO gs_orders (order_number, buyer_id, total_amount, donation_amount, maintenance_amount, order_status)
VALUES ('ORD005', 3, 500.00, 40.00, 20.00, 'pending');


-- Inserir dados na tabela gs_ngos
INSERT INTO gs_ngos (contact_id, name, mission, phone, website, contact_email)
VALUES (1, 'NGO A', 'Mission A', '123-456-7890', 'www.ngoa.com', 'contact@ngoa.com');
INSERT INTO gs_ngos (contact_id, name, mission, phone, website, contact_email)
VALUES (3, 'NGO B', 'Mission B', '234-567-8901', 'www.ngob.com', 'contact@ngob.com');
INSERT INTO gs_ngos (contact_id, name, mission, phone, website, contact_email)
VALUES (1, 'NGO C', 'Mission C', '345-678-9012', 'www.ngoc.com', 'contact@ngoc.com');
INSERT INTO gs_ngos (contact_id, name, mission, phone, website, contact_email)
VALUES (3, 'NGO D', 'Mission D', '456-789-0123', 'www.ngod.com', 'contact@ngod.com');
INSERT INTO gs_ngos (contact_id, name, mission, phone, website, contact_email)
VALUES (1, 'NGO E', 'Mission E', '567-890-1234', 'www.ngoe.com', 'contact@ngoe.com');


-- Inserir dados na tabela gs_orderitems
INSERT INTO gs_orderitems (order_id, product_id, quantity, price)
VALUES (1, 1, 2, 10.00);
INSERT INTO gs_orderitems (order_id, product_id, quantity, price)
VALUES (2, 2, 4, 20.00);
INSERT INTO gs_orderitems (order_id, product_id, quantity, price)
VALUES (3, 3, 6, 30.00);
INSERT INTO gs_orderitems (order_id, product_id, quantity, price)
VALUES (4, 4, 8, 40.00);
INSERT INTO gs_orderitems (order_id, product_id, quantity, price)
VALUES (5, 5, 10, 50.00);

-- Inserir dados na tabela gs_donations
INSERT INTO gs_donations (order_id, amount, ngo_id)
VALUES (1, 8.00, 1);
INSERT INTO gs_donations (order_id, amount, ngo_id)
VALUES (2, 16.00, 2);
INSERT INTO gs_donations (order_id, amount, ngo_id)
VALUES (3, 24.00, 2);
INSERT INTO gs_donations (order_id, amount, ngo_id)
VALUES (4, 32.00, 4);
INSERT INTO gs_donations (order_id, amount, ngo_id)
VALUES (5, 40.00, 4);

-- Relatório 1.1: Lista todos os usuários ordenados pelo email em ordem crescente
SELECT * FROM gs_users
ORDER BY email ASC;

-- Relatório 1.2: Lista todas as empresas ordenadas pelo nome em ordem crescente
SELECT * FROM gs_companies
ORDER BY name ASC;


-- Relatório 2.1: Seleciona produtos com preços entre 20 e 40, e cujos nomes começam com 'Product'
SELECT * FROM gs_products
WHERE price BETWEEN 20 AND 40
AND name LIKE 'Product%';

-- Relatório 2.2: Seleciona usuários cujo CEP está entre '30000-000' e '60000-000', e cujos emails contêm 'example'
SELECT * FROM gs_users
WHERE cep BETWEEN '30000-000' AND '60000-000'
AND email LIKE '%example%';


-- Relatório 3.1: Converte os nomes das ONGs para maiúsculas e lista juntamente com o telefone
SELECT UPPER(name) AS upper_name, phone FROM gs_ngos;

-- Relatório 3.2: Converte os nomes das empresas para maiúsculas e lista juntamente com o site
SELECT UPPER(name) AS upper_name, website FROM gs_companies;


-- Relatório 4.1: Extrai o ano de criação das empresas e lista juntamente com o nome
SELECT EXTRACT(YEAR FROM created_at) AS year_created, name 
FROM gs_companies;

-- Relatório 4.2: Extrai o mês de criação dos usuários e lista juntamente com o email
SELECT EXTRACT(MONTH FROM created_at) AS month_created, email 
FROM gs_users;


-- Relatório 5.1: Conta o número de usuários agrupados por tipo de usuário
SELECT user_type, COUNT(*) AS count 
FROM gs_users
GROUP BY user_type;

-- Relatório 5.2: Soma os preços dos produtos agrupados por empresa
SELECT company_id, SUM(price) AS total_price
FROM gs_products
GROUP BY company_id;


-- Relatório 6.1: Lista o número do pedido, email do comprador e valor total da compra, usando junção de equivalência entre gs_orders e gs_users
SELECT o.order_number, u.email, o.total_amount
FROM gs_orders o
JOIN gs_users u ON o.buyer_id = u.user_id;

-- Relatório 6.2: Lista o nome do produto, nome da empresa e quantidade em estoque, usando junção de equivalência entre gs_products e gs_companies
SELECT p.name AS product_name, c.name AS company_name, p.stock
FROM gs_products p
JOIN gs_companies c ON p.company_id = c.company_id;


-- Relatório 7.1: Lista os produtos que não têm correspondência na tabela gs_orderitems (produtos que não foram vendidos)
SELECT p.name AS product_name, p.price
FROM gs_products p
LEFT JOIN gs_orderitems oi ON p.product_id = oi.product_id
WHERE oi.product_id IS NULL;

-- Relatório 7.2: Lista as ONGs que não têm correspondência na tabela gs_donations (ONGs que não receberam doações)
SELECT n.name AS ngo_name, n.contact_email
FROM gs_ngos n
LEFT JOIN gs_donations d ON n.ngo_id = d.ngo_id
WHERE d.ngo_id IS NULL;





