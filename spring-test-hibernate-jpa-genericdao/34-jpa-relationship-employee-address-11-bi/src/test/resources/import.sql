-- ID: 1 ~ 4
insert into department(id, name) values (hibernate_sequence.nextval, 'Development');
insert into department(id, name) values (hibernate_sequence.nextval, 'Management');
insert into department(id, name) values (hibernate_sequence.nextval, 'Marketing');
insert into department(id, name) values (hibernate_sequence.nextval, 'Personnel');

-- ID: 5 ~ 6
insert into address (id, street, city) values
    (hibernate_sequence.nextval, 'Hangang-daero', 'Seoul'),
    (hibernate_sequence.nextval, 'Dongseo-daero', 'Daejon');

-- ID: 7 ~ 17
insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Allison', 1, 6);
insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Lois',    1, 5);
insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Ramon',   1, NULL);
insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Derek',   1, NULL);

insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Maria',   2, NULL);
insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Rosemary',2, NULL);
insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Emma',    2, NULL);

insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Gabriel', 3, NULL);
insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Frances', 3, NULL);

insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Elaine',  4, NULL);

insert into employee(id, name, department_id, address_id) values (hibernate_sequence.nextval, 'Bonnie', NULL, NULL);

-- ID: 18 ~ 21
insert into phone (id, model, employee_id) values
    (hibernate_sequence.nextval, 'Galaxy', 7),
    (hibernate_sequence.nextval, 'iPhone', 7),
    (hibernate_sequence.nextval, 'Galaxy', 8),
    (hibernate_sequence.nextval, 'iPhone', 8);

-- ID: 22 ~ 23
insert into project (id, title) values
    (hibernate_sequence.nextval, 'Java Projects'),
    (hibernate_sequence.nextval, 'Android App Projects');

insert into employee_project (employee_id, project_id) values
    (7, 22),
    (7, 23),
    (8, 22),
    (8, 23);

