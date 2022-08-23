package com.example.shop_module.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Test implements Serializable {
    private String title;

    public Test() {
    }

    public Test(String title) {
        this.title = title;
    }
}
