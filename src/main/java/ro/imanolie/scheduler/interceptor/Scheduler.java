package ro.imanolie.scheduler.interceptor;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.gateway.Gateway;
import ro.imanolie.scheduler.gateway.GatewayImplementation;

/**
 * @author imanolie on 4/21/2015.
 */
public class Scheduler {

    private int noOfResources;
    private Gateway gateway;

    public Scheduler(int noOfResources) {
        this.noOfResources = noOfResources;
        this.gateway = new GatewayImplementation(noOfResources);
    }

    public void send(Message msg) {
        // TODO
        this.gateway.send(msg);
    }
}
