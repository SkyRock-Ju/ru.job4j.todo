CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   description TEXT,
   created TIMESTAMP NOT NULL,
   done BOOLEAN NOT NULL
);