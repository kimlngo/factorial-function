package util;

import balancer.configuration.SpringContext;

public class PoolSizeUtil {
    public static String getThreadPoolSizeResponse(String poolSizeKey) {
        return String.format("{\"%s\" :" + SpringContext.getProperties(poolSizeKey) + "}", poolSizeKey);
    }
}
