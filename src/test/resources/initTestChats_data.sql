--	chat / 4 chats
INSERT INTO chat(chat_type, description, name) 
VALUES ('GROUP_CHAT', 'Group Chat about animals', 'Wild World'), 
	('PERSONAL_CHAT', 'Personal Serhii chat', 'ITEA'), 
	('GROUP_CHAT', 'Group Chat Green Way', 'Green Energy is our future'), 
	('CHANNEL', 'Our Habr Channel', 'Programmers news');

--	users / 6 users
INSERT INTO users_table(age, login, name, password, photo_id) 
VALUES (20, 'karina', 'Karina Pinchuk', 'kp', 1), 
	(99, 'admin', 'Administrator', 'admin', null), 
	(22, 'veron', 'Veronika', 'bugatti', 2), 
	(23, 'KomSoft', 'Volodymyr', '1234', null), 
	(24, 'dmytro', 'Дмитро', 'qwerty', null), 
	(25, 'denis', 'Денис', 'den', null);

--	file_table / 5 files
INSERT INTO file_table(file, file_type) 
VALUES ('karina_photo.jpg', 'image'), 
	('car_bugatti.jpg', 'image'), 
	('user_list.doc', 'msoffice_document'), 
	('google_sheet.xls', 'msoffice_document'), 
	('manual.pdf', 'acrobat_document'), 
	('Chervona_kalyna.mp3', 'sound');

--	messages_table / 30 messages
INSERT INTO messages_table(chat_id, user_id, message_text, file_id, date_time)
--	chat_id=2 / Personal Serhii chat
VALUES (2, 2, 'Serhii has created personal chat', null, '2022-08-20 08:00:00'), 
	(2, 2, 'Administrator invite Volodymyr to chat', null, '2022-08-20 12:15:08'), 
	(2, 4, 'Hello! Do you have any interesting project for me?', null, '2022-08-21 09:59:00'), 
	(2, 2, 'Yes. Please read an attachment', 5, '2022-08-21 14:11:00'), 
--	chat_id=4 / Our Habr Channel
	(4, 2, 'I has opened this chanel for you get new articles', null, '2022-08-20 08:13:00'), 
	(4, 2, 'Please read -Spring Boot веб приложение с нуля-', null, '2022-08-22 10:13:25'), 
	(4, 2, 'Please read -Quick Guide on Loading Initial Data with Spring Boot-', null, '2022-08-23 10:43:25'), 
	(4, 2, 'Please read -Односторонние и двусторонние отношения в Hibernate-', null, '2022-08-21 11:20:25'), 
	(4, 2, 'Please read -Настройка CORS в Spring Security-', null, '2022-08-21 09:11:30'), 
--	chat_id=1 / Group Chat about animals
	(1, 1, 'I do not know Karina likes cats or not. But she created this chat.', null, '2022-08-19 08:13:00'), 
	(1, 3, 'Veronika joined', null, '2022-08-19 15:32:00'), 
	(1, 4, 'Volodymyr joined', null, '2022-08-19 16:13:00'), 
	(1, 5, 'Dmytro joined', null, '2022-08-19 16:24:00'), 
	(1, 6, 'Denis joined', null, '2022-08-19 17:59:59'), 
	(1, 5, 'Do you like cheetah?', null, '2022-08-20 07:15:00'), 
	(1, 3, 'Yes. They are big cats', null, '2022-08-20 11:13:00'), 
	(1, 6, 'Save forests!', null, '2022-08-20 12:24:00'), 
	(1, 4, 'Please write here forest square', 4, '2022-08-20 17:59:59'), 

--	chat_id=3 / Group Chat Green Way
	(3, 6, 'I hate cats. I believe only clean green energy.', null, '2022-08-22 08:13:00'), 
	(3, 3, 'Bla-bla-bla.', null, '2022-08-22 09:24:00'), 
	(3, 2, 'I am an observer.', null, '2022-08-22 10:13:00'), 
	(3, 6, 'I hate cats. I believe only clean green energy.', null, '2022-08-22 08:13:00'), 
	(3, 1, 'Why you did not invite me here?!', null, '2022-08-27 00:01:00');
