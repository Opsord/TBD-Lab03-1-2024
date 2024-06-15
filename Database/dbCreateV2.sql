-- Extension PostGis en la base de datos si no existe
CREATE EXTENSION IF NOT EXISTS postgis;

-- Creación de tablas
CREATE TABLE point (
    point_id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    geom GEOMETRY(Point, 4326)
);

CREATE TABLE person (
    rut VARCHAR(20) PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    sex VARCHAR(1) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    availability BOOLEAN NOT NULL
);

CREATE TABLE person_point (
    person_point_id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(20),
    point_id BIGINT,
    FOREIGN KEY (rut) REFERENCES person(rut),
    FOREIGN KEY (point_id) REFERENCES point(point_id)
);

CREATE TABLE attribute (
    attribute_id BIGSERIAL PRIMARY KEY,
    attribute VARCHAR(255) NOT NULL
);

CREATE TABLE person_attribute (
    person_attribute_id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(20),
    attribute_id BIGINT,
    FOREIGN KEY (rut) REFERENCES person(rut),
    FOREIGN KEY (attribute_id) REFERENCES attribute(attribute_id)
);

CREATE TABLE institution (
    institution_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE person_institution (
    person_institution_id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(20),
    institution_id BIGINT,
    FOREIGN KEY (rut) REFERENCES person(rut),
    FOREIGN KEY (institution_id) REFERENCES institution(institution_id)
);

CREATE TABLE emergency (
    emergency_id BIGSERIAL PRIMARY KEY,
    status BOOLEAN NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL
    );

    CREATE TABLE emergency_person (
        emergency_person_id BIGSERIAL PRIMARY KEY,
        emergency_id BIGINT,
        rut VARCHAR(20),
        FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id),
        FOREIGN KEY (rut) REFERENCES person(rut)
    );

CREATE TABLE emergency_point (
    emergency_point_id BIGSERIAL PRIMARY KEY,
    emergency_id BIGINT,
    point_id BIGINT,
    FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id),
    FOREIGN KEY (point_id) REFERENCES point(point_id)
);

CREATE TABLE emergency_attribute (
    emergency_attribute_id BIGSERIAL PRIMARY KEY,
    emergency_id BIGINT,
    attribute_id BIGINT,
    compatibility BOOLEAN NOT NULL,
    FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id),
    FOREIGN KEY (attribute_id) REFERENCES attribute(attribute_id)
);

CREATE TABLE task (
    task_id BIGSERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    status BOOLEAN NOT NULL
);

-- Tipo de tarea
CREATE TABLE ttype (
    ttype_id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL
);

-- Relacion de tarea con tipo
CREATE TABLE task_ttype (
    task_ttype_id BIGSERIAL PRIMARY KEY,
    task_id BIGINT,
    ttype_id BIGINT,
    FOREIGN KEY (task_id) REFERENCES task(task_id),
    FOREIGN KEY (ttype_id) REFERENCES ttype(ttype_id)
);

CREATE TABLE task_emergency (
    task_emergency_id BIGSERIAL PRIMARY KEY,
    task_id BIGINT,
    emergency_id BIGINT,
    FOREIGN KEY (task_id) REFERENCES task(task_id),
    FOREIGN KEY (emergency_id) REFERENCES emergency(emergency_id)
);

CREATE TABLE person_task (
    person_task_id BIGSERIAL PRIMARY KEY,
    task_id BIGINT,
    rut VARCHAR(20),
    FOREIGN KEY (task_id) REFERENCES task(task_id),
    FOREIGN KEY (rut) REFERENCES person(rut)
);


------- Indexes -------

-- Point
CREATE INDEX idx_point_id ON point (point_id);

-- person
CREATE INDEX idx_person_rut ON person (rut);
CREATE INDEX idx_person_email ON person (email);

-- attribute
CREATE INDEX idx_attribute_id ON attribute (attribute_id);

-- Institution
CREATE INDEX idx_institution_id ON institution (institution_id);

-- Emergency
CREATE INDEX idx_emergency_id ON emergency (emergency_id);
CREATE INDEX idx_emergency_status ON emergency (status);

-- Task
CREATE INDEX idx_task_id ON task (task_id);
CREATE INDEX idx_task_status ON task (status);