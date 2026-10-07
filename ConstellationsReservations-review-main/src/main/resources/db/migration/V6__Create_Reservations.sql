CREATE TABLE Reservations (
                          id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL,
                          constellation_id INT NOT NULL,
                          status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                          start_time TIMESTAMP NOT NULL,
                          end_time TIMESTAMP NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES Users(id),
                          FOREIGN KEY (constellation_id) REFERENCES Constellations(id),
                          FOREIGN KEY (status) REFERENCES Status(status_name)
);
