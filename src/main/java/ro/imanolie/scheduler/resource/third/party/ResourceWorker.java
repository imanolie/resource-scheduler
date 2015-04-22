package ro.imanolie.scheduler.resource.third.party;

import ro.imanolie.scheduler.domain.Message;
import ro.imanolie.scheduler.logging.LogCode;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Thread implementation that mimics third party resource behaviour.
 * Given the time needed for a resource to process a message, the thread will sleep
 * for a random number of seconds.
 *
 * @author imanolie on 4/22/2015.
 */
public class ResourceWorker extends Thread {

    private final static Logger LOG = LogManager.getLogger(ResourceWorker.class.getName());

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
        LOG.info(LogCode.INFO_STARTED_MSG_PROCESSING, this.msg);
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
            LOG.error(LogCode.ERR_MSG_PROCESSING_EXCEPTION, e);
        }
    }
}
