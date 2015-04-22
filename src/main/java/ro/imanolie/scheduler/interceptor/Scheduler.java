package ro.imanolie.scheduler.interceptor;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.gateway.Gateway;
import ro.imanolie.scheduler.gateway.GatewayImplementation;

import java.util.concurrent.ConcurrentLinkedQueue;

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
    private ConcurrentLinkedQueue<Message> msgQueue = new ConcurrentLinkedQueue<Message>();

    public Scheduler(int noOfResources) {
        this.noOfAvailableResources = noOfResources;
        this.gateway = new GatewayImplementation();
    }

    public void send(Message msg) {
        msg.setObserver(this);
        schedule(msg);
    }

    private void schedule(Message msg) {
        System.err.println(noOfAvailableResources + " schedule: " + Thread.currentThread().getName() + " " + msg.getContent());
        if(areResourcesAvailable()) {
            System.err.println("schedule->sent: " + Thread.currentThread().getName() + " " + msg.getContent());
            this.gateway.send(msg);
            this.noOfAvailableResources--;
        } else {
            System.out.println("schedule->queued: " + Thread.currentThread().getName() + " " + msg.getContent());
            msgQueue.add(msg);
        }
    }

    private boolean areResourcesAvailable() {
        return noOfAvailableResources > 0;
    }

    public void update(Message msg) {
        msg.completed();
        if(!this.msgQueue.isEmpty()) {
            this.noOfAvailableResources++;
            this.schedule(this.msgQueue.poll());
        }
    }
}
