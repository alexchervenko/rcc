CREATE TABLE IF NOT EXISTS country (
    id        BINARY(16)    NOT NULL,
    name      VARCHAR(255)  NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE INDEX idx_country_name ON country(name);