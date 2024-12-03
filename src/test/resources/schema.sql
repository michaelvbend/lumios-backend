-- Create User table if it does not exist
CREATE TABLE IF NOT EXISTS _user  (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Create Book table if it does not exist
CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    genre VARCHAR(255),
    published_date VARCHAR(255),
    page_count VARCHAR(255),
    language VARCHAR(255),
    thumbnail VARCHAR(255),
    rating FLOAT
);

-- Create Rating table if it does not exist
CREATE TABLE IF NOT EXISTS rating (
    book_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating_value INTEGER NOT NULL,
    PRIMARY KEY (book_id, user_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE
);
