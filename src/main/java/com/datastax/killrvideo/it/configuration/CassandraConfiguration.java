package com.datastax.killrvideo.it.configuration;

import static com.datastax.killrvideo.it.configuration.KillrVideoProperties.WAIT_TIME_IN_SECONDS;
import static java.lang.String.format;

import java.util.List;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.killrvideo.it.util.HostAndPortSplitter;
import com.datastax.killrvideo.it.util.ServiceChecker;

@Configuration
public class CassandraConfiguration {

    private static final String CLUSTER_NAME = "killrvideo";
    private static final String KEYSPACE_NAME = "killrvideo";

    @Inject
    private KillrVideoProperties properties;

    @Bean(destroyMethod = "close")
    public Session getSession() throws Exception {
        ServiceChecker.waitForService("Cassandra", properties.cassandraHost, properties.cassandraPort, WAIT_TIME_IN_SECONDS);

        final Cluster cluster = Cluster
                .builder()
                .addContactPoint(properties.cassandraHost)
                .withPort(properties.cassandraPort)
                .withClusterName(CLUSTER_NAME)
                .build();

        return cluster.connect();
    }
}
