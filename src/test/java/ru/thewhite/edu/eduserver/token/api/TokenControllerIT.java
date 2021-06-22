package ru.thewhite.edu.eduserver.token.api;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.jupiter.tools.spring.test.postgres.annotation.EnablePostgresTestContainers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.thewhite.edu.eduserver.token.api.dto.CreateTokenDto;
import ru.thewhite.edu.eduserver.token.entity.Token;

/**
 * @author Maxim Seredkin
 */
@EnableWebMvc
@AutoConfigureWebTestClient
@EnablePostgresTestContainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
class TokenControllerIT {
    @Autowired
    WebTestClient webTestClient;

    static CreateTokenDto body = CreateTokenDto.builder()
                                               .owner("Owner")
                                               .build();

    @Test
    @DataSet(value = "/datasets/token/api/create.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/datasets/token/api/create__expected.json")
    void create() throws Exception {
        // Act
        Token result = webTestClient.post()
                                    .uri("/token/create")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .bodyValue(body)
                                    .exchange()

                                    // Assert
                                    .expectStatus().isCreated()
                                    .expectBody(Token.class)
                                    .returnResult().getResponseBody();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getOwner()).isEqualTo(body.getOwner());
    }

    @Test
    @DataSet(value = "/datasets/token/api/list.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/datasets/token/api/list.json")
    void list() throws Exception {
        // Act
        webTestClient.get()
                     .uri("/token/list")
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()

                     // Assert
                     .expectStatus().isOk()
                     .expectBodyList(Token.class)
                     .hasSize(2);
    }
}