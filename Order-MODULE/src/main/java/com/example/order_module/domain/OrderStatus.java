package com.example.order_module.domain;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    NEW,
    CREATED,
    APPROVED,
    CANCELED,
    PAID,
    CLOSED
}
