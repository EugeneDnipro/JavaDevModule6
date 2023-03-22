INSERT INTO worker (id, name, birthday, level, salary) VALUES (1, 'Jeremy', '1960-04-11', 'Senior', 25000);
INSERT INTO worker
    (id, name, birthday, level, salary)
    VALUES
        (2, 'James', '1963-01-16', 'Middle', 10000),
        (3, 'Richard', '1969-12-19', 'Junior', 5000),
        (4, 'Tom', '1995-05-01', 'Middle', 11000),
        (5, 'Jerry', '1996-06-07', 'Trainee', 900),
        (6, 'Michael', '1969-01-03', 'Senior', 95000),
        (7, 'Ayrton', '1960-03-21', 'Senior', 75000),
        (8, 'Tony', '1970-09-20', 'Middle', 12500),
        (9, 'Lara', '1985-08-15', 'Junior', 3500),
        (10, 'Mark', '1995-07-25', 'Trainee', 500);

INSERT INTO client
    (id, name)
    VALUES
        (1, 'Intel'),
        (2, 'AMD'),
        (3, 'Microsoft'),
        (4, 'Oracle'),
        (5, 'Apple');

INSERT INTO project
    (id, client_id, start_date, finish_date)
    VALUES
        (1, 1, '2022-09-01', '2022-12-01'),
        (2, 1, '2021-01-01', '2023-07-01'),
        (3, 2, '2022-10-01', '2023-10-01'),
        (4, 2, '2021-12-01', '2023-12-01'),
        (5, 2, '2022-11-01', '2024-11-01'),
        (6, 3, '2020-07-01', '2025-01-01'),
        (7, 3, '2022-03-01', '2023-04-01'),
        (8, 5, '2023-01-01', '2026-05-01'),
        (9, 4, '2022-05-01', '2024-02-01'),
        (10, 4, '2023-02-01', '2025-08-01');

INSERT INTO project_worker
    (project_id, worker_id)
    VALUES
        (1, 1),
        (2, 1),
        (2, 10),
        (2, 8),
        (3, 6),
        (3, 9),
        (4, 7),
        (4, 4),
        (4, 3),
        (4, 9),
        (5, 1),
        (5, 4),
        (5, 10),
        (6, 6),
        (6, 7),
        (6, 2),
        (6, 3),
        (6, 9),
        (7, 4),
        (7, 10),
        (8, 1),
        (8, 3),
        (8, 9),
        (9, 2),
        (9, 4),
        (9, 8),
        (10, 1),
        (10, 2),
        (10, 8),
        (10, 3);



