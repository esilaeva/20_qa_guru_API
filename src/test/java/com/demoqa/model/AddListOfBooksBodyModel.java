package com.demoqa.model;

import lombok.Data;

import java.util.List;

@Data
public class AddListOfBooksBodyModel {

    private String userId;
    private List<CollectionOfIsbnsModel> collectionOfIsbns;
}
