package cl.kibernum.test;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import cl.kibernum.client.PostClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostCrudTest {
    private static PostClient client;
    private static int createdId;
    private static final int USER_ID = 777;

    @BeforeAll
    static void setUp() {
        client = new PostClient();
    }

    @Test
    @DisplayName("GET /posts - lista todos los post")
    void listPosts_ok() {
        Response response = client.listPost();
        JsonPath json = response.jsonPath();
        List<Map<String, Object>> posts = json.getList("$");
        Assertions.assertThat(posts).as(response.getHeader("La lista de posts no debe estar vacía")).isNotEmpty();
        Assertions.assertThat(response.getHeader("Content-Type")).contains("application/json");
    }
}
