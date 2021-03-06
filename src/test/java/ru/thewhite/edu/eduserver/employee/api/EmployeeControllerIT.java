package ru.thewhite.edu.eduserver.employee.api;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.jupiter.tools.spring.test.postgres.annotation.EnablePostgresTestContainers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import ru.thewhite.edu.eduserver.employee.api.dto.CreateEmployeeDto;
import ru.thewhite.edu.eduserver.employee.entry.Employee;
import ru.thewhite.edu.eduserver.employee.entry.Gender;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Maxim Seredkin
 */
@EnableWebMvc
@AutoConfigureWebTestClient
@EnablePostgresTestContainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
class EmployeeControllerIT {
    @Autowired
    WebTestClient webTestClient;

    static UUID tokenId = UUID.fromString("00000000-0000-0000-0000-000000000000");

    static CreateEmployeeDto body = CreateEmployeeDto.builder()
                                                     .tokenId(tokenId)
                                                     .fullName("?????????????? ??????")
                                                     .email("text@mail.ru")
                                                     .password("1aS512")
                                                     .phone("+79999999999")
                                                     .profileUrl("https://github.com/tester")
                                                     .gender(Gender.MALE)
                                                     .acceptManual(Boolean.TRUE)
                                                     .salary(50000L)
                                                     .birthday(LocalDate.of(1992, 7, 2))
                                                     .comment("comment")
                                                     .build();

    @Test
    @DataSet(value = "/datasets/employee/api/create.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/datasets/employee/api/create__expected.json")
    void create() throws Exception {
        // Act
        webTestClient.post()
                     .uri("/employee/create")
                     .contentType(MediaType.APPLICATION_JSON)
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(body)
                     .exchange()

                     // Assert
                     .expectStatus().isCreated();
    }

    @ParameterizedTest
    @MethodSource
    void createValidation(CreateEmployeeDto body, Map<String, String> expectedBody) throws Exception {
        // Act
        webTestClient.post()
                     .uri("/employee/create")
                     .contentType(MediaType.APPLICATION_JSON)
                     .accept(MediaType.APPLICATION_JSON)
                     .bodyValue(body)
                     .exchange()

                     // Assert
                     .expectStatus().isBadRequest()
                     .expectBody(new ParameterizedTypeReference<Map<String, String>>() {})
                     .isEqualTo(expectedBody);
    }

    private static Stream<Arguments> createValidation() {
        return Stream.of(Arguments.of(body.toBuilder().fullName(null).build(), mapOf("fullName", "???? ???????????? ???????? ????????????")),
                         Arguments.of(body.toBuilder().fullName("").build(), mapOf("fullName", "???? ???????????? ???????? ????????????")),
                         Arguments.of(body.toBuilder().fullName("   ").build(), mapOf("fullName", "???? ???????????? ???????? ????????????")),
                         Arguments.of(body.toBuilder().tokenId(null).build(), mapOf("tokenId", "???? ???????????? ?????????????????? null")),
                         Arguments.of(body.toBuilder().email(null).build(), mapOf("email", "???? ???????????? ???????? ????????????")),
                         Arguments.of(body.toBuilder().email("").build(), mapOf("email", "???? ???????????? ???????? ????????????")),
                         Arguments.of(body.toBuilder().email(" ").build(), mapOf("email", "???? ???????????? ???????? ????????????")),
                         Arguments.of(body.toBuilder().email("email").build(), mapOf("email", "???????????? ?????????? ???????????? ???????????? ?????????????????????? ??????????")),
                         Arguments.of(body.toBuilder().password(null).build(), mapOf("password", "???? ???????????? ???????? ????????????")),
                         Arguments.of(body.toBuilder().password("").build(), mapOf("password", "???????????? ???????????? ???????????????????? ?? ?????????????????? ???? 6 ???? 12")),
                         Arguments.of(body.toBuilder().password("   ").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[a-z].*$\"")),
                         Arguments.of(body.toBuilder().password("aaaaaa").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[0-9].*$\"")),
                         Arguments.of(body.toBuilder().password("AAAAAA").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[a-z].*$\"")),
                         Arguments.of(body.toBuilder().password("123456").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[A-Z].*$\"")),
                         Arguments.of(body.toBuilder().password("AAAaaa").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[0-9].*$\"")),
                         Arguments.of(body.toBuilder().password("aaaAAA").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[0-9].*$\"")),
                         Arguments.of(body.toBuilder().password("123AAA").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[a-z].*$\"")),
                         Arguments.of(body.toBuilder().password("AAA456").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[a-z].*$\"")),
                         Arguments.of(body.toBuilder().password("123aaa").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[A-Z].*$\"")),
                         Arguments.of(body.toBuilder().password("aaa456").build(), mapOf("password", "???????????? ?????????????????????????????? \"^.*[A-Z].*$\"")),
                         Arguments.of(body.toBuilder().password("aaAA5").build(), mapOf("password", "???????????? ???????????? ???????????????????? ?? ?????????????????? ???? 6 ???? 12")),
                         Arguments.of(body.toBuilder().password("aaAA567891111").build(), mapOf("password", "???????????? ???????????? ???????????????????? ?? ?????????????????? ???? 6 ???? 12")),
                         Arguments.of(body.toBuilder().phone("").build(), mapOf("phone", "???????????? ?????????????????????????????? \"^\\+7[0-9]{10}$\"")),
                         Arguments.of(body.toBuilder().phone(" ").build(), mapOf("phone", "???????????? ?????????????????????????????? \"^\\+7[0-9]{10}$\"")),
                         Arguments.of(body.toBuilder().phone("79999999999").build(), mapOf("phone", "???????????? ?????????????????????????????? \"^\\+7[0-9]{10}$\"")),
                         Arguments.of(body.toBuilder().phone("799999999999").build(), mapOf("phone", "???????????? ?????????????????????????????? \"^\\+7[0-9]{10}$\"")),
                         Arguments.of(body.toBuilder().phone("89999999999").build(), mapOf("phone", "???????????? ?????????????????????????????? \"^\\+7[0-9]{10}$\"")),
                         Arguments.of(body.toBuilder().profileUrl("url").build(), mapOf("profileUrl", "???????????? ?????????????????? ???????????????????? URL")),
                         Arguments.of(body.toBuilder().gender(null).build(), mapOf("gender", "???? ???????????? ?????????????????? null")),
                         Arguments.of(body.toBuilder().acceptManual(null).build(), mapOf("acceptManual", "???? ???????????? ?????????????????? null")),
                         Arguments.of(body.toBuilder().acceptManual(false).build(), mapOf("acceptManual", "???????????? ???????? ?????????? true")),
                         Arguments.of(body.toBuilder().salary(null).build(), mapOf("salary", "???? ???????????? ?????????????????? null")),
                         Arguments.of(body.toBuilder().comment(RandomStringUtils.randomAlphanumeric(201)).build(), mapOf("comment", "???????????? ???????????? ???????????????????? ?? ?????????????????? ???? 0 ???? 200")));
    }

    private static Map<String, String> mapOf(String key, String value) {
        HashMap<String, String> map = new HashMap<>();

        map.put(key, value);

        return map;
    }

    @Test
    @DataSet(value = "/datasets/employee/api/list.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/datasets/employee/api/list.json")
    void list() throws Exception {
        // Act
        webTestClient.get()
                     .uri(uriBuilder -> uriBuilder.path("/employee/list").queryParam("tokenId", tokenId).build())
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()

                     // Assert
                     .expectStatus().isOk()
                     .expectBodyList(Employee.class)
                     .hasSize(2);
    }
}