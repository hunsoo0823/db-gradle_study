insert into department(id, name) values (hibernate_sequence.nextval, 'Development');
insert into department(id, name) values (hibernate_sequence.nextval, 'Management');
insert into department(id, name) values (hibernate_sequence.nextval, 'Marketing');
insert into department(id, name) values (hibernate_sequence.nextval, 'Personnel');

/*
current_date or current_date()
current_time
current_timestamp
*/

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

