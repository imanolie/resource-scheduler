package ro.imanolie.scheduler.interceptor;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.domain.SimpleMessage;
import ro.imanolie.scheduler.gateway.Gateway;
import ro.imanolie.scheduler.gateway.GatewayImplementation;
import ro.imanolie.scheduler.logging.LogCode;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class handles the way the messages are send to the 3rd party libraries for further processing.
 * His role is to schedule messages and not to flood the 3rd party resource, therefore a message will be send
 * only when at least one resource is available.
 *
 * @author imanolie on 4/21/2015.
 */
public class Scheduler implements MessageCompleteObserver {
    private final static Logger LOG = LogManager.getLogger(Scheduler.class.getName());

    private Gateway gateway;

    private int noOfAvailableResources;
    private MessagePriorityQueue msgQueue = new MessagePriorityQueue();
    private List<String> ignoredGroups = new ArrayList<String>();

    public Scheduler(int noOfResources) {
        this.noOfAvailableResources = noOfResources;
        this.gateway = new GatewayImplementation();
    }

    public void send(Message msg) {
        this.msgQueue.add(msg);
        LOG.info(LogCode.INFO_MSG_RECEIVED, msg);
        msg.setObserver(this);
        schedule();
    }

    public void cancel(String group) {
        this.ignoredGroups.add(group);
        LOG.info(LogCode.INFO_GROUP_CANCELED, group);
    }

    /**
     * If resources are available next message from queue will be sent for processing.
     * If next message is black listed, the process will repeat until a message is can be processed.
     */
    private void schedule() {
        if (areResourcesAvailable()) {
            Message nextMsgToBeProcessed = this.msgQueue.poll();
            SimpleMessage _msg = (SimpleMessage) nextMsgToBeProcessed;

            if(this.ignoredGroups.contains(_msg.getGroupId())) {
                schedule();
            } else {
                this.gateway.send(nextMsgToBeProcessed);
                this.noOfAvailableResources--;
            }
        }
    }

    private boolean areResourcesAvailable() {
        return noOfAvailableResources > 0;
    }

    /**
     * As message completes, resources are freed therefore is any messages are waiting in queue they will be processed.
     * @param msg - the completed message
     */
    public void update(Message msg) {
        msg.completed();
        this.noOfAvailableResources++;
        if(!this.msgQueue.isEmpty()) {
            this.schedule();
        }
    }
}
