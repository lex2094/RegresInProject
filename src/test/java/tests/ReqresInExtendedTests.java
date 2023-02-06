package java.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import lombok.Data;
import java.models.lombok.LoginBodyLombokModel;
import java.models.lombok.LoginResponseLombokModel;
import java.models.pojo.LoginBodyModel;
import java.models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static java.specs.LoginSpecs.loginRequestSpec;
import static java.specs.LoginSpecs.loginResponseSpec;

public class ReqresInExtendedTests {

    @Test
    void loginTest() {
        //BAD PRACTIVE, move body in model
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void loginWithPojoModelTest() {
        //BAD PRACTIVE, move body in model

        LoginBodyModel body = new LoginBodyModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginResponsePojoModel response = given()
                .log().uri()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);
                //.body("token", is("QpwL5tke4Pnpja7X4"));

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    void loginWithLombokModelTest(){

        LoginBodyLombokModel body = new LoginBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
        //.body("token", is("QpwL5tke4Pnpja7X4"));

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }
    @Test
    void loginWithAllureListenerLombokModelTest(){

        LoginBodyLombokModel body = new LoginBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
        //.body("token", is("QpwL5tke4Pnpja7X4"));

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    void loginWithCustomAllureListenerLombokModelTest(){

        LoginBodyLombokModel body = new LoginBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
               //given(loginRequestSpec)
                .spec(loginRequestSpec)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseLombokModel.class);
        //.body("token", is("QpwL5tke4Pnpja7X4"));

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }
}
