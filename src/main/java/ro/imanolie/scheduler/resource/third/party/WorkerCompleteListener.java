package ro.imanolie.scheduler.resource.third.party;

import ro.imanolie.scheduler.domain.Message;

public interface WorkerCompleteListener {
    void notifyOfThreadComplete(final Message message);
}