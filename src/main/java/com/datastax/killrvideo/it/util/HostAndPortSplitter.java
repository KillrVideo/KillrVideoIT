package com.datastax.killrvideo.it.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostAndPortSplitter {

    public static Pattern HOST_AND_PORT_PATTERN = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|[A-Za-z0-9\\-_]+):(\\d+)");

    public static void ensureValidFormat(String hostAndPort, String errorMsg) {
        final Matcher matcher = HOST_AND_PORT_PATTERN.matcher(hostAndPort);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static String extractAddress(String hostAndPort) {
        final Matcher matcher = HOST_AND_PORT_PATTERN.matcher(hostAndPort);
        assert matcher.matches();
        return matcher.group(1);
    }

    public static int extractPort(String hostAndPort) {
        final Matcher matcher = HOST_AND_PORT_PATTERN.matcher(hostAndPort);
        assert matcher.matches();
        return Integer.parseInt(matcher.group(2));
    }
}
