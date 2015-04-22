package ro.imanolie.scheduler.interceptor;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.domain.SimpleMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Wrapper over ConcurrentLinkedQueue class that has a custom priority algorithm as described below.
 *
 * All the messages belonging to a group will be hold in a separate queue. All queue are hold in a map having as key the group.
 *
 * {@link ConcurrentLinkedQueue};
 *
 * @author imanolie on 4/22/2015.
 */
public class MessagePriorityQueue {
    /**
     * This field will hold the number of different groups of messages that need to be processed.
     */
    private int priorityCounter = 0;
    private Map<Integer, String> groupPriorities = new HashMap<Integer, String>();
    /**
     * Holder for messages that cannot be processed at the moment. Messages will be kept in queues under a certain key
     * representing the group.
     */
    private Map<String, ConcurrentLinkedQueue<SimpleMessage>> queueMap =
            new HashMap<String, ConcurrentLinkedQueue<SimpleMessage>>();

    public synchronized boolean isEmpty() {
        boolean isEmpty = true;

        for(Map.Entry<String, ConcurrentLinkedQueue<SimpleMessage>> entry : queueMap.entrySet()) {
            isEmpty = isEmpty && entry.getValue().isEmpty();
            if(!isEmpty) {
                break;
            }
        }

        return isEmpty;
    }

    /**
     * Method that adds a message to the queue for further processing.
     * @param message - message to be added
     */
    public synchronized void add(Message message) {
        SimpleMessage msg = (SimpleMessage) message;
        String groupId = msg.getGroupId();
        if(this.queueMap.containsKey(groupId)) {
            this.queueMap.get(groupId).add(msg);
        } else {
            ConcurrentLinkedQueue<SimpleMessage> msgQueue = new ConcurrentLinkedQueue<SimpleMessage>();
            msgQueue.add(msg);
            this.queueMap.put(groupId, msgQueue);
            this.priorityCounter++;
            this.groupPriorities.put(this.priorityCounter, groupId);
        }
    }

    /**
     * This method will return a message, based on priority, that will be send to Gateway for further processing
     * @return the message with the highest priority
     */
    public synchronized Message poll() {
        Message response = null;

        int priority = 1;
        String priorityGroup = getGroupIdByPriority(priority);
        ConcurrentLinkedQueue<SimpleMessage> priorityMessages = this.queueMap.get(priorityGroup);

        while (priorityMessages.isEmpty() && priority < priorityCounter) {
            priorityGroup = getGroupIdByPriority(++priority);
            priorityMessages = this.queueMap.get(priorityGroup);
        }

        if(!priorityMessages.isEmpty()) {
            response = priorityMessages.poll();
        }

        return response;
    }

    private String getGroupIdByPriority(int priority) {
        return this.groupPriorities.get(priority);
    }
}
