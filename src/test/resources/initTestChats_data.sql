--	chat / 4 chats
INSERT INTO chats(chat_type, description, name) 
VALUES ('GROUP_CHAT', 'Group Chat about animals', 'Wild World'), 
	('PERSONAL_CHAT', 'Personal Serhii chat', 'ITEA'), 
	('GROUP_CHAT', 'Group Chat Green Way', 'Green Energy is our future'), 
	('CHANNEL', 'Our Habr Channel', 'Programmers news');

--	users / 6 users
INSERT INTO users(age, login, name, password, photo_id) 
VALUES (20, 'karina', 'Karina Pinchuk', 'kp', 1), 
	(99, 'admin', 'Administrator', 'admin', null), 
	(22, 'veron', 'Veronika', 'bugatti', 2), 
	(23, 'KomSoft', 'Volodymyr', '1234', null), 
	(24, 'dmytro', 'Дмитро', 'qwerty', null), 
	(25, 'denis', 'Денис', 'den', null);

--	chat members
INSERT INTO chats_users(chat_id, user_id, join_date, view_date)
--	chatId=1, users 1, 3, 4, 5, 6
-- VALUES (1, 1), (1, 3), (1, 4), (1, 5), (1, 6),
VALUES 	(1, 1, '2022-08-19 08:12:00', '2022-08-19 08:13:00'), (1, 3, '2022-08-19 15:31:00', '2022-08-20 11:13:00'), 
	(1, 4, '2022-08-19 16:12:00', '2022-08-20 17:59:59'), (1, 5, '2022-08-19 16:23:00', '2022-08-20 07:15:00'), 
	(1, 6, '2022-08-19 17:58:59', '2022-08-20 12:24:00'),
--	chatId=2, users 2, 4
	(2, 2, '2022-08-20 07:59:00', '2022-08-21 14:11:00'), (2, 4, '2022-08-21 09:58:00', '2022-08-21 09:59:00'),
--	chatId=3, users 6, 3, 2, 1
	(3, 6, '2022-08-22 08:12:00', '2022-08-22 08:13:00'), (3, 3, '2022-08-22 09:23:00', '2022-08-22 09:24:00'), 
	(3, 2, '2022-08-22 10:12:00', '2022-08-22 10:13:00'), (3, 1, '2022-08-27 00:00:30', '2022-08-27 00:01:00'),
--	chatId=4, users 2, 1, 3, 4, 5, 6
	(4, 2, '2022-08-20 08:00:00', '2022-08-20 08:01:00'), (4, 1, '2022-08-20 08:01:00', '2022-08-24 00:00:00'), 
	(4, 3, '2022-08-20 08:02:00', '2022-08-23 08:01:00'), (4, 4, '2022-08-20 08:03:00', null), 
	(4, 5, '2022-08-20 08:04:00', null), (4, 6, '2022-08-20 08:05:00', '2022-08-25 12:10:30'); 

--	file_table / 5 files
INSERT INTO files(file_name, file_type) 
VALUES ('karina_photo.jpg', 'IMAGE'), 
	('car_bugatti.jpg', 'IMAGE'),                                                       
	('user_list.doc', 'MSOFFICE_DOCUMENT'), 
	('google_sheet.xls', 'MSOFFICE_DOCUMENT'), 
	('manual.pdf', 'ACROBAT_DOCUMENT'), 
	('Chervona_kalyna.mp3', 'SOUND');

--	messages_table / 30 messages
INSERT INTO messages(chat_id, user_id, message_text, file_id, date_time)
--	chat_id=2 / Personal Serhii chat
VALUES (2, 2, 'Serhii has created personal chat', null, '2022-08-20 08:00:00'), 
	(2, 2, 'Administrator invite Volodymyr to chat', null, '2022-08-20 12:15:08'), 
	(2, 4, 'Hello! Do you have any interesting project for me?', null, '2022-08-21 09:59:00'), 
	(2, 2, 'Yes. Please read an attachment', 5, '2022-08-21 14:11:00'), 
--	chat_id=4 / Our Habr Channel
	(4, 2, 'I has opened this chanel for you get new articles', null, '2022-08-20 08:13:00'), 
	(4, 2, 'Please read -Spring Boot веб приложение с нуля-', null, '2022-08-22 10:13:25'), 
	(4, 2, 'Please read -Quick Guide on Loading Initial Data with Spring Boot-', null, '2022-08-23 10:43:25'), 
	(4, 2, 'Please read -Односторонние и двусторонние отношения в Hibernate-', null, '2022-08-24 11:20:25'), 
	(4, 2, 'Please read -Настройка CORS в Spring Security-', null, '2022-08-25 09:11:30'), 
--	chat_id=1 / Group Chat about animals
	(1, 1, 'I do not know Karina likes cats or not. But she created this chat.', null, '2022-08-19 08:13:00'), 
	(1, 3, 'Veronika joined', null, '2022-08-19 15:32:00'), 
	(1, 4, 'Volodymyr joined', null, '2022-08-19 16:13:00'), 
	(1, 5, 'Dmytro joined', null, '2022-08-19 16:24:00'), 
	(1, 6, 'Denis joined', null, '2022-08-19 17:59:59'), 
	(1, 5, 'Do you like cheetah?', null, '2022-08-20 07:15:00'), 
	(1, 3, 'Yes. They are big cats', null, '2022-08-20 11:13:00'), 
	(1, 6, 'Save forests!', null, '2022-08-20 12:24:00'), 
	(1, 4, 'Please write forest square here', 4, '2022-08-20 17:59:59'), 

--	chat_id=3 / Group Chat Green Way
	(3, 6, 'I hate cats. I believe only clean green energy.', null, '2022-08-22 08:13:00'), 
	(3, 3, 'Bla-bla-bla.', null, '2022-08-22 09:24:00'), 
	(3, 2, 'I am an observer.', null, '2022-08-22 10:13:00'), 
	(3, 6, 'I hate cats. I believe only clean green energy.', null, '2022-08-22 08:13:00'), 
	(3, 1, 'Why you did not invite me here?!', null, '2022-08-27 00:01:00');

--	status_links / for each message - statuses for each user in this chat
INSERT INTO status_links(message_id, user_id, status_type)
--	chat_id=1 / Group Chat about animals
VALUES (10, 1, 'READ'), 
	(11, 1, 'READ'), (11, 3, 'READ'),
	(12, 1, 'READ'), (12, 3, 'READ'), (12, 4, 'DELETED'),
	(13, 1, 'READ'), (13, 3, 'READ'), (13, 4, 'DELETED'), (13, 5, 'READ'),
	(14, 1, 'READ'), (14, 3, 'READ'), (14, 4, 'READ'), (14, 5, 'READ'), (14, 6, 'READ'),
	(15, 1, 'DELIVERED'), (15, 3, 'READ'), (15, 4, 'READ'), (15, 5, 'READ'), (15, 6, 'READ'),
	(16, 1, 'DELIVERED'), (16, 3, 'EDITED'), (16, 4, 'EDITED'), (16, 5, 'DELETED'), (16, 6, 'EDITED'),
	(17, 1, 'DELIVERED'), (17, 3, 'DELIVERED'), (17, 4, 'READ'), (17, 5, 'DELIVERED'), (17, 6, 'READ'),
	(18, 1, 'DELIVERED'), (18, 3, 'DELIVERED'), (18, 4, 'READ'), (18, 5, 'DELIVERED'), (18, 6, 'DELIVERED');
