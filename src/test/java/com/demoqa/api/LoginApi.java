package com.demoqa.api;

import com.demoqa.model.authorization.LoginBodyModel;
import com.demoqa.model.authorization.LoginResponseModel;
import com.demoqa.tests.TestBase;
import io.qameta.allure.Step;

import static com.demoqa.specs.LoginSpec.successLogin200ResponseSpec;
import static com.demoqa.specs.LoginSpec.successLoginRequestSpec;
import static io.restassured.RestAssured.given;

public class LoginApi extends TestBase {

    @Step("Make login request")
    public static LoginResponseModel successLogin(String userName, String password) {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setUserName(userName);
        authData.setPassword(password);

        return given(successLoginRequestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(successLogin200ResponseSpec)
                .extract().as(LoginResponseModel.class);
    }
}