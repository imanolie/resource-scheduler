package ro.imanolie.scheduler.gateway;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.resource.third.party.ResourceWorker;
import ro.imanolie.scheduler.resource.third.party.WorkerCompleteListener;

/**
 * Concrete implementation of Gateway interface, that directly communicates with external 3rd party libraries.
 * When the resource finishes processing a message will notify the gateway about this.
 *
 * @author imanolie on 4/21/2015.
 */
public class GatewayImplementation implements Gateway, WorkerCompleteListener {

    public void send(Message msg) {
        this.process(msg);
    }

    private void process(Message msg) {
        ResourceWorker worker = new ResourceWorker(msg);
        worker.addListener(this);
        worker.start();
    }

    /**
     * Forwards the notification to the message observer.
     *
     * @param msg - just completed message
     */
    public void notifyOfThreadComplete(Message msg) {
        msg.notifyObserver();
    }
}
