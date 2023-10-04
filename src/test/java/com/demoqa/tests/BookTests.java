package com.demoqa.tests;

import com.demoqa.api.BooksApi;
import com.demoqa.model.DeleteBookResponseModel;
import com.demoqa.model.LoginResponseModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.demoqa.api.LoginApi.successLogin;

public class BookTests extends TestBase {

    @ParameterizedTest(name = "Deleting existent book")
    @CsvFileSource(resources = "/testdata.csv")
    void deleteExistentBookTests(String userName, String password, String isbn) {
        BooksApi bookApi = new BooksApi();
        LoginResponseModel login = successLogin(userName, password);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", login.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", login.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", login.getExpires()));

        bookApi.deleteAllBooks(login.getUserId(), login.getToken());
        bookApi.addBook(login.getUserId(), login.getToken(), isbn);

        bookApi.deleteOneBook(login.getUserId(), login.getToken(), isbn);

        open("/profile");
        $("[id='see-book-Git Pocket Guide']").shouldBe(disappear);
    }

    @ParameterizedTest(name = "Deleting non-existent book")
    @CsvFileSource(resources = "/testdataWrongIsbn.csv")
    void deleteNonExistentBookTests(String userName, String password, String isbn, String isbnWrong) {
        BooksApi bookApi = new BooksApi();
        LoginResponseModel login = successLogin(userName, password);

        bookApi.deleteAllBooks(login.getUserId(), login.getToken());
        bookApi.addBook(login.getUserId(), login.getToken(), isbn);
        DeleteBookResponseModel response = bookApi.deleteBookWithWrongIsbn(login.getUserId(), login.getToken(), isbnWrong);

        bookApi.assertEqualsErrorCode(response, "1206", "ISBN supplied is not available in User's Collection!");
    }
}
