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

