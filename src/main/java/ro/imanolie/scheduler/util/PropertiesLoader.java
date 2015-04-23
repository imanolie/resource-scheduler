package ro.imanolie.scheduler.util;

import ro.imanolie.scheduler.exception.ApplicationInitializationException;
import ro.imanolie.scheduler.logging.LogCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * @author imanolie on 4/21/2015.
 */
public class PropertiesLoader {

    private final static Logger LOG = LogManager.getLogger(PropertiesLoader.class.getName());

    public static Properties load(String filePath) throws ApplicationInitializationException {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            properties.load(inputStream);
            System.setProperty(PropertyKeys.KEY_LOGGING_FILE_PATH,
                    properties.getProperty(PropertyKeys.KEY_LOGGING_FILE_PATH));
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
        } catch (FileNotFoundException e) {
            LOG.error(LogCode.ERR_FILE_NOT_FOUND, filePath);
            throw new ApplicationInitializationException(LogCode.ERR_FILE_NOT_FOUND);
        } catch (IOException e) {
            LOG.error(LogCode.ERR_FILE_READ, filePath);
            throw new ApplicationInitializationException(LogCode.ERR_FILE_READ);
        }

        return properties;
    }

    public static int getIntProperty(Properties properties, String key) throws ApplicationInitializationException {
        String property = properties.getProperty(key);
        int noOfResources;
        try {
            noOfResources = Integer.parseInt(property);
        } catch (NumberFormatException e) {
            LOG.error(LogCode.ERR_PROPERTY_MUST_BE_NUMBER, key, property);
            throw new ApplicationInitializationException(LogCode.ERR_PROPERTY_MUST_BE_NUMBER, e);
        }

        return noOfResources;
    }
}
