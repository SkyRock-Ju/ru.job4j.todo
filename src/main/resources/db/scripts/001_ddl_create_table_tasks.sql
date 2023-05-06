CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   description TEXT,
   created TIMESTAMP NOT NULL,
   done BOOLEAN NOT NULL
);