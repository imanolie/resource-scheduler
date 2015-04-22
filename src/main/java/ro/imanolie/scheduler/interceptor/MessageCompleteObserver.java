package ro.imanolie.scheduler.interceptor;

import ro.imanolie.scheduler.domain.Message;

/**
 * Classes that implements this interface can be set as observer on a message instance. Therefore, whenever a message
 * processing completes, the update method will be executed.
 *
 * @author imanolie on 4/22/2015.
 */
public interface MessageCompleteObserver {
    /**
     * This method will be executed once processing a message completes.
     *
     * @param msg - the completed message
     */
    void update(Message msg);
}
