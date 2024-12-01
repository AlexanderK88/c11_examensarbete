-- Table: Manga
CREATE TABLE IF NOT EXISTS manga (
                       id INT PRIMARY KEY,
                       title VARCHAR(255),
                       title_english VARCHAR(255),
                       title_japanese VARCHAR(255),
                       type VARCHAR(255),
                       chapters INT,
                       volumes INT,
                       status VARCHAR(255),
                       publishing BOOLEAN,
                       published_from DATETIME,
                       published_to DATETIME,
                       score DECIMAL(3, 2),
                       scored_by INT,
                       ranking INT,
                       popularity INT,
                       synopsis TEXT
);

-- Table: Images
CREATE TABLE IF NOT EXISTS images (
                        id INT PRIMARY KEY,
                        image_url VARCHAR(255),
                        small_image_url VARCHAR(255),
                        large_image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS manga_images (
                              manga_id INT,
                              image_id INT,
                              PRIMARY KEY (manga_id, image_id),
                              FOREIGN KEY (manga_id) REFERENCES manga(id),
                              FOREIGN KEY (image_id) REFERENCES images(id)
);

-- Table: Authors
CREATE TABLE IF NOT EXISTS authors (
                         id INT PRIMARY KEY,
                         name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS manga_authors (
                               manga_id INT,
                               author_id INT,
                               PRIMARY KEY (manga_id, author_id),
                               FOREIGN KEY (manga_id) REFERENCES manga(id),
                               FOREIGN KEY (author_id) REFERENCES authors(id)
);

-- Table: Genres
CREATE TABLE IF NOT EXISTS genres (
                        id INT PRIMARY KEY,
                        name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS manga_genres (
                              manga_id INT,
                              genre_id INT,
                              PRIMARY KEY (manga_id, genre_id),
                              FOREIGN KEY (manga_id) REFERENCES manga(id),
                              FOREIGN KEY (genre_id) REFERENCES genres(id)
);

CREATE TABLE IF NOT EXISTS users (
                        id INT PRIMARY KEY,
                        username VARCHAR(255),
                        password VARCHAR(255),
                        email VARCHAR(255),
                        role VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS saved_manga (
                                user_id INT,
                                manga_id INT,
                                list_id INT,
                                PRIMARY KEY (user_id, manga_id),
                                FOREIGN KEY (user_id) REFERENCES users(id),
                                FOREIGN KEY (manga_id) REFERENCES manga(id)
)
