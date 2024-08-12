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

-- Emergency triggers

-- Audit trigger
CREATE TABLE emergency_audit_trigger (
    trigger_id SERIAL PRIMARY KEY,
    emergency_id BIGINT,
    status BOOLEAN,
    title VARCHAR(255),
    description TEXT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operation TEXT
);

CREATE OR REPLACE FUNCTION emergency_audit_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO emergency_audit_trigger (emergency_id, status, title, description, date, operation)
    VALUES (NEW.emergency_id, NEW.status, NEW.title, NEW.description, CURRENT_TIMESTAMP, TG_OP);
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
    IF (SELECT COUNT(*) FROM emergency_attribute WHERE emergency_id = NEW.emergency_id AND skill_code = NEW.skill_code) > 0 THEN
        RAISE EXCEPTION 'Una emergencia no puede tener atributos duplicados.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_emergency_duplicate_attribute
BEFORE INSERT ON emergency_attribute
FOR EACH ROW
EXECUTE FUNCTION prevent_emergency_duplicate_attribute_func();
