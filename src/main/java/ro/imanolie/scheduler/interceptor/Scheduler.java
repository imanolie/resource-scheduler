package ro.imanolie.scheduler.interceptor;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.gateway.Gateway;
import ro.imanolie.scheduler.gateway.GatewayImplementation;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author imanolie on 4/21/2015.
 */
public class Scheduler {

    private int noOfResources;
    private Gateway gateway;

    private int noOfAvailableResources;

    private ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<Message>();

    public Scheduler(int noOfResources) {
        this.noOfResources = noOfResources;
        this.noOfAvailableResources = noOfResources;
        this.gateway = new GatewayImplementation(noOfResources);
    }

    public void send(Message msg) {
        schedule(msg);
    }

    private void schedule(Message msg) {
        if(areResourcesAvailable()) {
            this.gateway.send(msg);
        } else {
            queue.add(msg);
        }
    }

    private boolean areResourcesAvailable() {
        return noOfAvailableResources != 0;
    }
}
