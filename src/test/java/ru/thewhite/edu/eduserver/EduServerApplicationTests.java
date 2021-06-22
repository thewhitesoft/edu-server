package ru.thewhite.edu.eduserver;

import com.jupiter.tools.spring.test.postgres.annotation.EnablePostgresTestContainers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnablePostgresTestContainers
class EduServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
