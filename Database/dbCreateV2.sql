-- Crea la extension PostGis en la base de datos si no existe
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE point (
    point_id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    geom GEOMETRY(Point, 4326)
);

CREATE TABLE user (
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

CREATE TABLE user_point (
    user_point_id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(20),
    point_id BIGINT,
    FOREIGN KEY (rut) REFERENCES user(rut),
    FOREIGN KEY (point_id) REFERENCES point(point_id)
);

CREATE TABLE attribute (
    attribute_id BIGSERIAL PRIMARY KEY,
    attribute VARCHAR(255) NOT NULL
);

CREATE TABLE user_attribute (
    user_attribute_id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(20),
    attribute_id BIGINT,
    FOREIGN KEY (rut) REFERENCES user(rut),
    FOREIGN KEY (attribute_id) REFERENCES attribute(attribute_id)
);

CREATE TABLE institution (
    institution_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE user_institution (
    user_institution_id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(20),
    institution BIGINT,
    FOREIGN KEY (rut) REFERENCES user(rut),
    FOREIGN KEY (institution) REFERENCES institution(institution_id)
);

CREATE TABLE emergency (
    emergency_id BIGSERIAL PRIMARY KEY,
    status BOOLEAN NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
);

CREATE TABLE emergency_coordinator (
    emergency_coordinator_id BIGSERIAL PRIMARY KEY,
    emergency BIGINT,
    coordinator VARCHAR(20),
    FOREIGN KEY (emergency) REFERENCES emergency(emergency_id),
    FOREIGN KEY (coordinator) REFERENCES user(rut)
);

CREATE TABLE emergency_point (
    emergency_point_id BIGSERIAL PRIMARY KEY,
    emergency BIGINT,
    point BIGINT,
    FOREIGN KEY (emergency) REFERENCES emergency(emergency_id),
    FOREIGN KEY (point) REFERENCES point(point_id)

CREATE TABLE emergency_attribute (
    emergency_attribute_id BIGSERIAL PRIMARY KEY,
    emergency BIGINT,
    attribute BIGINT,
    compatibility BOOLEAN NOT NULL,
    FOREIGN KEY (emergency) REFERENCES emergency(emergency_id),
    FOREIGN KEY (attribute) REFERENCES attribute(attribute_id)
);

CREATE TABLE task (
    task_id BIGSERIAL PRIMARY KEY,
    emergency BIGINT,
    type VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    state BOOLEAN NOT NULL,
    FOREIGN KEY (emergency) REFERENCES emergency(emergency_id)
);

CREATE TABLE task_user (
    task_user_id BIGSERIAL PRIMARY KEY,
    task BIGINT,
    rut VARCHAR(20),
    FOREIGN KEY (task) REFERENCES task(task_id),
    FOREIGN KEY (rut) REFERENCES user(rut)
);


--Indexes

-- User
CREATE INDEX idx_user_rut ON user (rut);
CREATE INDEX idx_user_email ON user (email);

-- attribute
CREATE INDEX idx_attribute_id ON attribute (attribute_id);

-- Institution
CREATE INDEX idx_institution_id ON institution (institution_id);

-- Emergency
CREATE INDEX idx_emergency_id ON emergency (emergency_id);
CREATE INDEX idx_emergency_status ON emergency (status);

-- Task
CREATE INDEX idx_task_emergency_id ON task (emergency);

-- point
CREATE INDEX idx_point_id ON point (point_id);
