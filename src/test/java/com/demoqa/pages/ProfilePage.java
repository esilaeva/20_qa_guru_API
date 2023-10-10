package com.demoqa.pages;

import com.demoqa.model.authorization.LoginResponseModel;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProfilePage {

    @Step("Add Cookies")
    public ProfilePage addCookies(LoginResponseModel response) {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", response.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", response.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", response.getExpires()));

        return this;
    }

    @Step("Open Profile Page")
    public ProfilePage openProfilePage() {
        open("/profile");

        return this;
    }

    @Step("Verify: the list of books in Profile is empty")
    public void checkEmptyTable() {
        $("[id='see-book-Git Pocket Guide']").shouldNotBe(visible);
    }
}
