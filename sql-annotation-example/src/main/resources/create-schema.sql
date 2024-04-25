drop table if exists student;
CREATE TABLE student (
  id SERIAL PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  age INT
);