package ro.imanolie.scheduler.gateway;

import ro.imanolie.scheduler.domain.Message;

/**
 * @author imanolie on 4/21/2015.
 */
public class GatewayImplementation implements Gateway {

    private int noOfResources;

    public GatewayImplementation(int noOfResources) {
        this.noOfResources = noOfResources;
    }

    @Override
    public void send(Message msg) {
        this.process(msg);
    }

    private void process(Message msg) {
        System.out.println(msg.getContent());
    }
}
