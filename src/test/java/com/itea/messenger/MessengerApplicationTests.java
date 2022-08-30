package com.itea.messenger;

import com.itea.messenger.repository.MessagesRepository;
import com.itea.messenger.service.DefaultMessagesService;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@PropertySource("h2memory.properties")
@Log
@Sql("/initMessages.sql")
public class MessengerApplicationTests {
	private enum DbType { H2, POSTGRESQL_TEST_CONTAINER}
//	@Container
	public static ITEAPostgreSQLContainer iteaPostgreSQLContainer;
	@Autowired
	MessagesRepository messagesRepository;
	@Autowired
	DefaultMessagesService defaultMessagesService;

	@BeforeAll
	public static void init() {
		DbType db = DbType.H2;
//		DbType db = DbType.POSTGRESQL_TEST_CONTAINER;
		log.info("Initialising " + db + " test database...");
		switch (db) {
			case H2 -> initH2();
			case POSTGRESQL_TEST_CONTAINER -> initPostgreSQL();
		}
		log.info("Initialising " + db + " test database completed. Driver-class-name=" + System.getProperty("DRIVER_CLASS_NAME") + ", DB_ULR=" + System.getProperty("DB_URL") + ", DB_USERNAME=" + System.getProperty("DB_USERNAME") + ", DB_PASSWORD=" + System.getProperty("DB_PASSWORD"));
	}

	public static void initH2() {
		final String DB_NAME = "messenger_test";
		System.setProperty("DRIVER_CLASS_NAME", "org.h2.Driver");
		System.setProperty("DB_URL", "jdbc:h2:mem:" + DB_NAME + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
		System.setProperty("DB_USERNAME", "root");
		System.setProperty("DB_PASSWORD", "root");
	}

	public static void initPostgreSQL() {
		iteaPostgreSQLContainer = ITEAPostgreSQLContainer.getInstance();
		iteaPostgreSQLContainer.start();
	}

	@Test
//	@Transactional
	public void getMessagesCount() {
		long count = messagesRepository.count();
//		MessagesDto messagesDto = defaultMessagesService.getMessageById(2L);
		log.info("Total messages = " + count);
		Assert.assertEquals(23, count);
	}

	@Test
	public void testMessagesCountByChatId() {
		Long chatId = 2L;
		log.info("messagesCountByChatId called");
		long count = defaultMessagesService.getAllMessagesByChatId(chatId).size();
		log.info("Count messages by ChatId=" + chatId + " is: " + count);
		Assert.assertEquals(4, count);
	}

}
