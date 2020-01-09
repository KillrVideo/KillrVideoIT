package com.datastax.killrvideo.it.configuration;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KillrVideoProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(KillrVideoProperties.class);

    public static final int WAIT_TIME_IN_SECONDS = 10;
    public static final String APPLICATION_NAME  = "killrvideo.application.name";
    public static final String CASSANDRA_HOST    = "killrvideo.cassandra.host";
    public static final String CASSANDRA_PORT    = "killrvideo.cassandra.port";

    public final String applicationName;
    public final String cassandraHost;
    public final Integer cassandraPort;

    public final HashMap<String, String> services = new HashMap<String, String>();

    @Inject
    private Environment env;

    public KillrVideoProperties(Environment env) {
        this.applicationName = env.getProperty(APPLICATION_NAME, "KillrVideo");
        this.cassandraHost   = env.getProperty(CASSANDRA_HOST, "dse");
        this.cassandraPort   = Integer.parseInt(env.getProperty(CASSANDRA_PORT, "9042"));

        this.services.put("UserManagementService", "backend:50101");
        this.services.put("CommentsService", "backend:50101");
        this.services.put("VideoCatalogService", "backend:50101");
        this.services.put("RatingsService", "backend:50101");
        this.services.put("StatisticsService", "backend:50101");
        this.services.put("SearchService", "backend:50101");
        this.services.put("SuggestedVideoService", "backend:50101");
    }
}
