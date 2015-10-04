package hr.vspr.dpasic.socials.rest.config;

import org.springframework.core.env.Environment;

public class EnvironmentUtils {

    private static Environment environment;

    private EnvironmentUtils() {
    }

    public static void setEnvironment(Environment environment) {
        EnvironmentUtils.environment = environment;
    }

    public static String getProperty(String property) {
        return environment.getProperty(property);
    }
}
