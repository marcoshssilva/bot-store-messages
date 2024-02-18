package br.com.marcoshssilva.botstoremessages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@lombok.extern.slf4j.Slf4j
@SpringBootTest
class BotStoreMessagesApplicationTests {

	@DisplayName("must initialize project with Success")
	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> log.info("Project started with success!"));
	}

}
