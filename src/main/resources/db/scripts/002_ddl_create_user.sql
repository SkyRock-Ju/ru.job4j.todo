CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   login TEXT NOT NULL,
   password TEXT NOT NULL
);