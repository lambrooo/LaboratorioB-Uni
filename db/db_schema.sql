
DROP TABLE IF EXISTS ConsigliLibri CASCADE;
DROP TABLE IF EXISTS ValutazioniLibri CASCADE;
DROP TABLE IF EXISTS Librerie CASCADE;
DROP TABLE IF EXISTS Libri CASCADE;
DROP TABLE IF EXISTS UtentiRegistrati CASCADE;

CREATE TABLE UtentiRegistrati (
    userid VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    fiscalCode VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Libri (
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    year VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    PRIMARY KEY (title, author, year)
);

CREATE TABLE Librerie (
    bookshelf_name VARCHAR(255) NOT NULL,
    userid VARCHAR(255) NOT NULL,
    book_title VARCHAR(255) NOT NULL,
    book_author VARCHAR(255) NOT NULL,
    book_year VARCHAR(255) NOT NULL,
    PRIMARY KEY (bookshelf_name, userid, book_title, book_author, book_year),
    FOREIGN KEY (userid) REFERENCES UtentiRegistrati(userid),
    FOREIGN KEY (book_title, book_author, book_year) REFERENCES Libri(title, author, year)
);

CREATE TABLE ValutazioniLibri (
    userid VARCHAR(255) NOT NULL,
    bookshelf_name VARCHAR(255) NOT NULL,
    book_title VARCHAR(255) NOT NULL,
    book_author VARCHAR(255) NOT NULL,
    book_year VARCHAR(255) NOT NULL,
    style_rating INT,
    content_rating INT,
    pleasantness_rating INT,
    originality_rating INT,
    edition_rating INT,
    final_score INT,
    style_comment VARCHAR(256),
    content_comment VARCHAR(256),
    pleasantness_comment VARCHAR(256),
    originality_comment VARCHAR(256),
    edition_comment VARCHAR(256),
    PRIMARY KEY (userid, book_title, book_author, book_year),
    FOREIGN KEY (userid) REFERENCES UtentiRegistrati(userid),
    FOREIGN KEY (book_title, book_author, book_year) REFERENCES Libri(title, author, year),
    FOREIGN KEY (bookshelf_name, userid, book_title, book_author, book_year) REFERENCES Librerie(bookshelf_name, userid, book_title, book_author, book_year)
);

CREATE TABLE ConsigliLibri (
    userid VARCHAR(255) NOT NULL,
    source_book_title VARCHAR(255) NOT NULL,
    source_book_author VARCHAR(255) NOT NULL,
    source_book_year VARCHAR(255) NOT NULL,
    suggested_book_title VARCHAR(255) NOT NULL,
    suggested_book_author VARCHAR(255) NOT NULL,
    suggested_book_year VARCHAR(255) NOT NULL,
    PRIMARY KEY (userid, source_book_title, source_book_author, source_book_year, suggested_book_title, suggested_book_author, suggested_book_year),
    FOREIGN KEY (userid) REFERENCES UtentiRegistrati(userid),
    FOREIGN KEY (source_book_title, source_book_author, source_book_year) REFERENCES Libri(title, author, year),
    FOREIGN KEY (suggested_book_title, suggested_book_author, suggested_book_year) REFERENCES Libri(title, author, year)
);
