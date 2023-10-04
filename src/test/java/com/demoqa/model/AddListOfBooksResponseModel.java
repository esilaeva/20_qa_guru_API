package com.demoqa.model;

import lombok.Data;

import java.util.List;

@Data
public class AddListOfBooksResponseModel {

    private List<BooksModel> books;
}
