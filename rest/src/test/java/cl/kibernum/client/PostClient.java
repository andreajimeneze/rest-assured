package cl.kibernum.client;

import static io.restassured.RestAssured.given;

import cl.kibernum.config.Specs;
import cl.kibernum.model.Post;
import io.restassured.response.Response;

// Clase que agrupa métodos para llamar a los endpoints relacionados con Post
public class PostClient {
    // Método para listar los posts
    public Response listPost() {
        return given()
                .spec(Specs.request())
                .when()
                .get("/posts")
                .then()
                .spec(Specs.ok200())
                .extract().response();
    }

    // Método para obtener el detalle de un post por id
    public Response getPost(int id) {
        return given()
                .spec(Specs.request())
                .pathParam("id", id)
                .when()
                .get("/posts/{id}");
    }

    // Método para crear un post
    public Response createPost(Post payload) {
        return given()
                .spec(Specs.request())
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .spec(Specs.created201())
                .extract().response();
    }

    // Método para actualizar un post
    public Response updatedPost(int id, Post payload) {
        return given()
                .spec(Specs.request())
                .body(payload)
                .when()
                .put("/posts/{id}", id)
                .then()
                .spec(Specs.ok200())
                .extract().response();
    }

    // Método para eliminar un post
    public Response deletePost(int id) {
        return given()
                .spec(Specs.request())
                .when()
                .delete("/posts/{id}", id)
                .then()
                .spec(Specs.ok200())
                .extract().response();
    }
}
