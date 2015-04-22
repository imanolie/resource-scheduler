package ro.imanolie.scheduler.interceptor;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.gateway.Gateway;
import ro.imanolie.scheduler.gateway.GatewayImplementation;

/**
 * This class handles the way the messages are send to the 3rd party libraries for further processing.
 * His role is to schedule messages and not to flood the 3rd party resource, therefore a message will be send
 * only when at least one resource is available.
 *
 * @author imanolie on 4/21/2015.
 */
public class Scheduler implements MessageCompleteObserver {

    private Gateway gateway;

    private int noOfAvailableResources;
    private MessagePriorityQueue msgQueue = new MessagePriorityQueue();

    public Scheduler(int noOfResources) {
        this.noOfAvailableResources = noOfResources;
        this.gateway = new GatewayImplementation();
    }

    public void send(Message msg) {
        msg.setObserver(this);
        schedule(msg);
    }

    private void schedule(Message msg) {
        msgQueue.add(msg);

        if(areResourcesAvailable()) {
            Message nextMsgToBeProcessed = this.msgQueue.poll();
            this.gateway.send(nextMsgToBeProcessed);
            this.noOfAvailableResources--;
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
            this.schedule(this.msgQueue.poll());
        }
    }
}
