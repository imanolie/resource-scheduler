package ro.imanolie.scheduler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.domain.SimpleMessage;
import ro.imanolie.scheduler.interceptor.MessagePriorityQueue;

/**
 * @author imanolie on 4/22/2015.
 */
public class MessagePriorityQueueTest {

    private MessagePriorityQueue priorityQueue = new MessagePriorityQueue();

    @Test
    public void shouldAddMessagesBasedOnPriority() {
        Message msg1 = new SimpleMessage("group1", "message 1");
        Message msg2 = new SimpleMessage("group2", "message 2");
        Message msg3 = new SimpleMessage("group1", "message 3");
        Message msg4 = new SimpleMessage("group3", "message 4");
        Message msg5 = new SimpleMessage("group4", "message 5");

        priorityQueue.add(msg1);
        priorityQueue.add(msg2);
        priorityQueue.add(msg3);
        priorityQueue.add(msg4);
        priorityQueue.add(msg5);

        String firstMsgGroupId = ((SimpleMessage) priorityQueue.poll()).getGroupId();
        String sndMsgGroupId = ((SimpleMessage) priorityQueue.poll()).getGroupId();

        Assert.assertEquals("group1", firstMsgGroupId);
        Assert.assertEquals("group1", sndMsgGroupId);
    }

    @Test
    public void shouldQueueBeNotEmpty() {
        Message msg1 = new SimpleMessage("group1", "message 1");
        Message msg2 = new SimpleMessage("group2", "message 2");
        Message msg3 = new SimpleMessage("group1", "message 3");
        Message msg4 = new SimpleMessage("group3", "message 4");
        Message msg5 = new SimpleMessage("group4", "message 5");

        priorityQueue.add(msg1);
        priorityQueue.add(msg2);
        priorityQueue.add(msg3);
        priorityQueue.add(msg4);
        priorityQueue.add(msg5);

        // remove messages from one group
        priorityQueue.poll();
        priorityQueue.poll();

        Assert.assertFalse(priorityQueue.isEmpty());
    }

    @Test
    public void shouldQueueBeEmpty() {
        Message msg1 = new SimpleMessage("group1", "message 1");
        Message msg2 = new SimpleMessage("group2", "message 2");
        Message msg3 = new SimpleMessage("group1", "message 3");

        priorityQueue.add(msg1);
        priorityQueue.add(msg2);
        priorityQueue.add(msg3);

        // remove messages from all group
        priorityQueue.poll();
        priorityQueue.poll();
        priorityQueue.poll();

        Assert.assertTrue(priorityQueue.isEmpty());
    }
}
