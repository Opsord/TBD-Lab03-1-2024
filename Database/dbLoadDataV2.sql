--Poblacion de la tabla Institucion
INSERT INTO institution (name) 
VALUES 
    ('Cruz Roja de Chile'),
    ('Bomberos Municipales'),
    ('Equipo de rescate de desastres naturales');

-- Poblacion de la tabla person_institution
INSERT INTO person_institution (rut, institution_id)
VALUES 
    ('881263612-k', '1'),
    ('948128124-1', '2'),
    ('98482714-4', '3');


--Poblacion de la tabla Emergencia
INSERT INTO emergency (status, title, description)
VALUES 
    (true, 'Incendio forestal en zona rural', 'Se ha reportado un incendio forestal en la zona de la Reserva Nacional. Se necesita asistencia inmediata.'),
    (false, 'Evacuación por fuga de gas', 'Se ha detectado una fuga de gas en un edificio residencial. Se requiere evacuación de los residentes.'),
    (true, 'Inundación en área urbana', 'Las fuertes lluvias han provocado inundaciones en varios sectores de la ciudad. Se necesitan equipos de rescate.'),
    (true, 'Accidente de tráfico múltiple', 'Se ha producido un accidente de tráfico en la autopista principal. Varios vehículos están involucrados.'),
    (false, 'Deslizamiento de tierra en carretera', 'Un deslizamiento de tierra ha bloqueado una carretera importante. Se necesita ayuda para despejar la vía.');

--Poblacion de tabla
-- Población de la tabla Emergencia_Atributo
INSERT INTO emergency_attribute (emergency_id, skill_code, compatibility)
VALUES 
    (1, 'SKL007', true),   -- La emergencia 1 tiene compatibilidad con el atributo 1 (Fuerza física)
    (1, 'SKL001', true),   -- La emergencia 1 tiene compatibilidad con el atributo 2 (Rescate en Terreno)
    (1, 'SKL003', false),  -- La emergencia 1 no tiene compatibilidad con el atributo 3 (Apoyo psicológico)
    (2, 'SKL004', false),  -- La emergencia 2 no tiene compatibilidad con el atributo 4 (Manejo de herramientas de rescate)
    (2, 'SKL001', true),   -- La emergencia 2 tiene compatibilidad con el atributo 5 (Conocimientos en primeros auxilios)
    (3, 'SKL008', true),   -- La emergencia 3 tiene compatibilidad con el atributo 6 (Manejo de equipos de comunicación)
    (3, 'SKL006', true),   -- La emergencia 3 tiene compatibilidad con el atributo 7 (Capacidad para la búsqueda y localización de personas perdidas)
    (4, 'SKL005', true),   -- La emergencia 4 tiene compatibilidad con el atributo 1 (Fuerza física)
    (4, 'SKL003', false),  -- La emergencia 4 no tiene compatibilidad con el atributo 2 (Rescate en Terreno)
    (5, 'SKL002', false), -- La emergencia 5 no tiene compatibilidad con el atributo 6 (Manejo de equipos de comunicación)
    (5, 'SKL001', true);  -- La emergencia 5 tiene compatibilidad con el atributo 7 (Capacidad para la búsqueda y localización de personas perdidas)

-- Población de la tabla Tarea
INSERT INTO task (description, status)
VALUES 
    ('Coordinar y ejecutar la evacuación de los residentes afectados por el incendio forestal.', true),
    ('Gestionar la contención y control de la fuga de gas en el edificio residencial.', false),
    ('Realizar operaciones de rescate para ayudar a las personas atrapadas por la inundación.', true),
    ('Brindar atención médica a los heridos en el accidente de tráfico múltiple.', true),
    ('Organizar y llevar a cabo el despeje de la carretera bloqueada por el deslizamiento de tierra.', false),
    ('Distribuir alimentos y agua potable a los evacuados.', true),
    ('Brindar apoyo emocional y asistencia psicológica a las personas afectadas por la fuga de gas.', false);

-- Población de la tabla Persona_Tarea
INSERT INTO person_task (task_id, rut)
VALUES 
    (1, '123271472-1'),
    (2, '176271472-1'),
    (3, '182473567-4'),
    (4, '2421283874-4'),
    (5, '3641746726-4'),
    (1, '4152351623-5'),
    (2, '42163612342-5'),
    (3, '4412317123-k'),
    (4, '5872873212-4'),
    (5, '61523512412-5');

-- Población de la tabla Tarea_Emergencia
INSERT INTO task_emergency (task_id, emergency_id)
VALUES 
    (1, 2),
    (2, 2),
    (3, 5),
    (4, 4),
    (5, 1),
    (1, 3),
    (2, 1),
    (3, 5),
    (4, 4),
    (5, 3);



--Poblacion de la tabla Punto
INSERT INTO point (longitude, latitude)
VALUES 

-- Sueltas por Chile (principalmente La Serena)

    (-33.51783, -70.68525),
    (-33.00139, -71.42378),
    (-31.85485, -71.15550),
    (-29.867673, -71.241257),
    (-29.91469, -71.24675),
    (-29.921754, -71.212340),
    (-29.838239, -71.270828),
    (-43.551452, -72.336967),

-- Osorno
    (-40.583746, -73.116731),
    (-40.579751, -73.133199),
    (-40.578882, -73.127024),
    (-40.571412, -73.121077),
    (-40.574713, -73.174370),
    (-40.587741, -73.145779),
    (-40.592431, -73.174370),
    (-40.570543, -73.124736),
    (-40.565330, -73.111699),
    (-40.597468, -73.108497),

-- Concepción
    (-36.824599, -73.045838), 
    (-36.805786, -73.050785),
    (-36.796732, -73.090361),
    (-36.791214, -73.050608),
    (-36.823892, -73.021632),
    (-36.781875, -73.099549),
    (-36.823043, -72.994070),
    (-36.817810, -73.050431),
    (-36.826437, -73.049195),
    (-36.829549, -73.050431),
    (-36.779420, -73.034353),
    (-36.833650, -73.056792),
    (-36.833084, -73.052022),
    (-36.831104, -73.047958),
    (-36.782724, -73.034177),

-- Talcahuano
    (-36.730018, -73.116741),
    (-36.720588, -73.121099),
    (-36.723430, -73.113296),
    (-36.722524, -73.113296),
    (-36.712703, -73.134971),
    (-36.721390, -73.125830),
    (-36.722524, -73.119704),
    (-36.723657, -73.110799),
    (-36.722863, -73.113107),
    (-36.719351, -73.121589),
    (-36.712665, -73.120835);


-- Agregar ubicaciones a los usuarios
INSERT INTO person_point (point_id, rut)
VALUES 
    (1, '123271472-1'),
    (2, '176271472-1'),
    (3, '182473567-4'),
    (4, '948128124-1'),
    (5, '81723817246-1'),
    (7, '71264646674-4'),
    (8, '5872873212-4'),
    (9, '876327463-4'),
    (10, '7126476122-4');

-- Agregar ubicaciones a las emergencias
INSERT INTO emergency_point (point_id, emergency_id)
VALUES 
    (6, 1),
    (26, 2),
    (14, 3),
    (28, 4),
    (29, 5);
