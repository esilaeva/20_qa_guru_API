package com.demoqa.tests;

import com.demoqa.api.BooksApi;
import com.demoqa.model.books.DeleteBookResponseModel;
import com.demoqa.model.authorization.LoginResponseModel;
import com.demoqa.pages.ProfilePage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.demoqa.api.LoginApi.successLogin;

public class BookTests extends TestBase {

    @ParameterizedTest(name = "Deleting existent book")
    @CsvFileSource(resources = "/testdata.csv")
    void deleteExistentBookTests(String userName, String password, String isbn) {
        BooksApi bookApi = new BooksApi();
        ProfilePage  profilePage = new ProfilePage();
        LoginResponseModel login = successLogin(userName, password);

        profilePage.addCookies(login);

        bookApi.deleteAllBooks(login.getUserId(), login.getToken());
        bookApi.addBook(login.getUserId(), login.getToken(), isbn);

        bookApi.deleteOneBook(login.getUserId(), login.getToken(), isbn);

        profilePage
                .openProfilePage()
                .checkEmptyTable();
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
