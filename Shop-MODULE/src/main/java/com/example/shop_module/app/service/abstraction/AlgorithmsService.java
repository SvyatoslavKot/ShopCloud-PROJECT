package com.example.shop_module.app.service.abstraction;

import com.example.shop_module.app.domain.MessageFromSocket;

public interface AlgorithmsService {

    void sortBubble(MessageFromSocket message);
    void sortQuick(MessageFromSocket message);
}
