
CREATE TABLE Roles (
                       role_name VARCHAR(50) UNIQUE NOT NULL PRIMARY KEY
);


INSERT INTO Roles (role_name) VALUES
                             ('ADMIN'),
                             ('USER');