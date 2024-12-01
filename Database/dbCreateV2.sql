-- Extension PostGis en la base de datos si no existe
CREATE EXTENSION IF NOT EXISTS postgis;

DROP TABLE IF EXISTS point;
DROP TABLE IF EXISTS person_point;
DROP TABLE IF EXISTS institution;
DROP TABLE IF EXISTS person_institution;
DROP TABLE IF EXISTS emergency;
DROP TABLE IF EXISTS task_emergency;
DROP TABLE IF EXISTS person_task;
DROP TABLE IF EXISTS emergency_person;
DROP TABLE IF EXISTS emergency_attribute;
DROP TABLE IF EXISTS emergency_point;
DROP TABLE IF EXISTS supply;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS task_ttype;
DROP TABLE IF EXISTS ttype;
DROP TABLE IF EXISTS task;

-- Creación de tablas
CREATE TABLE point IF NOT EXISTS(
    point_id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    geom GEOMETRY(Point, 4326)
);

CREATE TABLE person_point IF NOT EXISTS(
    person_point_id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(20),
    point_id BIGINT,
    FOREIGN KEY (point_id) REFERENCES point(point_id)
);

CREATE TABLE institution IF NOT EXISTS(
    institution_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE person_institution IF NOT EXISTS(
    person_institution_id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(20),
    institution_id BIGINT,
    FOREIGN KEY (institution_id) REFERENCES institution(institution_id)
);

CREATE TABLE emergency IF NOT EXISTS(
    emergency_id BIGSERIAL PRIMARY KEY,
    status BOOLEAN NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL
    );

CREATE TABLE emergency_person IF NOT EXISTS(
    emergency_person_id BIGSERIAL PRIMARY KEY,
    emergency_id BIGINT,
    rut VARCHAR(20),
    FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id)
);

CREATE TABLE emergency_point IF NOT EXISTS(
    emergency_point_id BIGSERIAL PRIMARY KEY,
    emergency_id BIGINT,
    point_id BIGINT,
    FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id),
    FOREIGN KEY (point_id) REFERENCES point(point_id)
);

CREATE TABLE emergency_attribute IF NOT EXISTS(
    emergency_attribute_id BIGSERIAL PRIMARY KEY,
    emergency_id BIGINT,
    skill_code VARCHAR(20),
    compatibility BOOLEAN NOT NULL,
    FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id)
);

CREATE TABLE task IF NOT EXISTS(
    task_id BIGSERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    status BOOLEAN NOT NULL
);

-- Tipo de tarea
CREATE TABLE ttype IF NOT EXISTS(
    ttype_id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL
);

-- Relacion de tarea con tipo
CREATE TABLE task_ttype IF NOT EXISTS(
    task_ttype_id BIGSERIAL PRIMARY KEY,
    task_id BIGINT,
    ttype_id BIGINT,
    FOREIGN KEY (task_id) REFERENCES task(task_id),
    FOREIGN KEY (ttype_id) REFERENCES ttype(ttype_id)
);

CREATE TABLE task_emergency IF NOT EXISTS(
    task_emergency_id BIGSERIAL PRIMARY KEY,
    task_id BIGINT,
    emergency_id BIGINT,
    FOREIGN KEY (task_id) REFERENCES task(task_id),
    FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id)
);

CREATE TABLE person_task IF NOT EXISTS(
    person_task_id BIGSERIAL PRIMARY KEY,
    task_id BIGINT,
    rut VARCHAR(20),
    FOREIGN KEY (task_id) REFERENCES task(task_id)
);

------ Nuevas tablas para DevSecOps

CREATE TABLE supply IF NOT EXISTS(
    supply_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(100),
    classification VARCHAR(50)
);

CREATE TABLE inventory IF NOT EXISTS(
    inventory_id BIGSERIAL PRIMARY KEY,
    emergency_id BIGINT,
    supply_id BIGINT,
    requested INT,
    stock INT,
    missing VARCHAR(30),
    priority VARCHAR(50),
    FOREIGN KEY (supply_id) REFERENCES supply(supply_id),
    FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id)
);


------- Indexes -------

-- Point
CREATE INDEX idx_point_id ON point (point_id);

-- Institution
CREATE INDEX idx_institution_id ON institution (institution_id);

-- Emergency
CREATE INDEX idx_emergency_id ON emergency (emergency_id);
CREATE INDEX idx_emergency_status ON emergency (status);

-- Task
CREATE INDEX idx_task_id ON task (task_id);
CREATE INDEX idx_task_status ON task (status);
