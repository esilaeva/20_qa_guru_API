package com.demoqa.api;

import com.demoqa.model.books.*;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static com.demoqa.specs.BookSpec.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooksApi {

    @Step("Add the book to Profile with isbn: {2}")
    public AddListOfBooksResponseModel addBook(String userId, String token, String isbn) {

        CollectionOfIsbnsModel isbnModel = new CollectionOfIsbnsModel();
        isbnModel.setIsbn(isbn);
        List<CollectionOfIsbnsModel> listIsbn = new ArrayList<>();
        listIsbn.add(isbnModel);

        AddListOfBooksBodyModel bookData = new AddListOfBooksBodyModel();
        bookData.setUserId(userId);
        bookData.setCollectionOfIsbns(listIsbn);

        return given(bookRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(successAddBook201ResponseSpec)
                .extract().as(AddListOfBooksResponseModel.class);
    }

    @Step("Delete all books request")
    public void deleteAllBooks(String userId, String token) {
        DeleteAllBooksModel allBookForDeleteData = new DeleteAllBooksModel();
        allBookForDeleteData.setUserId(userId);

        given(bookRequestSpec)
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(successDeleteBook204ResponseSpec);
    }

    @Step("Delete the book with isbn: {2}")
    public void deleteOneBook(String userId, String token, String isbn) {
        DeleteBookBodyModel bookForDeleteData = new DeleteBookBodyModel();
        bookForDeleteData.setUserId(userId);
        bookForDeleteData.setIsbn(isbn);

        given(bookRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(bookForDeleteData)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(successDeleteBook204ResponseSpec);
    }

    @Step("Delete the book with isbn: {2}")
    public DeleteBookResponseModel deleteBookWithWrongIsbn(String userId, String token, String isbnWrong) {
        DeleteBookBodyModel bookForDeleteData = new DeleteBookBodyModel();
        bookForDeleteData.setUserId(userId);
        bookForDeleteData.setIsbn(isbnWrong);

        return given(bookRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(bookForDeleteData)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(errorDeleteBook400ResponseSpec)
                .extract().as(DeleteBookResponseModel.class);
    }

    @Step("Verify response")
    public void assertEqualsErrorCode(DeleteBookResponseModel response, String code, String message) {
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
    }
}
