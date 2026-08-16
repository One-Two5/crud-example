CREATE TABLE product
(
    id       BIGINT NOT NULL,
    name     VARCHAR(255),
    quantity VARCHAR(255),
    CONSTRAINT pk_product PRIMARY KEY (id)
);