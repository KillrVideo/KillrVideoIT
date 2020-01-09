package com.datastax.killrvideo.it.util;

import static java.lang.String.format;

import com.datastax.killrvideo.it.configuration.KillrVideoProperties;

import java.util.HashMap;
import java.io.IOException;
import java.net.*;

public class ServiceChecker {

    public static final int WAIT_TIMEOUT = 600;

    public static void waitForService(String service, String address, int port, int waitTimeInSec) throws Exception {

        System.out.println(format("Attempting to connect to service %s on %s:%s", service, address, port));

        long startTime = System.currentTimeMillis();
        while (true) {
            if (isServiceAccessible(service, address, port)) {
                return;
            } else {
                if (System.currentTimeMillis() - startTime >= WAIT_TIMEOUT * 1000) {
                    throw new RuntimeException(format("Timeout reached while waiting for the %s service.", service));
                }
                System.out.println(format("Waiting %s secs for %s to start, %s seconds left till timeout", waitTimeInSec, service, ((startTime + WAIT_TIMEOUT * 1000 ) - System.currentTimeMillis()) / 1000));
                Thread.sleep(waitTimeInSec * 1000);
            }
        }
    }

    public static boolean isServicePresent(HashMap<String, String> services, String grpcServiceName) throws Exception {
        if (!services.containsKey(grpcServiceName)) {
            return false;
        }

        final String address = services.get(grpcServiceName);

        HostAndPortSplitter.ensureValidFormat(
            address,
            format("The %s is not a valid host:port format", address)
        );

        final String host = HostAndPortSplitter.extractAddress(address);
        final int port = HostAndPortSplitter.extractPort(address);

        return isServiceAccessible(
            grpcServiceName, 
            host, 
            port
        );
    }

    private static boolean isServiceAccessible(String service, String address, int port) throws Exception {
        Socket s = null;
        try {
            s = new Socket(address, port);
            s.setReuseAddress(true);
            System.out.println(format("Connection to %s:%s is working for service %s", address, port, service));
            try {
                s.close();
            } catch (IOException e) {

            }

            return true;
        } catch (Exception e) {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException ex) {
                }
            }
            return false;
        }
    }
}
