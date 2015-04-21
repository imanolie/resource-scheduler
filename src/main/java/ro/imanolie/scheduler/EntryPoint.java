package ro.imanolie.scheduler;

import ro.imanolie.scheduler.exception.ApplicationInitializationException;
import ro.imanolie.scheduler.util.PropertiesLoader;
import ro.imanolie.scheduler.util.PropertyKeys;

import java.io.IOException;
import java.util.Properties;

/**
 * @author imanolie on 4/21/2015.
 */
public class EntryPoint {
    public static void main(String[] args) throws IOException {
        try {
            validateArgs(args);
            Properties properties = PropertiesLoader.load(args[0]);
            int noOfResources = PropertiesLoader.getIntProperty(properties, PropertyKeys.KEY_NO_OF_RESOURCES);
            System.out.println(noOfResources);
        } catch (ApplicationInitializationException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void validateArgs(String[] args) throws ApplicationInitializationException {
        if (args.length != 1) {
            throw new ApplicationInitializationException("You must specify the application properties file path.");
        }
    }
}
