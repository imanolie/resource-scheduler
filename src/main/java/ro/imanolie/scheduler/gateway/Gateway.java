package ro.imanolie.scheduler.gateway;

import ro.imanolie.scheduler.domain.Message;

/**
 * @author imanolie on 4/21/2015.
 */
public interface Gateway {
    void send(Message msg);
}
