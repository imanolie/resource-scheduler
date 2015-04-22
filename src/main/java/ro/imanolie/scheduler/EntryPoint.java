package ro.imanolie.scheduler;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.domain.SimpleMessage;
import ro.imanolie.scheduler.exception.ApplicationInitializationException;
import ro.imanolie.scheduler.interceptor.Scheduler;
import ro.imanolie.scheduler.resource.third.party.ResourceWorker;
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
            Scheduler scheduler = new Scheduler(noOfResources);
            sendBulk(scheduler);
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
