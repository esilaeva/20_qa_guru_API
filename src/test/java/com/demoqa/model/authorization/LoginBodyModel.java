package com.demoqa.model.authorization;

import lombok.Data;

@Data
public class LoginBodyModel {

    private String
            userName,
            password;
}
