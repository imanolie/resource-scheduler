package ro.imanolie.scheduler.domain;

/**
 * @author imanolie on 12/21/2015.
 */
public class SimpleMessage extends Message {

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
        // TODO log
        System.out.println("Message: '" + this.message + "' with groupId: '" + this.groupId + "' was successfully processed.");
    }

    public String getContent() {
        return this.groupId + "#" + this.message;
    }

}
