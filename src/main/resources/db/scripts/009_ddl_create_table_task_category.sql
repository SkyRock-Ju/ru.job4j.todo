CREATE TABLE task_category (
   id serial PRIMARY KEY,
   task_id int not null REFERENCES tasks(id) ON DELETE CASCADE,
   category_id int not null REFERENCES categories(id) ON DELETE CASCADE
);