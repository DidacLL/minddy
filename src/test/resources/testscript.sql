DROP DATABASE IF EXISTS minddy_test;
CREATE DATABASE minddy_test;
USE minddy_test;
create table if not exists projects
                (
                    holder_id     varchar(255) not null,
                    own_id        varchar(255) not null,
                    user_id       varchar(255) not null,
                    creation_date datetime(6)  null,
                    update_date   datetime(6)  null,
                    dead_line     date         null,
                    description   varchar(255) null,
                    name          varchar(60)  not null,
                    state         tinyint      not null,
                    ui_config     varchar(255) null,
                    primary key (holder_id, own_id, user_id),
                    check (`state` between 0 and 5)
                );

create table if not exists notes
(
    id            varchar(36)  not null
        primary key,
    body          varchar(255) not null,
    creation_date datetime(6)  null,
    update_date   datetime(6)  null,
    is_visible    bit          not null,
    name          varchar(36)  null,
    type          tinyint      not null,
    parent_id     varchar(255) not null,
    holder_id     varchar(255) not null,
    user          varchar(255) not null,
    constraint FK7botlab3hxlbm8of5l5glyc0e
        foreign key (parent_id, holder_id, user) references projects (holder_id, own_id, user_id),
    check (`type` between 0 and 3)
);

create index idx_note_by_user_visibility
    on notes (user, is_visible);

create table if not exists project_tracker
(
    id        binary(16)   not null
        primary key,
    auto      bit          not null,
    event     tinyint      null,
    max       int          not null,
    name      varchar(30)  null,
    prompt    varchar(255) null,
    target    tinyint      null,
    value     int          not null,
    parent_id varchar(255) null,
    holder_id varchar(255) null,
    user      varchar(255) null,
    constraint FK9p63wyfuctv0kd3pkctr0wgox
        foreign key (parent_id, holder_id, user) references projects (holder_id, own_id, user_id),
    check (`event` between 0 and 6),
    check (`target` between 0 and 2)
);

create index idx_project_state
    on projects (state);

create table if not exists tags
(
    name          varchar(30)  not null,
    user_id       varchar(255) not null,
    creation_date datetime(6)  null,
    update_date   datetime(6)  null,
    is_heritable  bit          not null,
    is_visible    bit          not null,
    primary key (name, user_id)
);

create table if not exists notes_tags
(
    note_id      varchar(36)  not null,
    tags_name    varchar(30)  not null,
    tags_user_id varchar(255) not null,
    constraint FK8rskn1swps7s7vmdpyhgcsjg9
        foreign key (tags_name, tags_user_id) references tags (name, user_id),
    constraint FKcxrhvlv1dppm49b2snddodsvi
        foreign key (note_id) references notes (id)
);

create table if not exists projects_tags
(
    project_holder_id varchar(255) not null,
    project_own_id    varchar(255) not null,
    project_user_id   varchar(255) not null,
    tags_name         varchar(30)  not null,
    tags_user_id      varchar(255) not null,
    constraint FK8ecv691mldfp8e24peeksw8gh
        foreign key (tags_name, tags_user_id) references tags (name, user_id),
    constraint FKhupgxp8rpbpqb28jv8thaelw4
        foreign key (project_holder_id, project_own_id, project_user_id) references projects (holder_id, own_id, user_id)
);

create index idx_tag_by_visibility
    on tags (is_visible);

create table if not exists tasks
(
    id            varchar(36)  not null
        primary key,
    date          date         null,
    creation_date datetime(6)  null,
    update_date   datetime(6)  null,
    description   varchar(255) null,
    name          varchar(30)  not null,
    priority      tinyint      not null,
    repeat_limit  int          not null,
    repeat_value  int          not null,
    repetition    tinyint      null,
    state         tinyint      not null,
    subtasks      json         null,
    parent_id     varchar(255) null,
    holder_id     varchar(255) null,
    user          varchar(255) null,
    constraint FKhe3ydqf7c5oipcnptgf472hdn
        foreign key (parent_id, holder_id, user) references projects (holder_id, own_id, user_id),
    check (`priority` between 0 and 4),
    check (`repetition` between 0 and 3),
    check (`state` between 0 and 5)
);

create index idx_task_state
    on tasks (state);

create table if not exists user
(
    id            varchar(255) not null
        primary key,
    creation_date datetime(6)  null,
    update_date   datetime(6)  null,
    name          varchar(255) null,
    ui_config     varchar(255) null
);






INSERT INTO user (id, creation_date, update_date, name, ui_config) VALUES ('1234567890', '2023-08-05 08:52:47.154444', '2023-08-05 08:52:47.154444', 'Cetato', '');
INSERT INTO user (id, creation_date, update_date, name, ui_config) VALUES ('1234567891', '2023-08-05 09:18:50.327880', '2023-08-05 09:18:50.327880', 'Fetato', '');

INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('_DELAYED_', '1234567890', '2023-08-05 08:52:47.065444', '2023-08-05 08:52:47.065444', false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('_DELAYED_', '1234567891', '2023-08-05 09:18:50.235879', '2023-08-05 09:18:50.235879', false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('_FAV_', '1234567890', '2023-08-05 08:52:47.094445', '2023-08-05 08:52:47.094445', false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('_FAV_', '1234567891', '2023-08-05 09:18:50.256875', '2023-08-05 09:18:50.256875', false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('_ROOT_', '1234567890', '2023-08-05 08:52:47.099446', '2023-08-05 08:52:47.099446', false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('_ROOT_', '1234567891', '2023-08-05 09:18:50.261881', '2023-08-05 09:18:50.261881', false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('_TASK_', '1234567890', '2023-08-05 08:52:47.149444', '2023-08-05 08:52:47.149444', false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('_TASK_', '1234567891', '2023-08-05 09:18:50.323877', '2023-08-05 09:18:50.323877', false, false);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('PATATA', '1234567891', '2023-08-05 09:18:50.323877', '2023-08-05 09:18:50.323877', true, true);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('FRITA', '1234567891', '2023-08-05 09:18:50.323877', '2023-08-05 09:18:50.323877', false, true);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('PATATA', '1234567890', '2023-08-05 09:18:50.323877', '2023-08-05 09:18:50.323877', true, true);
INSERT INTO tags (name, user_id, creation_date, update_date, is_heritable, is_visible) VALUES ('FRITA', '1234567890', '2023-08-05 09:18:50.323877', '2023-08-05 09:18:50.323877', false, true);



INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('', '00', '1234567890', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', null, '', 'Cetato', 2, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('00', 'FF', '1234567890', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', '2030-02-02', 'My most important X project with no doubt', 'Project X', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('00FF', 'FF', '1234567890', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', '2030-02-02', 'My most important Y branch in X project, thought ', 'Branch Y', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('00FFFF', 'FF', '1234567890', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', '2023-09-02', 'Sure! theres another project inside! how cooool mate', 'Baaaaaa', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('00FF', 'FE', '1234567890', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', '2030-02-02', 'My least important Z branch in X project, fine ', 'Branch Z', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('', '00', '1234567891', '2023-08-05 09:18:50.324876', '2023-08-05 09:18:50.324876', null, '', 'Fetato', 2, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('00', 'FF', '1234567891', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', '2030-02-02', 'My most important X project with no doubt', 'Project X', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('00FF', 'FF', '1234567891', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', '2030-02-02', 'My most important Y branch in X project, thought ', 'Branch Y', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('00FFFF', 'FF', '1234567891', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', '2023-09-02', 'Sure! theres another project inside! how cooool mate', 'Baaaaaa', 0, '');
INSERT INTO projects (holder_id, own_id, user_id, creation_date, update_date, dead_line, description, name, state, ui_config) VALUES ('00FF', 'FE', '1234567891', '2023-08-05 08:52:47.151445', '2023-08-05 08:52:47.151445', '2030-02-02', 'My least important Z branch in X project, fine ', 'Branch Z', 0, '');


INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('', '00', '1234567890', '_ROOT_', '1234567890');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('00FF', 'FE', '1234567890', 'PATATA', '1234567890');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('00FF', 'FF', '1234567890', 'FRITA', '1234567890');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('00FFFF', 'FF', '1234567890', 'PATATA', '1234567890');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('00FFFF', 'FF', '1234567890', 'FRITA', '1234567890');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('', '00', '1234567891', '_ROOT_', '1234567891');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('00FF', 'FE', '1234567891', 'PATATA', '1234567891');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('00FF', 'FF', '1234567891', 'FRITA', '1234567891');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('00FFFF', 'FF', '1234567891', 'PATATA', '1234567891');
INSERT INTO projects_tags (project_holder_id, project_own_id, project_user_id, tags_name, tags_user_id) VALUES ('00FFFF', 'FF', '1234567891', 'FRITA', '1234567891');


insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value, repetition, state, subtasks, parent_id, holder_id, user)
values ('10a6f4fc-b187-40be-9788-84c6f0093747',DATE_ADD(CURDATE(), INTERVAL 1 DAY),curdate(),curdate(),'How Awesome, that`s my first task :`)','First one',2,0,0,0,0,'{}','00FF','FE','1234567890');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value, repetition, state, subtasks, parent_id, holder_id, user)
values ('11a6f4fc-b187-40be-9788-84c6f0093747',CURDATE(),curdate(),curdate(),'How Awesome, that`s my second task :`)','Second one',2,0,0,0,0,'{}','00FF','FF','1234567890');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value, repetition, state, subtasks, parent_id, holder_id, user)
values ('12a6f4fc-b187-40be-9788-84c6f0093747',DATE_ADD(CURDATE(), INTERVAL 2 DAY),curdate(),curdate(),'How Awesome, that`s my third task :`)','Third one',4,0,0,0,0,'{}','00FFFF','FF','1234567890');

insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value, repetition, state, subtasks, parent_id, holder_id, user)
values ('13a6f4fc-b187-40be-9788-84c6f0093747',DATE_ADD(CURDATE(), INTERVAL 1 DAY),curdate(),curdate(),'How Awesome, that`s my first task :`)','First one',2,0,0,0,0,'{}','00FF','FE','1234567891');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value, repetition, state, subtasks, parent_id, holder_id, user)
values ('14a6f4fc-b187-40be-9788-84c6f0093747',CURDATE(),curdate(),curdate(),'How Awesome, that`s my second task :`)','Second one',2,0,0,0,0,'{}','00FF','FF','1234567891');
insert into tasks (id, date, creation_date, update_date, description, name, priority, repeat_limit, repeat_value, repetition, state, subtasks, parent_id, holder_id, user)
values ('15a6f4fc-b187-40be-9788-84c6f0093747',DATE_ADD(CURDATE(), INTERVAL 2 DAY),curdate(),curdate(),'How Awesome, that`s my third task :`)','Third one',4,0,0,0,0,'{}','00FFFF','FF','1234567891');


insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('05a6f4fc-b187-40be-9788-84c6f0093747','This is a note, where you can note that is notable anything you note to notate... ',curdate(),curdate(),true,'Dummy Note',0,'00FF','FF','1234567890');

insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('18a6f4fc-b187-40be-9788-84c6f0093747','This is a task note, where you can note that is notable anything you note to notate... ',curdate(),curdate(),false,'10a6f4fc-b187-40be-9788-84c6f0093747',0,'00FF','FE','1234567890');

insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('28a6f4fc-b187-40be-9788-84c6f0093747','This is a listed note, where you can note that is notable anything you note to notate... ',curdate(),curdate(),true,'Dummy Listed Note 1',0,'00FF','FF','1234567890');

insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('38a6f4fc-b187-40be-9788-84c6f0093747','This is a listed note, where you can note that is notable anything you note to notate... ',curdate(),curdate(),true,'Dummy Listed Note 2',0,'00FF','FF','1234567890');

insert into notes (id, body, creation_date, update_date, is_visible, name, type, parent_id, holder_id, user)
values ('58a6f4fc-b187-40be-9788-84c6f0093747','This is a listed note, where you can note that is notable anything you note to notate... ',curdate(),curdate(),true,'Dummy Listed Note 3',0,'00FFFF','FF','1234567890');




insert into notes_tags (note_id, tags_name, tags_user_id) values ('18a6f4fc-b187-40be-9788-84c6f0093747','_TASK_','1234567890');
insert into notes_tags (note_id, tags_name, tags_user_id) values ('28a6f4fc-b187-40be-9788-84c6f0093747','PATATA','1234567890');
insert into notes_tags (note_id, tags_name, tags_user_id) values ('38a6f4fc-b187-40be-9788-84c6f0093747','PATATA','1234567890');
insert into notes_tags (note_id, tags_name, tags_user_id) values ('58a6f4fc-b187-40be-9788-84c6f0093747','PATATA','1234567890');
