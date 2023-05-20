ALTER TABLE tasks ADD COLUMN IF NOT EXISTS user_id int REFERENCES users(id);
ALTER TABLE tasks ADD COLUMN IF NOT EXISTS priority_id int REFERENCES priorities(id);