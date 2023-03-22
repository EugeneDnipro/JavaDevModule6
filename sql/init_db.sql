CREATE TABLE worker(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR NOT NULL,
	birthday DATE,
	level VARCHAR NOT NULL,
	salary BIGINT
);
ALTER TABLE worker ADD CONSTRAINT check_worker_name CHECK(LENGTH(name) >= 2 AND LENGTH(name) <= 1000);
ALTER TABLE worker ADD CONSTRAINT check_worker_birthday CHECK(year(birthday) > 1900 );
ALTER TABLE worker ADD CONSTRAINT check_worker_level CHECK(level IN ('Trainee', 'Junior', 'Middle', 'Senior'));
ALTER TABLE worker ADD CONSTRAINT check_worker_salary CHECK(salary > 100 AND salary < 100000);

CREATE TABLE client(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR NOT NULL
);
ALTER TABLE client ADD CONSTRAINT check_client_name CHECK(LENGTH(name) >= 2 AND LENGTH(name) <= 1000);

CREATE TABLE project(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	client_id INTEGER,
	start_date DATE,
	finish_date DATE
);

CREATE TABLE project_worker (
    project_id INTEGER,
    worker_id INTEGER,
    PRIMARY KEY (project_id, worker_id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (worker_id) REFERENCES worker(id)
);