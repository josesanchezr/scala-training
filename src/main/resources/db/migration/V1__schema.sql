DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
    id NUMERIC(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    company VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    telephone NUMERIC(20) NOT NULL,
    status VARCHAR(10) NULL
);

ALTER TABLE employee ADD CONSTRAINT employee_id PRIMARY KEY(id);