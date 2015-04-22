package ro.imanolie.scheduler.domain;

import ro.imanolie.scheduler.interceptor.MessageCompleteObserver;

/**
 * @author imanolie on 12/21/2015.
 */
public abstract class Message {

    protected MessageCompleteObserver observer;
    /**
     * This method will be called once the message processing is completed.
     */
    public abstract void completed();

    public void setObserver(MessageCompleteObserver observer) {
        this.observer = observer;
    }

    public void notifyObserver() {
        this.observer.update(this);
    }
}
