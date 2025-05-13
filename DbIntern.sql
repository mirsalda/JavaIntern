CREATE TABLE klient (
klient_id INT PRIMARY KEY,
emri VARCHAR(100),
mbiemri VARCHAR (100),
phone VARCHAR(20)
);

CREATE TABLE tables(
table_id INT PRIMARY KEY,
table_number INT,
kapaciteti INT
);

CREATE TABLE rezervim(
rezervim_id INT PRIMARY KEY,
klient_id INT,
table_id INT,
rezervim_date DATE,
FOREIGN KEY (klient_id) REFERENCES klient(klient_id),
FOREIGN KEY(table_id) REFERENCES tables(table_id)

);

CREATE TABLE Orders(
order_id INT PRIMARY KEY, 
rezervim_id INT,
item_name VARCHAR(100),
sasia INT,
price DECIMAL(6,2),
FOREIGN KEY (rezervim_id) REFERENCES rezervim(rezervim_id)
);


CREATE TABLE klient_tables (
    klient_id INT,
    table_id INT,
    PRIMARY KEY (klient_id, table_id),
    FOREIGN KEY (klient_id) REFERENCES klient(klient_id),
    FOREIGN KEY (table_id) REFERENCES tables(table_id)
);

INSERT INTO klient_tables (klient_id, table_id)
SELECT r.klient_id, r.table_id
FROM rezervim r;


SELECT * FROM klient_tables;


INSERT INTO klient (klient_id, emri, mbiemri, phone) VALUES
(1, 'Mirsalda', 'D', '123456789'),
(2, 'Geri', 'Mrekullia', '1234567890'),
(3, 'Morush', 'Zemra', '1234567890'),
(4, 'Dajana', 'Shala', '234567890'),
(5, 'Andi', 'Lufti', '234567890'),
(6, 'Beni', 'Rama', '23456789'),
(7, 'Olti', 'Krasniqi', '234567890'),
(8, 'Ardit', 'Shkurti', '234567890'),
(9, 'Luna', 'Hoxha', '4523456789'),
(10, 'Emil', 'Veliu', '345678678');


INSERT INTO tables (table_id, table_number, kapaciteti) VALUES
(1, 1, 4),
(2, 2, 2),
(3, 3, 6),
(4, 4, 4),
(5, 5, 2),
(6, 6, 6),
(7, 7, 8),
(8, 8, 4),
(9, 9, 2),
(10, 10, 4);


INSERT INTO rezervim (rezervim_id, klient_id, table_id, rezervim_date) VALUES
(1, 1, 1, '2025-05-13'),
(2, 2, 3, '2025-05-13'),
(3, 3, 4, '2025-05-14'),
(4, 4, 5, '2025-05-14'),
(5, 5, 2, '2025-05-15'),
(6, 6, 6, '2025-05-15'),
(7, 7, 7, '2025-05-16'),
(8, 8, 8, '2025-05-16'),
(9, 9, 9, '2025-05-17'),
(10, 10, 10, '2025-05-17');

INSERT INTO orders (order_id, rezervim_id, item_name, sasia, price) VALUES
(1, 1, 'Pizza Margherita', 2, 5.50),
(2, 1, 'Coca-Cola', 2, 1.50),
(3, 2, 'Burger', 1, 4.00),
(4, 2, 'Fries', 1, 2.50),
(5, 3, 'Salad', 1, 3.00),
(6, 3, 'Water', 2, 1.00),
(7, 4, 'Spaghetti Bolognese', 1, 6.50),
(8, 4, 'Lemonade', 2, 2.00),
(9, 5, 'Pasta Alfredo', 1, 7.00),
(10, 5, 'Ice Cream', 1, 2.50);


SELECT * FROM klient ;
SELECT * FROM tables;
SELECT * FROM rezervim;
SELECT * FROM orders;
-- Totali i rezervimeve për cdo klient
SELECT k.emri, COUNT(r.rezervim_id) AS total_reservim
FROM klient k
JOIN rezervim r ON k.klient_id = r.klient_id
GROUP BY k.emri
ORDER BY total_reservim DESC;

--Totali i shumes per cdo rezervim
SELECT r.rezervim_id, SUM(o.sasia * o.price) AS total_amount
FROM rezervim r
JOIN orders o ON r.rezervim_id = o.rezervim_id
GROUP BY r.rezervim_id
ORDER BY total_amount DESC;

SELECT item_name, SUM(sasia) AS total_ordered
FROM orders
GROUP BY item_name
ORDER BY total_ordered DESC;


-- Artikujt me te porositur
SELECT AVG(price) AS average_price
FROM orders;

