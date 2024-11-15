-- table comments
CREATE TABLE IF NOT EXISTS comments (
                                        comment_id SERIAL PRIMARY KEY,
                                        user_id INTEGER NOT NULL,
                                        location_id INTEGER NOT NULL,
                                        comment_text TEXT,
                                        rating INTEGER CHECK (rating BETWEEN 1 AND 5)
    );

-- Insertion de données initiales (optionnel)
INSERT INTO comments (user_id, location_id, comment_text, rating) VALUES
                                                                      (1, 1, 'Très bon endroit', 5),
                                                                      (2, 1, 'Pas mal du tout', 4),
                                                                      (3, 2, 'Peut mieux faire', 3);
