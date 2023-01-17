package com.example.shop_module.app.domain.enums;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    NEW,
    APPROVED,
    CANCELED,
    PAID,
    CLOSED
}