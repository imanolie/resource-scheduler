package ro.imanolie.scheduler.domain;

/**
 * @author imanolie on 12/21/2015.
 */
public interface Message {
    /**
     * This method will be called once the message processing is completed.
     */
    void completed();

    String getContent();
}
