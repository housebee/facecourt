insert into User (id, username, password) values (1,'john', '123');
insert into User (id, username, password) values (2,'admin', 'admin');

insert into court (id, name, description, category, type) values (1, 'open', 'open court for everyone to join', 0, 0);

insert into artifact (id, title, description, courtId, ownerId) values (1, 'Test Case', 'This is a test case', 1, 1);
