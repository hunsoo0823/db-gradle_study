drop table if exists Track CASCADE 
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table Track (TRACK_ID integer not null, added date, filePath varchar(255), playTime time, title varchar(255), volume smallint not null, primary key (TRACK_ID))
