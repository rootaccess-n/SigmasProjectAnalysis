
CREATE TABLE Users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       subscription_type VARCHAR(50) NOT NULL DEFAULT 'STANDARD',
                       role VARCHAR(50) NOT NULL DEFAULT 'USER',
                       FOREIGN KEY (role) REFERENCES Roles(role_name),
                       FOREIGN KEY (subscription_type) REFERENCES Subscription(subscription_name)
);


INSERT INTO Users (username, password, subscription_type, role) VALUES
                                                ('admin', 'adminpass', 'PREMIUM', 'ADMIN'),
                                                ('john_doe', 'password123', 'PLUS', 'USER'),
                                                ('alice', 'securepass', 'STANDARD', 'USER'),
                                                ('bob', 'mypassword', DEFAULT, DEFAULT);