DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS favorite_stores CASCADE;
DROP TABLE IF EXISTS stores CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    id            bigint generated always as identity,
    name          varchar(10) NOT NULL,
    email         varchar(50) NOT NULL,
    password      varchar(90) NOT NULL,
    login_count   int         NOT NULL DEFAULT 0,
    last_login_at timestamp            DEFAULT NULL,
    created_at    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TYPE store_state AS ENUM ('HIDDEN', 'NORMAL');
CREATE TABLE stores
(
    id          bigint generated always as identity,
    name        varchar(20) NOT NULL,
    store_state store_state          DEFAULT 'NORMAL' NOT NULL,
    off_day     int         NOT NULL,
    is_run_24   int         NOT NULL,
    open_time   int         NOT NULL,
    close_time  int         NOT NULL,
    created_at  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE favorite_stores
(
    id         bigint generated always as identity,
    user_id    bigint    NOT NULL,
    store_id   bigint    NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT unique_user_store UNIQUE (user_id, store_id),
    CONSTRAINT fk_favorite_to_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_favorite_to_stores FOREIGN KEY (store_id) REFERENCES stores (id)
);

CREATE TYPE order_state as ENUM ('NEW', 'COMPLETE', 'CANCEL');
CREATE TABLE orders
(
    id             bigint generated always as identity,
    user_id        bigint    NOT NULL,
    store_id       bigint    NOT NULL,
    order_state    order_state        DEFAULT 'NEW' NOT NULL,
    cancel_message varchar(512)       DEFAULT NULL,
    canceled_at    timestamp          DEFAULT NULL,
    completed_at   timestamp          DEFAULT NULL,
    created_at     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_orders_to_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_orders_to_stores FOREIGN KEY (store_id) REFERENCES stores (id)
);

CREATE TABLE order_items
(
    id         bigint generated always as identity,
    order_id   bigint      NOT NULL,
    name       varchar(50) NOT NULL,
    unit_price int         NOT NULL,
    unit_count int         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_items_to_orders FOREIGN KEY (order_id) REFERENCES orders (id)
);
