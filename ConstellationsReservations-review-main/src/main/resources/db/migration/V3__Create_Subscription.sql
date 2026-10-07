CREATE TABLE Subscription(
    subscription_name VARCHAR(50) UNIQUE NOT NULL PRIMARY KEY
);

INSERT INTO Subscription(subscription_name) VALUES ('STANDARD');
INSERT INTO Subscription(subscription_name)  VALUES ('PLUS') ;
INSERT INTO Subscription(subscription_name)  VALUES ('PREMIUM') ;