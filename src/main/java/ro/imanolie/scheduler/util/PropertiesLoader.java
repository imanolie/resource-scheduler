package ro.imanolie.scheduler.util;

import ro.imanolie.scheduler.exception.ApplicationInitializationException;

import java.io.*;
import java.util.Properties;

/**
 * @author imanolie on 4/21/2015.
 */
public class PropertiesLoader {

    public static Properties load(String filePath) throws ApplicationInitializationException {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new ApplicationInitializationException("File '" + filePath + "' doesn't exist.");
        } catch (IOException e) {
            throw new ApplicationInitializationException("An error occurred while reading the file: '" + filePath + "'");
        }

        return properties;
    }

    public static int getIntProperty(Properties properties, String key) throws ApplicationInitializationException {
        String property = properties.getProperty(key);
        int noOfResources;
        try {
            noOfResources = Integer.parseInt(property);
        } catch (NumberFormatException e) {
            throw new ApplicationInitializationException("Parameter: '" + key + "' with value: '" + property + "' is not a number.", e);
        }

        return noOfResources;
    }
}
