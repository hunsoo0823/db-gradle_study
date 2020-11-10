-- ID: 1 ~ 4
insert into department(id, name) values (hibernate_sequence.nextval, 'Development');
insert into department(id, name) values (hibernate_sequence.nextval, 'Management');
insert into department(id, name) values (hibernate_sequence.nextval, 'Marketing');
insert into department(id, name) values (hibernate_sequence.nextval, 'Personnel');

-- ID: 5 ~ 15
insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Allison', 1);
insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Lois',    1);
insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Ramon',   1);
insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Derek',   1);

insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Maria',   2);
insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Rosemary',2);
insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Emma',    2);

insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Gabriel', 3);
insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Frances', 3);

insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Elaine',  4);

insert into employee(id, name, department_id) values (hibernate_sequence.nextval, 'Bonnie', NULL);

-- ID: 5 ~ 6
insert into address (id, street, city) values
    (6, 'Hangang-daero', 'Seoul'),
    (5, 'Dongseo-daero', 'Daejon');

-- ID: 16 ~ 19
insert into phone (id, model, employee_id) values
    (hibernate_sequence.nextval, 'Galaxy', 5),
    (hibernate_sequence.nextval, 'iPhone', 5),
    (hibernate_sequence.nextval, 'Galaxy', 6),
    (hibernate_sequence.nextval, 'iPhone', 6);

-- ID: 20 ~ 21
insert into project (id, title) values
    (hibernate_sequence.nextval, 'Java Projects'),
    (hibernate_sequence.nextval, 'Android App Projects');

insert into employee_project (employee_id, project_id) values
    (5, 20),
    (5, 21),
    (6, 20),
    (6, 21);

