CREATE DATABASE expenses;

CREATE TABLE users(
    user_id TEXT PRIMARY KEY,
    user_name TEXT NOT NULL,
    user_email TEXT UNIQUE NOT NULL,
    user_password TEXT NOT NULL
);

CREATE TABLE expense_type(
    expense_type_id INT PRIMARY KEY,
    expense_type TEXT UNIQUE NOT NULL
);

CREATE TABLE expense(
    expense_id INT PRIMARY KEY,
    user_id TEXT REFERENCES users(user_id),
    expense_type_id INT REFERENCES expense_type(expense_type_id),
    amount INT NOT NULL,
    description TEXT NOT NULL,
    payment_date DATE NOT NULL
);