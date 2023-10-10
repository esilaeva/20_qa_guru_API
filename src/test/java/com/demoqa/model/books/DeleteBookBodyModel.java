package com.demoqa.model.books;

import lombok.Data;

@Data
public class DeleteBookBodyModel {

    private String
            isbn,
            userId;
}
