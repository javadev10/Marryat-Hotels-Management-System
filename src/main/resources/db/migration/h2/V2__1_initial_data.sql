alter table booking add constraint dates_check check (end_date >= start_date);

insert into room(id, room_number) values(1, 23);
insert into room(id, room_number) values(2, 27);
insert into room(id, room_number) values(3, 28);
insert into room(id, room_number) values(4, 29);
insert into room(id, room_number) values(5, 30);
insert into room(id, room_number) values(6, 31);
insert into room(id, room_number) values(7, 32);
insert into room(id, room_number) values(8, 33);
insert into room(id, room_number) values(9, 34);
insert into room(id, room_number) values(10, 35);

insert into booking(start_date, end_date, room_id) values ('2020-01-01', '2020-01-05', 1);
insert into booking(start_date, end_date, room_id) values ('2020-01-06', '2020-02-08', 1);
insert into booking(start_date, end_date, room_id) values ('2020-01-09', '2020-02-11', 1);
insert into booking(start_date, end_date, room_id) values ('2020-01-01', '2020-01-05', 2);
insert into booking(start_date, end_date, room_id) values ('2020-01-01', '2020-01-05', 3);
insert into booking(start_date, end_date, room_id) values ('2020-01-01', '2020-01-05', 4);
insert into booking(start_date, end_date, room_id) values ('2020-01-01', '2020-02-01', 5);
