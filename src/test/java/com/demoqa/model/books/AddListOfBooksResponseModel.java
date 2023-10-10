package com.demoqa.model.books;

import lombok.Data;

import java.util.List;

@Data
public class AddListOfBooksResponseModel {

    private List<BooksModel> books;
}
