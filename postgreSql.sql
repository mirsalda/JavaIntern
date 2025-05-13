CREATE TABLE kursi (
  id SERIAL PRIMARY KEY,
  emri_kursit VARCHAR(100),
  kohezgjatja integer,
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO kursi (emri_kursit, kohezgjatja)
VALUES ('java', 3);
INSERT INTO kursi (emri_kursit, kohezgjatja)
VALUES ('.net', 3);
INSERT INTO kursi (emri_kursit, kohezgjatja)
VALUES ('angular', 5);

INSERT INTO kursi(emri_kursit, kohezgjatja)
VALUES ('python', 6);
INSERT INTO kursi(emri_kursit, kohezgjatja)
VALUES('JavaScript', 8);

SELECT * FROM kursi;

ALTER TABLE kursi
ADD programming_language VARCHAR(100);

UPDATE kursi
SET programming_language='springboot'
WHERE emri_kursit='java';


DELETE FROM kursi 
where id=3;


CREATE TABLE student (
  ID SERIAL PRIMARY KEY,
  student_key INTEGER,
  name VARCHAR(100),
  email VARCHAR(100),
  birth_date TIMESTAMP,
  phone VARCHAR(20),
  pike INTEGER,
  FOREIGN KEY (student_key) REFERENCES kursi (id)
);

 INSERT INTO student (name,email,birth_date, phone, pike, student_key) VALUES
 ('MirsaldaYlli', 'mirsaldad@gmail.com', '2004-06-07', '123456789',100,1),
 
 ('GeriMrekullia', 'gerimrekullia@gmail.com' , '1999-08-13', '123456789', 100,2),
 ('Morush', 'morush@gmail.com' , '2004-08-10', '123456789', 100,3),
 ('Daniel', 'daniel@gmail.com', '2004-08-10' , '123456789', 100,4);
 SELECT * FROM student;

 ALTER TABLE student
RENAME COLUMN name TO emri;

SELECT * From student
WHERE emri LIKE 'M%';

SELECT * FROM kursi
WHERE create_date BETWEEN '2023-01-01' AND '2025-12-31';

SELECT * 
FROM student
WHERE EXTRACT(YEAR FROM AGE(birth_date)) < 2000;


SELECT 
  student.id AS student_id,
  student.name AS student_name,
  student.email,
  kursi.emri_kursit,
  kursi.kohezgjatja
FROM student
INNER JOIN kursi ON student.student_key = kursi.id;


SELECT COUNT(id)
FROM student;

SELECT AVG(kohezgjatja)
FROM kursi;

SELECT AVG(pike)
FROM student;


SELECT SUM(pike)
FROM student;

SELECT kursi.emri_kursit, COUNT(student.id)
FROM student
LEFT JOIN kursi ON student.id= kursi.id
GROUP BY emri_kursit;


SELECT * 
FROM student
ORDER BY birth_date;

CREATE TABLE application (
  id SERIAL PRIMARY KEY,
  application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(50),
  student_id INTEGER,
  kursi_id INTEGER,
  FOREIGN KEY (student_id) REFERENCES student(id),
  FOREIGN KEY (kursi_id) REFERENCES kursi(id)
);
INSERT INTO application (status, student_id, kursi_id) VALUES
('Pending', 1, 1),
('Accepted', 2, 1),
('Rejected', 3, 2),
('Pending', 1, 3),
('Accepted', 4, 2);



SELECT * FROM  application;


