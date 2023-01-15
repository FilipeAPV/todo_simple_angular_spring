INSERT INTO role (name) VALUES ('user');
INSERT INTO role (name) VALUES ('admin');

INSERT INTO user (email, first_name, last_name, password) VALUES ('ze@gmail.com','Ze','Martins','$2a$10$d9ounDR6SQgA.5Jtrj3gP.eFU3PL8pr4kMXaT.V6MZDw8AofvHEiO');
INSERT INTO user (email, first_name, last_name, password) VALUES ('jane@gmail.com','Jane','Doe','$2a$10$d9ounDR6SQgA.5Jtrj3gP.eFU3PL8pr4kMXaT.V6MZDw8AofvHEiO');
INSERT INTO user (email, first_name, last_name, password) VALUES ('mike@gmail.com','Mike','Smith','$2a$10$d9ounDR6SQgA.5Jtrj3gP.eFU3PL8pr4kMXaT.V6MZDw8AofvHEiO');
INSERT INTO user (email, first_name, last_name, password) VALUES ('paul@gmail.com','Paul','Johnson','$2a$10$d9ounDR6SQgA.5Jtrj3gP.eFU3PL8pr4kMXaT.V6MZDw8AofvHEiO');
INSERT INTO user (email, first_name, last_name, password) VALUES ('sara@gmail.com','Sara','Williams','$2a$10$d9ounDR6SQgA.5Jtrj3gP.eFU3PL8pr4kMXaT.V6MZDw8AofvHEiO');
INSERT INTO user (email, first_name, last_name, password) VALUES ('kim@gmail.com','Kim','Jones','$2a$10$d9ounDR6SQgA.5Jtrj3gP.eFU3PL8pr4kMXaT.V6MZDw8AofvHEiO');

INSERT INTO users_roles(user_id, role_id) VALUES (1,1);
INSERT INTO users_roles(user_id, role_id) VALUES (1,2);
INSERT INTO users_roles(user_id, role_id) VALUES (2,1);
INSERT INTO users_roles(user_id, role_id) VALUES (3,1);
INSERT INTO users_roles(user_id, role_id) VALUES (4,1);
INSERT INTO users_roles(user_id, role_id) VALUES (5,1);
INSERT INTO users_roles(user_id, role_id) VALUES (6,1);

INSERT INTO task (title, text, done, user_id) VALUES ('Watch a documentary', 'Doc. on black holes', false, 1);
INSERT INTO task (title, text, done, user_id) VALUES ('Read a book', '"The Alchemist" by Paulo Coelho', true, 2);
INSERT INTO task (title, text, done, user_id) VALUES ('Go for a run', 'Run for 30 minutes', false, 3);
INSERT INTO task (title, text, done, user_id) VALUES ('Practice yoga', 'Yoga session at home', true, 4);
INSERT INTO task (title, text, done, user_id) VALUES ('Prepare dinner', 'Cook a healthy meal', false, 5);
INSERT INTO task (title, text, done, user_id) VALUES ('Organize closet', 'Clean and organize clothes', true, 1);
