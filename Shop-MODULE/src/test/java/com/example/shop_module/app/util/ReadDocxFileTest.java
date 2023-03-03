package com.example.shop_module.app.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadDocxFileTest {


    private ReadDocxFile readDocxFile = new ReadDocxFile();
    @Test
    void read() {

        System.out.println(readDocxFile.read());
        assertEquals(readDocxFile.read(), "Понедельник");
    }
}