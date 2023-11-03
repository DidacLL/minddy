USE minddy_test;

INSERT INTO user (id, creation_date, update_date, name, ui_config)
VALUES ('DEMO_00FF', CURDATE(), CURDATE(), 'Minddy Malatesta', '{}');

INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('_DELAYED_', 'DEMO_00FF', CURDATE(), CURDATE(), false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('_FAV_', 'DEMO_00FF', CURDATE(), CURDATE(), false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('_ROOT_', 'DEMO_00FF', CURDATE(), CURDATE(), false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('_TASK_', 'DEMO_00FF', CURDATE(), CURDATE(), false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('_MAIN_', 'DEMO_00FF', CURDATE(), CURDATE(), false, false);

INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('VIP', 'DEMO_00FF', CURDATE(), CURDATE(), false, true);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('QUICK_LIST', 'DEMO_00FF', CURDATE(), CURDATE(), false, true);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('IDEAS', 'DEMO_00FF', CURDATE(), CURDATE(), false, true);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('CODE', 'DEMO_00FF', CURDATE(), CURDATE(), false, true);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible)
VALUES ('JOB_SEARCH', 'DEMO_00FF', CURDATE(), CURDATE(), false, true);



INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('', '00', 'DEMO_00FF', CURDATE(), CURDATE(), null, '', '_minddy Today', 2, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00', 'FF', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 4 YEAR),
        'Studying Software Engineering Degree at Online University', 'Software Engineering', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00', 'FA', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 4 YEAR),
        'DELETED PROJECT TEST ', 'DISCARDED ', 4, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00', 'F9', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 4 YEAR),
        'Materializing my idea of a project and task manager to keep track of all live appointments ', 'Minddy project', 5, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FF', 'FF', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 4 MONTH),
        'First approach to programming using C language', 'Introduction to Programming', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FFFF', 'FF', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 MONTH),
        'Design a C program that accomplishes the requirements specified on the PR1 ', 'First Practice', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FF', 'FE', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 MONTH),
        'It deals with the logical evaluation of statements and predicates. Some colleagues said that this is harder than expected',
        'Logic', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00', 'FE', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 2 MONTH),
        'React.Js course offered by the Barcelona City Council by it´s IT Academy', 'BCN ITAcademy', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FE', 'FF', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'Html basis... again T.T',
        'HTML', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FE', 'FE', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY),
        'May something  be worse than html?', 'CSS', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FE', 'FD', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 21 DAY), 'Code code code! :)',
        'Basic JS', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FE', 'FC', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 28 DAY), 'Code code code... :S',
        'Advanced JS', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FE', 'FB', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 35 DAY), 'React is cool',
        'Introduction to React', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FE', 'FA', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 45 DAY),
        'React is still cool... i guess', 'React Hooks', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FE', 'F9', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 2 MONTH),
        'What the... am I doing here?  ', 'Final Project', 0, '');

-- Personal Projects
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00', 'FD', 'DEMO_00FF', CURDATE(), CURDATE(), null, '', 'Personal Projects', 2, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state,
                      ui_config)
VALUES ('00FD', 'FF', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 MONTH),
        'Learning to play the guitar', 'Guitar Lessons', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name,
                      state,
                      ui_config)
VALUES ('00FDFF', 'FF', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 WEEK),
        'Learn the chords for the song "Wonderwall"', 'Wonderwall Chords', 0, '');

-- Health Meetings
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description,
                      name,
                      state,
                      ui_config)
VALUES ('00FD', 'FE', 'DEMO_00FF', CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL 2 MONTH),
        'Regular check-ups and appointments with healthcare professionals', 'Health Meetings', 0,
        '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date,
                      dead_line,
                      description,
                      name,
                      state,
                      ui_config)
VALUES ('00FDFE', 'FF', 'DEMO_00FF', CURDATE(), CURDATE(),
        DATE_ADD(CURDATE(), INTERVAL 1 WEEK),
        'Appointment with the dentist for a routine cleaning',
        'Dentist Appointment',
        0,
        '');

-- Job Search
INSERT INTO projects (holder_id, own_id,
                      user_id,
                      creation_date,
                      update_date,
                      dead_line,
                      description,
                      name,
                      state,
                      ui_config)
VALUES ('00FD',
        'FD',
        'DEMO_00FF',
        CURDATE(),
        CURDATE(),
        DATE_ADD(CURDATE(), INTERVAL 3 MONTH),
        'Looking for new job opportunities in the tech industry',
        'Job Search',
        0,
        '');
INSERT INTO projects (holder_id,
                      own_id,
                      user_id,
                      creation_date,
                      update_date,
                      dead_line,
                      description,
                      name,
                      state,
                      ui_config)
VALUES ('00FDFD',
        'FF',
        'DEMO_00FF',
        CURDATE(),
        CURDATE(),
        DATE_ADD(CURDATE(), INTERVAL 1 DAY),
        'Update resume with recent work experience and skills',
        'Update Resume',
        0,
        '');






INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id)
VALUES ('', '00', 'DEMO_00FF', '_ROOT_', 'DEMO_00FF');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id)
VALUES ('00', 'FF', 'DEMO_00FF', 'CODE', 'DEMO_00FF');


insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
values ('11a6f4fc-b187-40be-9788-84c6f0095743', CURDATE(), curdate(), curdate(),
        'How Awesome, that`s my second task :`)', '2ND TASK', 2, 0, 0, 0, 5, '{}', '00FF', 'FF', 'DEMO_00FF');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
values ('11a6f4fc-b187-40be-9788-84c6f0093783', CURDATE(), curdate(), curdate(),
        'How Awesome, that`s my first task :`)', '1ST TASK', 2, 0, 0, 0, 1, '{}', '00FF', 'FE', 'DEMO_00FF');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
values ('11a6f4fc-b187-40be-9788-84c6f0093742', DATE_ADD(CURDATE(), INTERVAL 1 DAY), curdate(), curdate(),
        'How Awesome, that`s my second task :`)', 'Test Tomorrow P:2', 2, 0, 0, 0, 2, '{}', '00FF', 'FE', 'DEMO_00FF');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
values ('11a6f4fc-b187-40be-9788-84c6f0093741', DATE_ADD(CURDATE(), INTERVAL 2 DAY), curdate(), curdate(),
        'How Awesome, that`s my second task :`)', 'Test 2 days P:2', 2, 0, 0, 0, 0, '{}', '00FF', 'FE', 'DEMO_00FF');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
values ('11a6f4fc-b187-40be-9788-84c6f0093740', DATE_SUB(CURDATE(), INTERVAL 1 DAY), curdate(), curdate(),
        'How Awesome, that`s my second task :`)', 'Test -1 day P:2', 2, 0, 0, 0, 0, '{}', '00FF', 'FE', 'DEMO_00FF');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
values ('12a6f4fc-b187-40be-9788-84c6f0093747', DATE_ADD(CURDATE(), INTERVAL 3 DAY), curdate(), curdate(),
        'How Awesome, that`s my third task :`)', 'Test 3DAYS P:4', 4, 0, 0, 0, 0, '{}', '00FFFF', 'FF', 'DEMO_00FF');
INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 1 DAY), CURDATE(), CURDATE(), 'Estudiar los fundamentos de JavaScript',
        'Estudio JS: Día 1', 2, 0, 0, 0, 2, '{}', '00FE', 'FD', 'DEMO_00FF');

INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 2 DAY), CURDATE(), CURDATE(), 'Practicar ejercicios de JavaScript',
        'Práctica JS: Día 2', 2, 0, 0, 0, 2, '{}', '00FE', 'FD', 'DEMO_00FF');

INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 3 DAY), CURDATE(), CURDATE(), 'Repasar los fundamentos de JavaScript',
        'Repaso JS: Día 3', 2, 0, 0, 0, 2, '{}', '00FE', 'FD', 'DEMO_00FF');

INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 4 DAY), CURDATE(), CURDATE(),
        'Practicar ejercicios de JavaScript avanzados', 'Práctica JS Avanzado: Día 4', 2, 0, 0, 0, 2, '{}', '00FE',
        'FD', 'DEMO_00FF');

-- Tareas para el proyecto 'Introduction to Programming'
INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 1 DAY), CURDATE(), CURDATE(), 'Estudiar los fundamentos de C',
        'Estudio C: Día 1', 2, 0, 0, 0, 2, '{}', '00FFFF', 'FF', 'DEMO_00FF');

INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 2 DAY), CURDATE(), CURDATE(), 'Practicar ejercicios de C',
        'Práctica C: Día 2', 2, 0, 0, 0, 2, '{}', '00FFFF', 'FF', 'DEMO_00FF');

-- Tareas para el proyecto 'Logic'
INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 3 DAY), CURDATE(), CURDATE(), 'Estudiar los fundamentos de la lógica',
        'Estudio Lógica: Día 3', 2, 0, 0, 0, 2, '{}', '00FF', 'FE', 'DEMO_00FF');

INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 4 DAY), CURDATE(), CURDATE(), 'Practicar ejercicios de lógica',
        'Práctica Lógica: Día 4', 2, 0, 0, 0, 2, '{}', '00FF', 'FE', 'DEMO_00FF');

-- Tareas para el proyecto 'HTML'
INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 5 DAY), CURDATE(), CURDATE(), 'Estudiar los fundamentos de HTML',
        'Estudio HTML: Día 5', 2, 0, 0, 0, 2, '{}', '00FE', 'FF', 'DEMO_00FF');

INSERT INTO tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value,
                   repetition, state, subtasks, parent_id, holder_id, user)
VALUES (UUID(), DATE_ADD(CURDATE(), INTERVAL 6 DAY), CURDATE(), CURDATE(), 'Practicar ejercicios de HTML',
        'Práctica HTML: Día 6', 2, 0, 0, 0, 2, '{}', '00FE', 'FF', 'DEMO_00FF');

-- Tareas para el proyecto 'CSS'
INSERT INTO tasks (id, date, creation_date, update_date, description, name,priority,repeat_limit,repeat_value,repetition,state,subtasks,parent_id,holder_id, user)
VALUES (UUID(),DATE_ADD(CURDATE(),INTERVAL 7 DAY),CURDATE(),CURDATE(),'Estudiar los fundamentos de CSS','Estudio CSS: Día 7',2, 0,0, 0,2,'{}','00FE','FE','DEMO_00FF');

INSERT INTO tasks (id,date,creation_date,update_date,description,name,priority,repeat_limit,repeat_value,repetition,state,subtasks,parent_id,holder_id,user)
VALUES (UUID(),DATE_ADD(CURDATE(),INTERVAL 8 DAY),CURDATE(),CURDATE(),'Practicar ejercicios de CSS','Práctica CSS: Día 8',2,0,0,0,2,'{}','00FE','FE','DEMO_00FF');

-- Notas para el proyecto 'Introduction to Programming'
INSERT INTO notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
VALUES (UUID(), 'Recuerda repasar los fundamentos de C: https://www.learn-c.org/', CURDATE(), CURDATE(), true, 'Nota: Día 1', 0, '00FFFF', 'FF', 'DEMO_00FF');

INSERT INTO notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
VALUES (UUID(), 'Enlace a ejercicios de C: https://exercism.io/tracks/c', CURDATE(), CURDATE(), true, 'Nota: Día 2', 0, '00FFFF', 'FF', 'DEMO_00FF');

-- Notas para el proyecto 'Logic'
INSERT INTO notes (id, body, creation_date, update_date, is_visible, name,type,parent_id,holder_id,user)
VALUES (UUID(),'Recuerda repasar los fundamentos de la lógica: https://www.coursera.org/courses?query=logic',CURDATE(),CURDATE(),true,'Nota: Día 3',0,'00FF','FE','DEMO_00FF');

INSERT INTO notes (id,body,creation_date,update_date,is_visible,name,type,parent_id,holder_id,user)
VALUES (UUID(),'Enlace a ejercicios de lógica: https://www.khanacademy.org/math/logic-proofs',CURDATE(),CURDATE(),true,'Nota: Día 4',0,'00FF','FE','DEMO_00FF');
-- Notas para el proyecto 'HTML'
INSERT INTO notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
VALUES (UUID(), 'Recuerda repasar los fundamentos de HTML: https://www.w3schools.com/html/', CURDATE(), CURDATE(), true, 'Nota: Día 5', 0, '00FE', 'FF', 'DEMO_00FF');

INSERT INTO notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
VALUES (UUID(), 'Enlace a ejercicios de HTML: https://www.codecademy.com/learn/learn-html', CURDATE(), CURDATE(), true, 'Nota: Día 6', 0, '00FE', 'FF', 'DEMO_00FF');

-- Notas para el proyecto 'CSS'
INSERT INTO notes (id, body, creation_date, update_date, is_visible, name,type,parent_id,holder_id,user)
VALUES (UUID(),'Recuerda repasar los fundamentos de CSS: https://www.w3schools.com/css/',CURDATE(),CURDATE(),true,'Nota: Día 7',0,'00FE','FE','DEMO_00FF');

INSERT INTO notes (id,body,creation_date,update_date,is_visible,name,type,parent_id,holder_id,user)
VALUES (UUID(),'Enlace a ejercicios de CSS: https://www.codecademy.com/learn/learn-css',CURDATE(),CURDATE(),true,'Nota: Día 8',0,'00FE','FE','DEMO_00FF');
-- Notas para el proyecto 'HTML'
INSERT INTO notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
VALUES (UUID(), 'Hablar con Marta sobre el trabajo en equipo en el proyecto HTML', CURDATE(), CURDATE(), true, 'Nota: Día 9', 0, '00FE', 'FF', 'DEMO_00FF');

INSERT INTO notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
VALUES (UUID(), 'Buscar recursos adicionales para mejorar las habilidades de HTML', CURDATE(), CURDATE(), true, 'Nota: Día 10', 0, '00FE', 'FF', 'DEMO_00FF');

-- Notas para el proyecto 'CSS'
INSERT INTO notes (id, body, creation_date, update_date, is_visible, name,type,parent_id,holder_id,user)
VALUES (UUID(),'Programar una reunión con el equipo para discutir los avances en CSS',CURDATE(),CURDATE(),true,'Nota: Día 11',0,'00FE','FE','DEMO_00FF');

INSERT INTO notes (id,body,creation_date,update_date,is_visible,name,type,parent_id,holder_id,user)
VALUES (UUID(),'Revisar los últimos estándares de CSS y actualizar el código si es necesario',CURDATE(),CURDATE(),true,'Nota: Día 12',0,'00FE','FE','DEMO_00FF');

INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('12a6f4fc-b187-40be-9788-84c6f0093747', 'CODE', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('12a6f4fc-b187-40be-9788-84c6f0093747', 'IDEAS', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('12a6f4fc-b187-40be-9788-84c6f0093747', 'VIP', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('11a6f4fc-b187-40be-9788-84c6f0093783', 'CODE', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('11a6f4fc-b187-40be-9788-84c6f0093783', 'IDEAS', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('11a6f4fc-b187-40be-9788-84c6f0093741', 'VIP', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('11a6f4fc-b187-40be-9788-84c6f0093740', 'VIP', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('11a6f4fc-b187-40be-9788-84c6f0093740', 'CODE', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('11a6f4fc-b187-40be-9788-84c6f0093740', 'IDEAS', 'DEMO_00FF');
INSERT INTO tasks_tags(task_id, tags_name, tags_user_id)
VALUES ('11a6f4fc-b187-40be-9788-84c6f0093741', 'JOB_SEARCH', 'DEMO_00FF');

insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('05a6f4fc-b187-40be-9788-84c6f0093747',
        'This is a note, where you can note that is notable anything you note to notate... ', curdate(), curdate(),
        true, 'Dummy Note', 0, '00FF', 'FF', 'DEMO_00FF');
insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('18a6f4fc-b187-40be-9788-84c6f0093747',
        'This is a task note, where you can note that is notable anything you note to notate... ', curdate(), curdate(),
        false, 'WHATEVER', 0, '00FF', 'FE', 'DEMO_00FF');
insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('28a6f4fc-b187-40be-9788-84c6f0093747',
        'This is a listed note, where you can note that is notable anything you note to notate... ', curdate(),
        curdate(), true, 'Dummy Listed Note 1', 0, '00FF', 'FF', 'DEMO_00FF');
insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('38a6f4fc-b187-40be-9788-84c6f0093747',
        'This is a listed note, where you can note that is notable anything you note to notate... ', curdate(),
        curdate(), true, 'Dummy Listed Note 2', 0, '00FF', 'FF', 'DEMO_00FF');
insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('58a6f4fc-b187-40be-9788-84c6f0093747',
        'This is a listed note, where you can note that is notable anything you note to notate... ', curdate(),
        curdate(), true, 'Dummy Listed Note 3', 0, '00FFFF', 'FF', 'DEMO_00FF');
