package com.example.productmodule.app.domain;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    NEW,
    APPROVED,
    CANCELED,
    PAID,
    CLOSED
}
