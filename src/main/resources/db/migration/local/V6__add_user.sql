INSERT INTO users (username, password, email, phone_number, registration_date,
                   first_name, last_name, role)
VALUES ('Leva12', '$2a$12$EPkeblBi8JjzDzcqSZDKyOpdlQRdCojL92K7g1PqA9EPVLhDUUsoK',
        'example@example.com', '+1234567890', current_timestamp, 'Lev', 'Doe', 'USER');

INSERT INTO users (username, password, email, phone_number, registration_date,
                   first_name, last_name, role)
VALUES ('Ivan34', '$2a$12$EPkeblBi8JjzDzcqSZDKyOpdlQRdCojL92K7g1PqA9EPVLhDUUsoK',
        'example@example2.com', '+0987654321', current_timestamp, 'Ivan', 'Stew', 'ADMIN');