-- Point Trigger
CREATE OR REPLACE FUNCTION generate_point_from_lat_lng()
RETURNS TRIGGER AS $$
BEGIN
    NEW.geom := ST_SetSRID(ST_MakePoint(NEW.latitude, NEW.longitude), 4326);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER insert_point_trigger
BEFORE INSERT ON point
FOR EACH ROW
EXECUTE FUNCTION generate_point_from_lat_lng();

CREATE OR REPLACE TRIGGER update_point_trigger
BEFORE UPDATE ON point
FOR EACH ROW
WHEN (NEW.latitude <> OLD.latitude OR NEW.longitude <> OLD.longitude)
EXECUTE FUNCTION generate_point_from_lat_lng();
-----------------------------------------------------------------------------------------------------------------

-- User triggers

-- Audit trigger
CREATE TABLE user_audit_trigger (
    trigger_id SERIAL PRIMARY KEY,
    rut VARCHAR(20),
    name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(255),
    operation TEXT
);

-- Modify user_audit_trigger_function
CREATE OR REPLACE FUNCTION user_audit_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    -- Insert into user_audit_trigger
    INSERT INTO user_audit_trigger (rut, name, email, last_name, password, role, date, operation)
    VALUES (NEW.rut, NEW.name, NEW.last_name, NEW.email, NEW.password, NEW.role, CURRENT_TIMESTAMP, TG_OP);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_audit_trigger
BEFORE INSERT OR UPDATE OR DELETE ON user
FOR EACH ROW
EXECUTE FUNCTION user_audit_trigger_function();

-- Trigger to prevent user from having duplicated attribute
CREATE OR REPLACE FUNCTION prevent_user_duplicate_attribute_func()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM user_attribute WHERE rut = NEW.rut AND attribute_id = NEW.attribute_id) > 0 THEN
        RAISE EXCEPTION 'Un usuario no puede tener atributos duplicados.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_user_duplicate_attribute
BEFORE INSERT ON user_attribute
FOR EACH ROW
EXECUTE FUNCTION prevent_user_duplicate_attribute_func();

-- Trigger to prevent user from having duplicated institution
CREATE OR REPLACE FUNCTION prevent_user_duplicate_institution_func()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM user_institution WHERE rut = NEW.rut AND institution = NEW.institution) > 0 THEN
        RAISE EXCEPTION 'Un usuario no puede tener instituciones duplicadas.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_user_duplicate_institution
BEFORE INSERT ON user_institution
FOR EACH ROW
EXECUTE FUNCTION prevent_user_duplicate_institution_func();
-----------------------------------------------------------------------------------------------------------------

-- Emergency triggers

-- Audit trigger
CREATE TABLE emergency_audit_trigger (
    trigger_id SERIAL PRIMARY KEY,
    emergency_id BIGINT,
    status BOOLEAN,
    title VARCHAR(255),
    description TEXT,
    coordinator VARCHAR(20),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operation TEXT
);

CREATE OR REPLACE FUNCTION emergency_audit_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO emergency_audit_trigger (emergency_id, status, title, description, coordinator, date, operation)
    VALUES (NEW.emergency_id, NEW.status, NEW.title, NEW.description, NEW.coordinator, CURRENT_TIMESTAMP, TG_OP);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER emergency_audit_trigger
BEFORE INSERT OR UPDATE OR DELETE ON emergency
FOR EACH ROW
EXECUTE FUNCTION emergency_audit_trigger_function();

-- Trigger to prevent emergency from having duplicated attribute
CREATE OR REPLACE FUNCTION prevent_emergency_duplicate_attribute_func()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM emergency_attribute WHERE emergency = NEW.emergency AND attribute = NEW.attribute) > 0 THEN
        RAISE EXCEPTION 'Una emergencia no puede tener atributos duplicados.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_emergency_duplicate_attribute
BEFORE INSERT ON emergency_attribute
FOR EACH ROW
EXECUTE FUNCTION prevent_emergency_duplicate_attribute_func();
