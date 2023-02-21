-- liquibase formatted sql

-- changeset EvgenyF:1

CREATE TABLE users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email      VARCHAR,
    first_name VARCHAR,
    last_name  VARCHAR,
    phone      VARCHAR,
    reg_date   VARCHAR,
    city       VARCHAR,
    password   VARCHAR,
    image      VARCHAR
);


-- changeset EvgeniyL:3
CREATE TABLE comments
(
    pk         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id    BIGINT REFERENCES users (id),
    created_at VARCHAR,
    text       TEXT
);


-- changeset GlebD:4
CREATE TABLE ads
(
    pk          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description VARCHAR,
    title       VARCHAR,
    user_id     BIGINT REFERENCES users (id),
    price       BIGINT,
    image_ads   VARCHAR[]

);

-- changeset GlebD:5

CREATE TABLE image
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    file_path  VARCHAR,
    file_size  BIGINT,
    media_type VARCHAR,
    data       BYTEA,
    ads_pk     BIGINT REFERENCES ads (pk)
);
-- changeset EvgenyF:6
CREATE TABLE avatars
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    file_size  BIGINT,
    media_type varchar,
    data       bytea,
    user_id    BIGINT REFERENCES users (id)
);


