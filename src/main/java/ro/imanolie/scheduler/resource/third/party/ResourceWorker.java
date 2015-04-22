package ro.imanolie.scheduler.resource.third.party;

import ro.imanolie.scheduler.domain.Message;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Thread implementation that mimics third party resource behaviour.
 * Given the time needed for a resource to process a message, the thread will sleep
 * for a random number of seconds.
 *
 * @author imanolie on 4/22/2015.
 */
public class ResourceWorker extends Thread {

    private Message msg;
    private Random random = new Random();

    private final Set<WorkerCompleteListener> listeners = new CopyOnWriteArraySet<WorkerCompleteListener>();

    public ResourceWorker(Message msg) {
        this.msg = msg;
    }

    public void addListener(final WorkerCompleteListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (WorkerCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this.getMessage());
        }
    }

    public Message getMessage() {
        return this.msg;
    }

    public void run() {
        System.err.println("Run: " + this.getName() + " " + this.msg.getContent());
        try {
            this.process();
        } finally {
            notifyListeners();
        }
    }

    private void process() {
        int x = random.nextInt(100);
        try {
            Thread.sleep(x * 100);
        } catch (InterruptedException e) {
            // TODO log
            e.printStackTrace();
        }
    }
}
