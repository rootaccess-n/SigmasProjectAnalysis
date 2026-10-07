CREATE TABLE Status(
    status_name VARCHAR(50) UNIQUE NOT NULL PRIMARY KEY
);

INSERT INTO Status(status_name) VALUES ('PENDING');
INSERT INTO Status(status_name)  VALUES ('ACTIVE') ;
INSERT INTO Status(status_name)  VALUES ('DELETED') ;