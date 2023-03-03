package com.example.order_module.config;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.boot.system.SystemProperties;

public class CassandraConnection {
    private Cluster cluster;

    private Session session;

    public void connect(String node, Integer port) {
        SystemProperties.get("USE_NATIVE_CLOCK_SYSTEM_PROPERTY");

        Cluster.Builder b = Cluster.builder()
                .withCredentials("cassandra", "password123")

                .addContactPoint(node);
        if (port != null) {
            b.withPort(port);
        }
        cluster = b.build();

        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }

}
