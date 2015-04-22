package ro.imanolie.scheduler;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.domain.SimpleMessage;
import ro.imanolie.scheduler.exception.ApplicationInitializationException;
import ro.imanolie.scheduler.interceptor.Scheduler;
import ro.imanolie.scheduler.logging.LogCode;
import ro.imanolie.scheduler.util.PropertiesLoader;
import ro.imanolie.scheduler.util.PropertyKeys;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author imanolie on 4/21/2015.
 */
public class EntryPoint {

    private final static Logger LOG = LogManager.getLogger(EntryPoint.class.getName());

    public static void main(String[] args) throws IOException {
        LOG.info(LogCode.INFO_APP_STARTED);
        try {
            validateArgs(args);
            Properties properties = PropertiesLoader.load(args[0]);
            int noOfResources = PropertiesLoader.getIntProperty(properties, PropertyKeys.KEY_NO_OF_RESOURCES);
            Scheduler scheduler = new Scheduler(noOfResources);
            sendBulk(scheduler);
        } catch (ApplicationInitializationException e) {
            LOG.error(e.getMessage(), e);
            System.exit(-1);
        }
    }

    public static void validateArgs(String[] args) throws ApplicationInitializationException {
        if (args.length != 1) {
            LOG.error(LogCode.ERR_INVALID_ARGS);
            throw new ApplicationInitializationException(LogCode.ERR_INVALID_ARGS);
        }
    }

    public static void sendBulk(Scheduler scheduler) {
        Message msg1 = new SimpleMessage("group1", "message 1");
        Message msg2 = new SimpleMessage("group2", "message 2");
        Message msg3 = new SimpleMessage("group1", "message 3");
        Message msg4 = new SimpleMessage("group3", "message 4");
        Message msg5 = new SimpleMessage("group4", "message 5");

        scheduler.send(msg1);
        scheduler.send(msg2);
        scheduler.send(msg3);
        scheduler.send(msg4);
        scheduler.send(msg5);

    }
}
