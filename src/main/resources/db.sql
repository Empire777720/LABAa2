CREATE TABLE tv_models (
                           id SERIAL PRIMARY KEY,
                           title VARCHAR(255) NOT NULL,
                           price NUMERIC(10,2) NOT NULL,
                           image_url TEXT NOT NULL,
                           product_url TEXT NOT NULL
);