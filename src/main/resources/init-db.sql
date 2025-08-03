CREATE DATABASE sprt_todo;
CREATE USER 'todo_user'@'localhost' IDENTIFIED BY 'todo_password';
GRANT ALL PRIVILEGES ON sprt_todo.* TO 'todo_user'@'localhost';
FLUSH PRIVILEGES;