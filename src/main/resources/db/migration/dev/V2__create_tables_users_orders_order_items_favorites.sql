CREATE TABLE users
(
    user_id bigserial primary key ,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    phone_number varchar(255),
    registration_date timestamp,
    first_name varchar(255),
    last_name varchar(255)
);

CREATE TABLE orders
(
    order_id bigserial primary key ,
    user_id bigint not null references users (user_id),
    order_date timestamp,
    status varchar(255)
);

CREATE TABLE order_items
(
    book_id bigint not null references books (book_id),
    order_id bigint not null references orders (order_id),
    price decimal(8, 2) not null ,
    primary key (book_id, order_id)
);

CREATE TABLE favorites
(
    user_id bigint not null references users (user_id),
    book_id bigint not null references books (book_id),
    primary key (user_id, book_id)
);
