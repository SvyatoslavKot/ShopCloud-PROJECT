package com.example.shopclient_module.app.mq;

import java.util.Map;

public interface ProductModuleListener {

    void updateClientBucket(Map<String , Object> msgMap );
}
