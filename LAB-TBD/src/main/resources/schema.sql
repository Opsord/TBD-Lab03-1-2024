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
CREATE TABLE IF NOT EXISTS emergency_audit_trigger (
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

-----------------------------------------------------------------------------------------------------------------

-- User Audit Tables and Triggers

-- Tabla de auditoría para usuarios/personas
CREATE TABLE IF NOT EXISTS user_audit (
    audit_id SERIAL PRIMARY KEY,
    table_name VARCHAR(50) NOT NULL,
    record_id BIGINT,
    rut VARCHAR(20),
    operation VARCHAR(10) NOT NULL, -- INSERT, UPDATE, DELETE
    old_values JSONB,
    new_values JSONB,
    changed_by VARCHAR(100),
    change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_id VARCHAR(100),
    ip_address INET
);

-- Función de auditoría para usuarios
CREATE OR REPLACE FUNCTION user_audit_trigger_function()
    RETURNS TRIGGER AS $$
DECLARE
    audit_row user_audit%ROWTYPE;
    record_id_value BIGINT;
    rut_value VARCHAR(20);
BEGIN
    audit_row.table_name := TG_TABLE_NAME;
    audit_row.operation := TG_OP;
    audit_row.changed_by := current_user;
    audit_row.change_date := CURRENT_TIMESTAMP;
    audit_row.session_id := current_setting('application.session_id', true);
    audit_row.ip_address := inet_client_addr();

    IF TG_OP = 'DELETE' THEN
        -- Extraer el ID y RUT según la tabla
        CASE TG_TABLE_NAME
            WHEN 'person_point' THEN
                record_id_value := OLD.person_point_id;
                rut_value := OLD.rut;
            WHEN 'person_institution' THEN
                record_id_value := OLD.person_institution_id;
                rut_value := OLD.rut;
            WHEN 'person_task' THEN
                record_id_value := OLD.person_task_id;
                rut_value := OLD.rut;
            WHEN 'emergency_person' THEN
                record_id_value := OLD.emergency_person_id;
                rut_value := OLD.rut;
            ELSE
                record_id_value := NULL;
                rut_value := NULL;
        END CASE;

        audit_row.record_id := record_id_value;
        audit_row.rut := rut_value;
        audit_row.old_values := row_to_json(OLD);
        INSERT INTO user_audit (table_name, record_id, rut, operation, old_values, changed_by, change_date, session_id, ip_address)
        VALUES (audit_row.table_name, audit_row.record_id, audit_row.rut, audit_row.operation, audit_row.old_values,
                audit_row.changed_by, audit_row.change_date, audit_row.session_id, audit_row.ip_address);
        RETURN OLD;
    ELSIF TG_OP = 'UPDATE' THEN
        -- Extraer el ID y RUT según la tabla
        CASE TG_TABLE_NAME
            WHEN 'person_point' THEN
                record_id_value := NEW.person_point_id;
                rut_value := NEW.rut;
            WHEN 'person_institution' THEN
                record_id_value := NEW.person_institution_id;
                rut_value := NEW.rut;
            WHEN 'person_task' THEN
                record_id_value := NEW.person_task_id;
                rut_value := NEW.rut;
            WHEN 'emergency_person' THEN
                record_id_value := NEW.emergency_person_id;
                rut_value := NEW.rut;
            ELSE
                record_id_value := NULL;
                rut_value := NULL;
        END CASE;

        audit_row.record_id := record_id_value;
        audit_row.rut := rut_value;
        audit_row.old_values := row_to_json(OLD);
        audit_row.new_values := row_to_json(NEW);
        INSERT INTO user_audit (table_name, record_id, rut, operation, old_values, new_values, changed_by, change_date, session_id, ip_address)
        VALUES (audit_row.table_name, audit_row.record_id, audit_row.rut, audit_row.operation, audit_row.old_values,
                audit_row.new_values, audit_row.changed_by, audit_row.change_date, audit_row.session_id, audit_row.ip_address);
        RETURN NEW;
    ELSIF TG_OP = 'INSERT' THEN
        -- Extraer el ID y RUT según la tabla
        CASE TG_TABLE_NAME
            WHEN 'person_point' THEN
                record_id_value := NEW.person_point_id;
                rut_value := NEW.rut;
            WHEN 'person_institution' THEN
                record_id_value := NEW.person_institution_id;
                rut_value := NEW.rut;
            WHEN 'person_task' THEN
                record_id_value := NEW.person_task_id;
                rut_value := NEW.rut;
            WHEN 'emergency_person' THEN
                record_id_value := NEW.emergency_person_id;
                rut_value := NEW.rut;
            ELSE
                record_id_value := NULL;
                rut_value := NULL;
        END CASE;

        audit_row.record_id := record_id_value;
        audit_row.rut := rut_value;
        audit_row.new_values := row_to_json(NEW);
        INSERT INTO user_audit (table_name, record_id, rut, operation, new_values, changed_by, change_date, session_id, ip_address)
        VALUES (audit_row.table_name, audit_row.record_id, audit_row.rut, audit_row.operation, audit_row.new_values,
                audit_row.changed_by, audit_row.change_date, audit_row.session_id, audit_row.ip_address);
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Triggers de auditoría para tablas de usuarios
CREATE TRIGGER person_point_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE ON person_point
    FOR EACH ROW
EXECUTE FUNCTION user_audit_trigger_function();

CREATE TRIGGER person_institution_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE ON person_institution
    FOR EACH ROW
EXECUTE FUNCTION user_audit_trigger_function();

CREATE TRIGGER person_task_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE ON person_task
    FOR EACH ROW
EXECUTE FUNCTION user_audit_trigger_function();

CREATE TRIGGER emergency_person_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE ON emergency_person
    FOR EACH ROW
EXECUTE FUNCTION user_audit_trigger_function();

-----------------------------------------------------------------------------------------------------------------

-- Query Audit Tables and Triggers

-- Tabla de auditoría para consultas a la base de datos
CREATE TABLE IF NOT EXISTS query_audit (
    query_audit_id SERIAL PRIMARY KEY,
    query_text TEXT NOT NULL,
    query_type VARCHAR(20), -- SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, etc.
    execution_time INTERVAL,
    rows_affected INTEGER,
    database_name VARCHAR(100),
    schema_name VARCHAR(100),
    table_names TEXT[], -- Array de tablas afectadas
    executed_by VARCHAR(100),
    execution_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_id VARCHAR(100),
    ip_address INET,
    application_name VARCHAR(100),
    success BOOLEAN DEFAULT true,
    error_message TEXT
);

-- Función para extraer el tipo de consulta
CREATE OR REPLACE FUNCTION get_query_type(query_text TEXT)
    RETURNS VARCHAR(20) AS $$
BEGIN
    IF query_text ~* '^\s*SELECT' THEN
        RETURN 'SELECT';
    ELSIF query_text ~* '^\s*INSERT' THEN
        RETURN 'INSERT';
    ELSIF query_text ~* '^\s*UPDATE' THEN
        RETURN 'UPDATE';
    ELSIF query_text ~* '^\s*DELETE' THEN
        RETURN 'DELETE';
    ELSIF query_text ~* '^\s*CREATE' THEN
        RETURN 'CREATE';
    ELSIF query_text ~* '^\s*DROP' THEN
        RETURN 'DROP';
    ELSIF query_text ~* '^\s*ALTER' THEN
        RETURN 'ALTER';
    ELSIF query_text ~* '^\s*TRUNCATE' THEN
        RETURN 'TRUNCATE';
    ELSE
        RETURN 'OTHER';
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Función para registrar consultas ejecutadas
CREATE OR REPLACE FUNCTION log_query_execution()
    RETURNS event_trigger AS $$
DECLARE
    query_text TEXT;
    start_time TIMESTAMP;
    end_time TIMESTAMP;
BEGIN
    query_text := current_query();
    start_time := statement_timestamp();
    end_time := clock_timestamp();

    -- Solo auditar consultas DML y DDL importantes, no consultas del sistema
    IF query_text !~* '^\s*(BEGIN|COMMIT|ROLLBACK|SET|SHOW|EXPLAIN)'
       AND query_text !~* 'pg_catalog|information_schema|query_audit|user_audit' THEN

        INSERT INTO query_audit (
            query_text,
            query_type,
            execution_time,
            database_name,
            schema_name,
            executed_by,
            execution_date,
            session_id,
            ip_address,
            application_name
        ) VALUES (
            query_text,
            get_query_type(query_text),
            end_time - start_time,
            current_database(),
            current_schema(),
            current_user,
            CURRENT_TIMESTAMP,
            current_setting('application.session_id', true),
            inet_client_addr(),
            current_setting('application_name', true)
        );
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Event trigger para capturar consultas DDL
DROP EVENT TRIGGER IF EXISTS ddl_command_audit;
CREATE EVENT TRIGGER ddl_command_audit
    ON ddl_command_end
EXECUTE FUNCTION log_query_execution();

-- Función alternativa para auditoría de consultas DML usando hooks
CREATE OR REPLACE FUNCTION audit_dml_query()
    RETURNS TRIGGER AS $$
DECLARE
    query_text TEXT;
    affected_rows INTEGER;
BEGIN
    query_text := current_query();

    -- Obtener número de filas afectadas según la operación
    IF TG_OP = 'INSERT' THEN
        affected_rows := 1;
    ELSIF TG_OP = 'UPDATE' THEN
        affected_rows := 1;
    ELSIF TG_OP = 'DELETE' THEN
        affected_rows := 1;
    END IF;

    -- Registrar la consulta si no es una consulta del sistema de auditoría
    IF query_text !~* 'query_audit|user_audit|emergency_audit' THEN
        INSERT INTO query_audit (
            query_text,
            query_type,
            rows_affected,
            database_name,
            schema_name,
            table_names,
            executed_by,
            execution_date,
            session_id,
            ip_address,
            application_name
        ) VALUES (
            query_text,
            TG_OP,
            affected_rows,
            current_database(),
            current_schema(),
            ARRAY[TG_TABLE_NAME],
            current_user,
            CURRENT_TIMESTAMP,
            current_setting('application.session_id', true),
            inet_client_addr(),
            current_setting('application_name', true)
        );
    END IF;

    IF TG_OP = 'DELETE' THEN
        RETURN OLD;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Aplicar triggers de auditoría DML a las tablas principales
CREATE TRIGGER emergency_query_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE ON emergency
    FOR EACH ROW
EXECUTE FUNCTION audit_dml_query();

CREATE TRIGGER task_query_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE ON task
    FOR EACH ROW
EXECUTE FUNCTION audit_dml_query();

CREATE TRIGGER point_query_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE ON point
    FOR EACH ROW
EXECUTE FUNCTION audit_dml_query();

CREATE TRIGGER institution_query_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE ON institution
    FOR EACH ROW
EXECUTE FUNCTION audit_dml_query();

-----------------------------------------------------------------------------------------------------------------

-- Views for Audit Reports

-- Función auxiliar para comparar valores JSON (debe ir antes de las vistas)
CREATE OR REPLACE FUNCTION jsonb_diff(old_data jsonb, new_data jsonb)
    RETURNS jsonb AS $$
DECLARE
    result jsonb := '{}';
    key text;
BEGIN
    FOR key IN SELECT jsonb_object_keys(new_data)
    LOOP
        IF old_data->key IS DISTINCT FROM new_data->key THEN
            result := result || jsonb_build_object(
                key,
                jsonb_build_object(
                    'old', old_data->key,
                    'new', new_data->key
                )
            );
        END IF;
    END LOOP;
    RETURN result;
END;
$$ LANGUAGE plpgsql;

-- Vista para reportes de auditoría de usuarios
CREATE OR REPLACE VIEW user_audit_report AS
SELECT
    ua.audit_id,
    ua.table_name,
    ua.rut,
    ua.operation,
    ua.changed_by,
    ua.change_date,
    ua.session_id,
    ua.ip_address,
    CASE
        WHEN ua.operation = 'UPDATE' THEN
            jsonb_pretty(jsonb_diff(ua.old_values, ua.new_values))
        ELSE NULL
    END as changes_summary
FROM user_audit ua
ORDER BY ua.change_date DESC;

-- Vista para reportes de auditoría de consultas
CREATE OR REPLACE VIEW query_audit_report AS
SELECT
    qa.query_audit_id,
    qa.query_type,
    CASE
        WHEN length(qa.query_text) > 100 THEN
            substring(qa.query_text from 1 for 100) || '...'
        ELSE qa.query_text
    END as query_summary,
    qa.execution_time,
    qa.rows_affected,
    qa.table_names,
    qa.executed_by,
    qa.execution_date,
    qa.ip_address,
    qa.application_name,
    qa.success
FROM query_audit qa
WHERE qa.query_type IN ('SELECT', 'INSERT', 'UPDATE', 'DELETE')
ORDER BY qa.execution_date DESC;
