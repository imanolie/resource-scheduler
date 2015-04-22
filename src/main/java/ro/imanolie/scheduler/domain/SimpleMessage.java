package ro.imanolie.scheduler.domain;

import ro.imanolie.scheduler.logging.LogCode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author imanolie on 12/21/2015.
 */
public class SimpleMessage extends Message {

    private final static Logger LOG = LogManager.getLogger(SimpleMessage.class.getName());

    protected String groupId;
    protected String message;

    public SimpleMessage(String groupId, String message) {
        this.groupId = groupId;
        this.message = message;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void completed() {
        LOG.info(LogCode.INFO_MSG_PROCESSING_SUCCESS, this);
    }

    @Override
    public String toString() {
        return "SimpleMessage{" + "groupId='" + groupId + '\'' + ", message='" + message + '\'' + '}';
    }
}
