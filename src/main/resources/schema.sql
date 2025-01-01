-- Table: Manga
CREATE TABLE IF NOT EXISTS manga
(
    id             INT PRIMARY KEY,
    title          VARCHAR(255),
    title_english  VARCHAR(255),
    title_japanese VARCHAR(255),
    type           VARCHAR(255),
    chapters       INT,
    volumes        INT,
    status         VARCHAR(255),
    publishing     BOOLEAN,
    published_from DATETIME,
    published_to   DATETIME,
    score          DECIMAL(3, 2),
    scored_by      INT,
    ranking        INT,
    popularity     INT,
    synopsis       TEXT
);

-- Table: Images
CREATE TABLE IF NOT EXISTS images
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    image_url       VARCHAR(255),
    small_image_url VARCHAR(255),
    large_image_url VARCHAR(255),
    manga_id        INT,
    FOREIGN KEY (manga_id) REFERENCES manga (id)
);

-- Table: Authors
CREATE TABLE IF NOT EXISTS authors
(
    id   INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS manga_authors
(
    manga_id  INT,
    author_id INT,
    PRIMARY KEY (manga_id, author_id),
    FOREIGN KEY (manga_id) REFERENCES manga (id),
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

-- Table: Genres
CREATE TABLE IF NOT EXISTS genres
(
    id   INT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS manga_genres
(
    manga_id INT,
    genre_id INT,
    PRIMARY KEY (manga_id, genre_id),
    FOREIGN KEY (manga_id) REFERENCES manga (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,                                 -- Unique internal ID for the user
    oauth_provider      VARCHAR(50),                                                    -- The provider used for authentication (e.g., "google" or "github")
    oauth_provider_id   VARCHAR(255) UNIQUE,                                            -- The unique identifier from the OAuth provider (e.g., Google ID or GitHub ID)
    username            VARCHAR(100),                                                   -- Username (if applicable)
    email               VARCHAR(255) UNIQUE,                                            -- User's email address
    first_name          VARCHAR(100),                                                   -- User's first name
    last_name           VARCHAR(100),                                                   -- User's last name
    profile_picture_url VARCHAR(255),                                                   -- URL to the user's profile picture (optional)
    access_token        VARCHAR(255),                                                   -- Access token (if you need to store it for API requests)
    refresh_token       VARCHAR(255),
    role                VARCHAR(255),-- Refresh token (optional, if you are storing it)
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- Date and time when the user was created
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Date and time when the user was last updated
);

CREATE TABLE IF NOT EXISTS saved_manga
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    user_id  INT,
    manga_id INT,
    personal_rating FLOAT DEFAULT NULL,
    current_chapter INT DEFAULT 0,
    status VARCHAR(50) DEFAULT 'not started',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (manga_id) REFERENCES manga (id),
    UNIQUE (manga_id, user_id)
);

CREATE TABLE IF NOT EXISTS lists
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    list_name   VARCHAR(255),
    description TEXT,
    user_id     INT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS list_manga
(
    list_id        INT,
    saved_manga_id INT,
    PRIMARY KEY (list_id, saved_manga_id),
    FOREIGN KEY (list_id) REFERENCES lists (id),
    FOREIGN KEY (saved_manga_id) REFERENCES saved_manga (id)
);

CREATE TABLE IF NOT EXISTS notifications
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    message     TEXT,
    read_status BOOLEAN,
    user_id     INT,
    timestamp   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    review_text TEXT,
    title       VARCHAR(255),
    spoiler     BOOLEAN,
    rating      DECIMAL(3, 2),
    user_id     INT,
    manga_id    INT,
    timestamp   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (manga_id) REFERENCES manga (id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    comment_text TEXT,
    user_id      INT,
    review_id    INT,
    timestamp    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (review_id) REFERENCES reviews (id)
);

CREATE TABLE IF NOT EXISTS tags
(
    id       INT PRIMARY KEY,
    tag_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS manga_tags
(
    manga_id INT,
    tag_id   INT,
    PRIMARY KEY (manga_id, tag_id),
    FOREIGN KEY (manga_id) REFERENCES manga (id),
    FOREIGN KEY (tag_id) REFERENCES tags (id)
);

