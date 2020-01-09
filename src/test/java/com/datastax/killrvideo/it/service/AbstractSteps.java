package com.datastax.killrvideo.it.service;

import static java.lang.String.format;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.datastax.killrvideo.it.configuration.KillrVideoProperties;
import com.datastax.killrvideo.it.dao.CassandraDao;
import com.datastax.killrvideo.it.util.ServiceChecker;

import cucumber.api.PendingException;
import io.grpc.ManagedChannel;

@ContextConfiguration
@SpringBootTest
public abstract class AbstractSteps {

    /** Logger for Test.*/
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractSteps.class);
    
    @Inject
    protected ManagedChannel managedChannel;

    @Inject
    CassandraDao dao;

    @Inject
    ExecutorService threadPool;

    @Inject
    KillrVideoProperties properties;

    protected abstract String serviceName();

    protected void checkForService() {
        try {
            if (!ServiceChecker.isServicePresent(this.properties.services, serviceName())) {
                throw new PendingException(format("Please implement service %s on the KillrVideoServer",serviceName()));
            }
            LOGGER.info("Testing service {} OK", serviceName());
        } catch (Exception e) {
            throw new PendingException(format("Please implement service %s on the KillrVideoServer",serviceName()));
        }

    }
}
