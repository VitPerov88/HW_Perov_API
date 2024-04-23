package tests;

import io.restassured.response.Response;
import models.homework.GetUserResponseModel;
import models.homework.CreateUserResponseModel;
import models.homework.UpdateUserPatchResponseModel;
import models.homework.UpdateUserPutResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static specs.HomeWorkSpec.*;

public class HomeWorkApiExtendedTests extends TestBase {

    String userName = "morpheus";
    String userJob = "zion resident";
    String userJob2 = "leader";

    @Test
    @DisplayName("Get user request")
    void checkUsersListTest() {
            GetUserResponseModel getUserResponseModel = step("Make request", () ->
                given(defaultRequestSpec)

                .when()
                        .get("/users/2")

                .then()
                        .spec(getUserResponseSpec)
                        .extract().as(GetUserResponseModel.class));
        step("Check result", () -> {
            assertThat(getUserResponseModel.getData().getId(), is(equalTo("2")));
            assertThat(getUserResponseModel.getData().getFirstName(), is(equalTo("Janet")));
            assertThat(getUserResponseModel.getData().getLastName(), is(equalTo("Weaver")));
            assertThat(getUserResponseModel.getData().getAvatar(), is(equalTo("https://reqres.in/img/faces/2-image.jpg")));
        });

    }
    @Test
    @DisplayName("Create user")
    void createUser() {
        CreateUserResponseModel createUserResponseData = new CreateUserResponseModel();
        createUserResponseData.setName(userName);
        createUserResponseData.setJob(userJob2);

        CreateUserResponseModel createUserResponseModel = step("Make request", () ->
                given(defaultRequestSpec)
                        .body(createUserResponseData)
                .when()
                        .post("/users")
                .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));
        step("Check result", () -> {
            assertThat(createUserResponseModel.getName(), is(equalTo(userName)));
            assertThat(createUserResponseModel.getJob(), is(equalTo(userJob2)));
        });
    }

    @Test
    @DisplayName("Put edit test")
    void putTest() {
        UpdateUserPutResponseModel updateUserPutResponseData = new UpdateUserPutResponseModel();
        updateUserPutResponseData.setName(userName);
        updateUserPutResponseData.setJob(userJob);

        UpdateUserPutResponseModel updateUserPutResponseModel = step("Make request", () ->
        given(defaultRequestSpec)
                .body(updateUserPutResponseData)
        .when()
                .put("/users/2")
        .then()
                .spec(updateUserPutResponseSpec)
                .extract().as(UpdateUserPutResponseModel.class));
        step("Check result", () -> {
            assertThat(updateUserPutResponseModel.getName(), is(equalTo(userName)));
            assertThat(updateUserPutResponseModel.getJob(), is(equalTo(userJob)));
        });
    }

    @Test
    @DisplayName("Patch edit test")
    void patchTest() {
        UpdateUserPatchResponseModel updateUserPatchResponseData = new UpdateUserPatchResponseModel();
        updateUserPatchResponseData.setName(userName);
        updateUserPatchResponseData.setJob(userJob);

        UpdateUserPatchResponseModel updateUserPatchResponseModel = step("Make request", () ->
                given(defaultRequestSpec)
                        .body(updateUserPatchResponseData)
                .when()
                        .patch("/users/2")
                .then()
                        .spec(updateUserPatchResponseSpec)
                        .extract().as(UpdateUserPatchResponseModel.class));
        step("Check result", () -> {
            assertThat(updateUserPatchResponseModel.getName(), is(equalTo(userName)));
            assertThat(updateUserPatchResponseModel.getJob(), is(equalTo(userJob)));
        });
    }

    @Test
    @DisplayName("Delete test")
    void deleteTest() {
        Response response = step("Make request", () ->
        given(defaultRequestSpec)

        .when()
                .delete("/users/2")
        .then()
                .spec(deleteResponseSpec)
                .extract().response());

        step("Check response", () ->
                assertThat(response.getStatusCode(), is(equalTo(204))));

    }
}
