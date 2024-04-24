package tests;

import io.restassured.response.Response;
import models.reqresAPI.GetUserResponseModel;
import models.reqresAPI.CreateUserResponseModel;
import models.reqresAPI.UpdateUserPatchResponseModel;
import models.reqresAPI.UpdateUserPutResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

import static specs.ReqresSpec.*;

public class ReqresApiExtendedTests extends TestBase {

    String userName = "morpheus";
    String userJob = "zion resident";
    String userJob2 = "leader";

    @Test
    @DisplayName("Get user request")
    void checkUsersListTest() {
        GetUserResponseModel getUserResponseModel = step("Get single user request", () ->
                given(defaultRequestSpec)

                        .when()
                        .get("/users/2")

                        .then()
                        .body(matchesJsonSchemaInClasspath("reqres/schemas/get_user_schema.json"))
                        .spec(successResponseSpec)
                        .extract().as(GetUserResponseModel.class));
        step("Check result", () -> {
            assertThat(getUserResponseModel.getData().getId()).isEqualTo("2");
            assertThat(getUserResponseModel.getData().getFirstName()).isEqualTo("Janet");
            assertThat(getUserResponseModel.getData().getLastName()).isEqualTo("Weaver");
            assertThat(getUserResponseModel.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
        });

    }

    @Test
    @DisplayName("Create user")
    void createUser() {
        CreateUserResponseModel createUserResponseData = new CreateUserResponseModel();
        createUserResponseData.setName(userName);
        createUserResponseData.setJob(userJob2);

        CreateUserResponseModel createUserResponseModel = step("Post create user request", () ->
                given(defaultRequestSpec)
                        .body(createUserResponseData)
                        .when()
                        .post("/users")
                        .then()
                        .body(matchesJsonSchemaInClasspath("reqres/schemas/create_user_schema.json"))
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));
        step("Check result", () -> {
            assertThat(createUserResponseModel.getName()).isEqualTo(userName);
            assertThat(createUserResponseModel.getJob()).isEqualTo(userJob2);
        });
    }

    @Test
    @DisplayName("Put edit test")
    void putTest() {
        UpdateUserPutResponseModel updateUserPutResponseData = new UpdateUserPutResponseModel();
        updateUserPutResponseData.setName(userName);
        updateUserPutResponseData.setJob(userJob);

        UpdateUserPutResponseModel updateUserPutResponseModel = step("Put edit request", () ->
                given(defaultRequestSpec)
                        .body(updateUserPutResponseData)
                        .when()
                        .put("/users/2")
                        .then()
                        .body(matchesJsonSchemaInClasspath("reqres/schemas/put_user_job_schema.json"))
                        .spec(successResponseSpec)
                        .extract().as(UpdateUserPutResponseModel.class));
        step("Check result", () -> {
            assertThat(updateUserPutResponseModel.getName()).isEqualTo(userName);
            assertThat(updateUserPutResponseModel.getJob()).isEqualTo(userJob);
        });
    }

    @Test
    @DisplayName("Patch edit test")
    void patchTest() {
        UpdateUserPatchResponseModel updateUserPatchResponseData = new UpdateUserPatchResponseModel();
        updateUserPatchResponseData.setName(userName);
        updateUserPatchResponseData.setJob(userJob);

        UpdateUserPatchResponseModel updateUserPatchResponseModel = step("Patch edit request", () ->
                given(defaultRequestSpec)
                        .body(updateUserPatchResponseData)
                        .when()
                        .patch("/users/2")
                        .then()
                        .body(matchesJsonSchemaInClasspath("reqres/schemas/patch_user_job_schema.json"))
                        .spec(successResponseSpec)
                        .extract().as(UpdateUserPatchResponseModel.class));
        step("Check result", () -> {
            assertThat(updateUserPatchResponseModel.getName()).isEqualTo(userName);
            assertThat(updateUserPatchResponseModel.getJob()).isEqualTo(userJob);
        });
    }

    @Test
    @DisplayName("Delete test")
    void deleteTest() {
        Response response = step("Delete request", () ->
                given(defaultRequestSpec)

                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(deleteResponseSpec)
                        .extract().response());

        step("Check response", () ->
                assertThat(response.getStatusCode()).isEqualTo(204));

    }
}
