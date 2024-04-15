package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class HomeWorkApiTests extends TestBase {

    @Test
    @DisplayName("Check users list")
    void checkUserslist() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("homework/schemas/list_users_schema.json"));
    }

    @Test
    @DisplayName("Create user")
    void createUser() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/create_user_schema.json"))
                .statusCode(201)
                .extract().response();
    }

    @Test
    @DisplayName("Put edit test")
    void putTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/put_user_job_schema.json"))
                .statusCode(200)
                .extract().response();
    }

    @Test
    @DisplayName("Patch edit test")
    void patchTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("homework/schemas/patch_user_job_schema.json"))
                .statusCode(200)
                .extract().response();
    }
    @Test
    @DisplayName("Delete test")
    void deleteTest() {
        given()
                .log().uri()
                .log().method()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204)
                .extract().response();
    }
}
