package com.example.productmodule.domain;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    NEW,
    APPROVED,
    CANCELED,
    PAID,
    CLOSED
}
