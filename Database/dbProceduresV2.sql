-- Reporte de cantidad de operaciones de inserción, actualización y eliminación de usuarios
-- Genera el reporte a partir del voluntario y rut que que tenga mas de cada tipo de operation.


CREATE OR REPLACE PROCEDURE get_person_operations_report()
AS $$

DECLARE
    person_volunteer RECORD;
    person_rut RECORD;
BEGIN
    -- Get the volunteer person with the most insert operations
    RAISE NOTICE 'Voluntario con más operationes de inserción:';
    FOR person_volunteer IN
        SELECT rut, name, last_name, COUNT(*)
        FROM person_auditTrigger
        WHERE operation = 'INSERT'
        AND role = 'VOLUNTEER'
        GROUP BY rut
        ORDER BY COUNT(*) DESC
        LIMIT 1
    LOOP
        RAISE NOTICE 'Rut: %, Nombre: %, Apellido: %, Cantidad: %', person_volunteer.rut, person_volunteer.name, person_volunteer.last_name, person_volunteer.count;
    END LOOP;

    -- Get the volunteer person with the most update operations
    RAISE NOTICE 'Voluntario con más operationes de actualización:';
    FOR person_volunteer IN
        SELECT rut, name, last_name, COUNT(*)
        FROM person_auditTrigger
        WHERE operation = 'UPDATE'
        AND role = 'VOLUNTEER'
        GROUP BY rut
        ORDER BY COUNT(*) DESC
        LIMIT 1
    LOOP
        RAISE NOTICE 'Rut: %, Nombre: %, Apellido: %, Cantidad: %', person_volunteer.rut, person_volunteer.name, person_volunteer.last_name, person_volunteer.count;
    END LOOP;

    -- Get the volunteer person with the most delete operations
    RAISE NOTICE 'Voluntario con más operationes de eliminación:';
    FOR person_volunteer IN
        SELECT rut, name, last_name, COUNT(*)
        FROM person_auditTrigger
        WHERE operation = 'DELETE'
        AND role = 'VOLUNTEER'
        GROUP BY rut
        ORDER BY COUNT(*) DESC
        LIMIT 1
    LOOP
        RAISE NOTICE 'Rut: %, Nombre: %, Apellido: %, Cantidad: %', person_volunteer.rut, person_volunteer.name, person_volunteer.last_name, person_volunteer.count;
    END LOOP;

    -- Get the rut person with the most insert operations
    RAISE NOTICE 'rut con más operationes de inserción:';
    FOR person_rut IN
        SELECT rut, name, last_name, COUNT(*)
        FROM person_auditTrigger
        WHERE operation = 'INSERT'
        AND role = 'rut'
        GROUP BY rut
        ORDER BY COUNT(*) DESC
        LIMIT 1
    LOOP
        RAISE NOTICE 'Rut: %, Nombre: %, Apellido: %, Cantidad: %', person_rut.rut, person_rut.name, person_rut.last_name, person_rut.count;
    END LOOP;

    -- Get the rut person with the most update operations
    RAISE NOTICE 'rut con más operationes de actualización:';
    FOR person_rut IN
        SELECT rut, name, last_name, COUNT(*)
        FROM person_auditTrigger
        WHERE operation = 'UPDATE'
        AND role = 'rut'
        GROUP BY rut
        ORDER BY COUNT(*) DESC
        LIMIT 1
    LOOP
        RAISE NOTICE 'Rut: %, Nombre: %, Apellido: %, Cantidad: %', person_rut.rut, person_rut.name, person_rut.last_name, person_rut.count;
    END LOOP;

    -- Get the rut person with the most delete operations
    RAISE NOTICE 'rut con más operationes de eliminación:';
    FOR person_rut IN
        SELECT rut, name, last_name, COUNT(*)
        FROM person_auditTrigger
        WHERE operation = 'DELETE'
        AND role = 'rut'
        GROUP BY rut
        ORDER BY COUNT(*) DESC
        LIMIT 1
    LOOP
        RAISE NOTICE 'Rut: %, Nombre: %, Apellido: %, Cantidad: %', person_rut.rut, person_rut.name, person_rut.last_name, person_rut.count;
    END LOOP;

END;
$$ LANGUAGE plpgsql;

-- Para llamarlo dentro directamente en la base de dato ejecute: CALL get_person_operations_report();


-- Procedimiento almacenado para el reporte de cantidad e actulaizaciones, inserciones y eliminaciones de emergencia 
-- Genera el reporte a partir del coordinador que que tenga mas de cada tipo de operation.
CREATE OR REPLACE PROCEDURE get_rutperson_operations()
AS $$

DECLARE
    rut_maxCount_insert INT;
    rut_maxCount_insert_id VARCHAR(20);
    rut_maxCount_update INT;
    rut_maxCount_update_id VARCHAR(20);
    rut_maxCount_delete INT;
    rut_maxCount_delete_id VARCHAR(20);
BEGIN
    -- Contar operationes de inserción por rutes
    SELECT COUNT(*), rut INTO rut_maxCount_insert, rut_maxCount_insert_id
    FROM Emergency_auditTrigger
    WHERE operation = 'INSERT'
    GROUP BY rut
    ORDER BY COUNT(*) DESC
    LIMIT 1;

   -- Count update operations by ruts
    SELECT COUNT(*), rut INTO rut_maxCount_update, rut_maxCount_update_id
    FROM Emergency_auditTrigger
    WHERE operation = 'UPDATE'
    GROUP BY rut
    ORDER BY COUNT(*) DESC
    LIMIT 1;

    -- Contar operationes de eliminación por rutes
    SELECT COUNT(*), rut INTO rut_maxCount_delete, rut_maxCount_delete_id
    FROM Emergency_auditTrigger
    WHERE operation = 'DELETE'
    GROUP BY rut
    ORDER BY COUNT(*) DESC
    LIMIT 1;

    -- Mostrar resultados
    RAISE NOTICE 'Coordinador con más operationes de inserción de emergencias: ID = %, Cantidad = %', rut_maxCount_insert_id, rut_maxCount_insert;
    RAISE NOTICE 'Coordinador con más operationes de actualización de emergencias: ID = %, Cantidad = %', rut_maxCount_update_id, rut_maxCount_update;
    RAISE NOTICE 'Coordinador con más operationes de eliminación de emergencias: ID = %, Cantidad = %', rut_maxCount_delete_id, rut_maxCount_delete;

END;
$$ LANGUAGE plpgsql;


--Para llamar al procedimiento: CALL get_rutperson_operations();

