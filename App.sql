-- Таблица genres
CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE CHECK (char_length(name) > 0)
);

-- Таблица books
CREATE TABLE books (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL CHECK (char_length(title) > 0),
    description TEXT,
    genre_id INT REFERENCES genres(id) ON DELETE SET NULL,
    is_available BOOLEAN DEFAULT TRUE,
    publication_date DATE CHECK (publication_date <= CURRENT_DATE),
    popularity_score REAL CHECK (popularity_score BETWEEN 0 AND 10),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);


-- Таблица authors
CREATE TABLE authors (
    id UUID PRIMARY KEY,
    surname VARCHAR(50) NOT NULL CHECK (char_length(surname) > 0),
    name VARCHAR(255) NOT NULL CHECK (char_length(name) > 0),
    patronymic VARCHAR(255),
    birth_date DATE CHECK (birth_date <= CURRENT_DATE),
    biography TEXT
);


-- Таблица book_authors
CREATE TABLE book_authors (
    book_id UUID NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    author_id UUID NOT NULL REFERENCES authors(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

-- Таблица readers
CREATE TABLE readers (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL CHECK (char_length(name) > 0),
    surname VARCHAR(255) NOT NULL CHECK (char_length(surname) > 0),
    patronymic VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    birth_date DATE CHECK (birth_date <= CURRENT_DATE),
    registration_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    phone_number VARCHAR(15) UNIQUE CHECK (phone_number ~ '^[0-9\-\+]{10,15}$')
);


-- Таблица loans
CREATE TABLE loans (
    id UUID PRIMARY KEY,
    book_id UUID NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    reader_id UUID NOT NULL REFERENCES readers(id) ON DELETE CASCADE,
    loan_date DATE NOT NULL CHECK (loan_date <= CURRENT_DATE),
    penalty NUMERIC(19, 2) CHECK (penalty >= 0),
    loan_duration INTERVAL,
    status VARCHAR(50) DEFAULT 'active' CHECK (status IN ('active', 'overdue', 'closed'))
);

INSERT INTO genres (name) VALUES
    ('Fiction'),
    ('Science Fiction'),
    ('Fantasy'),
    ('Mystery'),
    ('Romance'),
    ('Thriller'),
    ('Horror'),
    ('Historical'),
    ('Biography'),
    ('Self-Help');

INSERT INTO books (id, title, description, genre_id, is_available, publication_date, popularity_score, created_at)
VALUES
    (gen_random_uuid(), 'Book 1', 'Description for Book 1', 1, true, '2023-01-01', 8.5, now()),
    (gen_random_uuid(), 'Book 2', 'Description for Book 2', 2, true, '2023-02-01', 7.2, now()),
    (gen_random_uuid(), 'Book 3', 'Description for Book 3', 3, false, '2023-03-01', 6.8, now()),
    (gen_random_uuid(), 'Book 4', 'Description for Book 4', 4, true, '2023-04-01', 9.1, now()),
    (gen_random_uuid(), 'Book 5', 'Description for Book 5', 5, true, '2023-05-01', 7.7, now()),
    (gen_random_uuid(), 'Book 6', 'Description for Book 6', 6, false, '2023-06-01', 8.3, now()),
    (gen_random_uuid(), 'Book 7', 'Description for Book 7', 7, true, '2023-07-01', 6.5, now()),
    (gen_random_uuid(), 'Book 8', 'Description for Book 8', 8, true, '2023-08-01', 7.9, now()),
    (gen_random_uuid(), 'Book 9', 'Description for Book 9', 9, false, '2023-09-01', 9.0, now()),
    (gen_random_uuid(), 'Book 10', 'Description for Book 10', 10, true, '2023-10-01', 8.0, now());

INSERT INTO authors (id, surname, name, patronymic, birth_date, biography)
VALUES
    (gen_random_uuid(), 'Smith', 'John', 'Edward', '1980-01-15', 'Biography of John Smith'),
    (gen_random_uuid(), 'Doe', 'Jane', 'Marie', '1990-02-10', 'Biography of Jane Doe'),
    (gen_random_uuid(), 'Brown', 'James', 'Patrick', '1985-03-20', 'Biography of James Brown'),
    (gen_random_uuid(), 'Johnson', 'Emily', 'Sophia', '1975-04-25', 'Biography of Emily Johnson'),
    (gen_random_uuid(), 'Williams', 'Michael', 'David', '1988-05-30', 'Biography of Michael Williams'),
    (gen_random_uuid(), 'Taylor', 'Sarah', 'Ann', '1992-06-05', 'Biography of Sarah Taylor'),
    (gen_random_uuid(), 'Anderson', 'Robert', 'John', '1970-07-12', 'Biography of Robert Anderson'),
    (gen_random_uuid(), 'Thomas', 'Laura', 'Elizabeth', '1983-08-20', 'Biography of Laura Thomas'),
    (gen_random_uuid(), 'Moore', 'Daniel', 'James', '1995-09-25', 'Biography of Daniel Moore'),
    (gen_random_uuid(), 'Jackson', 'Sophia', 'Grace', '2000-10-10', 'Biography of Sophia Jackson');

SELECT * FROM books;
SELECT * FROM authors;

INSERT INTO book_authors (book_id, author_id)
VALUES
-- Связываем книги с авторами
('c83a7c13-be0f-4430-bd88-93fcb6e6db39', '78dc4c3c-bed5-4650-805e-3594794723d6'),
('54b27be1-6cdd-4d87-a322-ee8f3f235efb', 'ab48a866-2ad6-4a5e-9256-1f91c3d29350'),
('da3ac4e3-f758-4c6e-8e21-c564a266c515', '675764ab-73e3-49bb-bd8a-8be1acd24952'),
('c9ac8b12-ae32-4133-b7d4-69ec73e5b833', '888eb31d-bbc1-474e-bc37-9527929aa308'),
('db62a987-3153-49af-a536-c52c25b4cb5f', '7b63999c-676f-4c3f-a34d-866d4bb18d5d'),
('5d79c22e-5075-40bb-8f0d-b713777e9b57', '29874048-4fc6-43c3-8182-4002e664714c'),
('05412c9d-56e8-4bf9-b579-00a48db4becf', '632a7be9-1f30-4315-9749-f00b6cc508d5'),
('5660d3e7-9514-4ced-8090-27ac6e2e5262', 'b042e419-f0cd-41d9-b3b6-f7499d8033af'),
('ca6abd0f-7679-4824-bfd7-868029f57266', '40d56427-59e6-449c-8261-6923a5740273'),
('d4c122d2-fee5-4b14-839c-f5815f094d56', 'b2301924-d73a-4f68-83ba-2bfc62bf34f9');

INSERT INTO readers (id, name, surname, patronymic, email, birth_date, registration_time, phone_number)
VALUES
    (gen_random_uuid(), 'Alice', 'Johnson', 'Marie', 'alice.johnson@example.com', '1990-01-01', now(), '+1234567890'),
    (gen_random_uuid(), 'Bob', 'Smith', 'Edward', 'bob.smith@example.com', '1985-02-15', now(), '+1234567891'),
    (gen_random_uuid(), 'Charlie', 'Brown', 'Patrick', 'charlie.brown@example.com', '1995-03-20', now(), '+1234567892'),
    (gen_random_uuid(), 'Diana', 'Lee', 'Sophia', 'diana.lee@example.com', '1988-04-10', now(), '+1234567893'),
    (gen_random_uuid(), 'Ethan', 'Taylor', 'James', 'ethan.taylor@example.com', '1992-05-05', now(), '+1234567894'),
    (gen_random_uuid(), 'Fiona', 'Williams', 'Ann', 'fiona.williams@example.com', '1998-06-15', now(), '+1234567895'),
    (gen_random_uuid(), 'George', 'Anderson', 'David', 'george.anderson@example.com', '1983-07-25', now(), '+1234567896'),
    (gen_random_uuid(), 'Hannah', 'Thomas', 'Elizabeth', 'hannah.thomas@example.com', '1979-08-30', now(), '+1234567897'),
    (gen_random_uuid(), 'Ian', 'Moore', 'Michael', 'ian.moore@example.com', '1990-09-10', now(), '+1234567898'),
    (gen_random_uuid(), 'Julia', 'Jackson', 'Grace', 'julia.jackson@example.com', '1987-10-20', now(), '+1234567899');

SELECT * FROM readers;

INSERT INTO loans (id, book_id, reader_id, loan_date, penalty, loan_duration, status)
VALUES
    (gen_random_uuid(), 'c83a7c13-be0f-4430-bd88-93fcb6e6db39', 'd27e4794-cbcc-474b-a574-38e7a7071c12', '2024-01-01', 0.00, '14 days', 'active'),
    (gen_random_uuid(), '54b27be1-6cdd-4d87-a322-ee8f3f235efb', '16a5956b-852b-4688-bbe9-93ba2f897533', '2024-01-05', 1.50, '7 days', 'overdue'),
    (gen_random_uuid(), 'da3ac4e3-f758-4c6e-8e21-c564a266c515', 'b88eb651-3006-4170-a449-8bc7ea033aba', '2024-01-10', 0.00, '21 days', 'closed'),
    (gen_random_uuid(), 'c9ac8b12-ae32-4133-b7d4-69ec73e5b833', 'a20b1456-7b0a-426b-9a05-4fb620735055', '2024-01-15', 2.00, '14 days', 'active'),
    (gen_random_uuid(), 'db62a987-3153-49af-a536-c52c25b4cb5f', '216cb983-43e6-4d48-977c-ad90f3880f42', '2024-01-20', 0.00, '30 days', 'closed'),
    (gen_random_uuid(), '5d79c22e-5075-40bb-8f0d-b713777e9b57', '5d822c81-af82-4a71-8851-082903595547', '2024-01-25', 3.00, '7 days', 'overdue'),
    (gen_random_uuid(), '05412c9d-56e8-4bf9-b579-00a48db4becf', 'a44ae232-21c5-4bef-ac59-99f86984041e', '2024-02-01', 0.00, '14 days', 'active'),
    (gen_random_uuid(), '5660d3e7-9514-4ced-8090-27ac6e2e5262', 'f315f565-7579-4aa4-892d-ec7d93b91a64', '2024-02-05', 0.00, '21 days', 'closed'),
    (gen_random_uuid(), 'ca6abd0f-7679-4824-bfd7-868029f57266', '81714d22-2c04-420b-a6ec-7b5ec65ee705', '2024-02-10', 0.00, '7 days', 'active'),
    (gen_random_uuid(), 'd4c122d2-fee5-4b14-839c-f5815f094d56', '66c95690-772e-46e9-9b13-f68d30464e5b', '2024-02-15', 0.00, '30 days', 'closed');

SELECT * FROM loans;
SET lc_monetary TO 'en_US.UTF-8';