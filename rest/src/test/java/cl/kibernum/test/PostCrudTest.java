package cl.kibernum.test;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import cl.kibernum.client.PostClient;
import cl.kibernum.model.Post;
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
    @Order(1)
    @DisplayName("GET /posts - lista todos los post")
    void listPosts_ok() {
        Response response = client.listPost();
        JsonPath json = response.jsonPath();
        List<Map<String, Object>> posts = json.getList("$");
        Assertions.assertThat(posts)
          .as("La lista de posts no debe estar vacía")
          .isNotEmpty();
        Assertions.assertThat(response.getHeader("Content-Type")).contains("application/json");
    }

    @Test
    @Order(2)
    @DisplayName("GET /posts/{id} - Obtiene un post existente")
    void getPostById() {
        Response response = client.getPost(1);
        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getInt("id")).isEqualTo(1);
        Assertions.assertThat(json.getString("title")).isNotBlank();
    }

    @Test
    @Order(3)
    @DisplayName("POST /posts - Crear un post")
    void createPost_created() {
        String title = "Aprendiendo testing";
        String body = "Hoy toca aprender rest assured";
        Post payload = new Post(USER_ID, title, body);

        Response response = client.createPost(payload);
        JsonPath json = response.jsonPath();
        createdId = json.getInt("id");
        Assertions.assertThat(createdId).isNotNull();
        Assertions.assertThat(json.getString("title")).isEqualTo(title);
        Assertions.assertThat(json.getString("body")).isEqualTo(body);
        Assertions.assertThat(json.getInt("userId")).isEqualTo(USER_ID);
    }

    @Test
    @Order(4)
    @DisplayName("PUT /posts - Crear un post")
    void updatePost_ok() {
        int idToUpdate = 1;
        String title = "Aprendiendo testing CON JAVA";
        String body = "Hoy toca aprender rest assured";
        Post updatePost = new Post(USER_ID, title, body);

        Response response = client.updatedPost(idToUpdate, updatePost);
        JsonPath json = response.jsonPath();
       
        Assertions.assertThat(json.getString("title")).isEqualTo(title);
        Assertions.assertThat(json.getString("body")).isEqualTo(body);
        Assertions.assertThat(json.getInt("userId")).isEqualTo(USER_ID);
    }

    @Test
    @Order(5)
    @DisplayName("PUT /posts - Crear un post")
    void deletPost_ok() {
        int idToDelete = (createdId > 0) ? createdId : 1;
      
        Response response = client.deletePost(idToDelete);
        int status = response.statusCode();
       
        Assertions.assertThat(status).isIn(200, 204);
    }
}
