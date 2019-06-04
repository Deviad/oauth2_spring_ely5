package com.simplicity.authserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
@ActiveProfiles("test")
@EnableWebSecurity
@RunWith(SpringRunner.class)
public class SpringdemoApplicationTests {


    @LocalServerPort
    private int port;


    @Test
    @SneakyThrows
    public void AJsonToUrlEncodedAuthenticationFilterShouldReturnAccessTokenWHenCorrectParamsArePassed() {

        Map<String, String> content = Stream.of(new Object[][]{
                {"grant_type", "password"},
                {"client_id", "fooClientIdPassword"},
                {"client_secret", "secret"},
                {"username", "pippo"},
                {"password", "123"},
                {"scope", "read"},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));


        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(content);

//        final ResultActions resultActions = mvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_UTF8).content(json));
//                resultActions
////                .andExpect(status().isOk())
//                .andDo(print());
////                .andExpect(content()
////                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
////                    .andExpect(jsonPath("$[0].name", is("bob")));


        final String access_token = given()
                .port(port)
                .request()
                .contentType(ContentType.JSON)
                .body(json)
                .post("/oauth/token")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value()).extract()
                .body()
                .jsonPath().getString("access_token");
        assertThat(access_token, is(not(isEmptyString())));
    }
}

