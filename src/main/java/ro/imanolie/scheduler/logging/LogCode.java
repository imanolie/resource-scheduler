package ro.imanolie.scheduler.logging;

/**
 * @author imanolie on 4/22/2015.
 */
public class LogCode {
    public static final String INFO_APP_STARTED = "Application has started.";
    public static final String INFO_STARTED_MSG_PROCESSING = "Message '{}' is now processing.";
    public static final String INFO_MSG_PROCESSING_SUCCESS = "Message '{}' was successfully processed.";
    public static final String INFO_MSG_RECEIVED = "Message '{}' was received.";
    public static final String INFO_MSG_SENT = "Message '{}' was sent for processing.";
    public static final String INFO_GROUP_CANCELED = "Further messages from group: '{}' will no longer be processed.";

    public static final String DEBUG_MSG_ADDED_TO_QUEUE_TO_EXISTING_GROUP = "Message '{}' was queued under existing group: '{}'.";
    public static final String DEBUG_MSG_ADDED_TO_QUEUE_NEW_GROUP = "Message '{}' was queued under new group: '{}'.";
    public static final String DEBUG_MSG_POLLED_FROM_QUEUE = "Message '{}' was polled out of queue.";

    public static final String ERR_INVALID_ARGS = "You must specify the application properties file path.";
    public static final String ERR_FILE_NOT_FOUND = "File '{}' doesn't exist.";
    public static final String ERR_FILE_READ = "An error occurred while reading the file: '{}'.";
    public static final String ERR_PROPERTY_MUST_BE_NUMBER = "Parameter: '{}' with value: '{}' is not a number.";
    public static final String ERR_MSG_PROCESSING_EXCEPTION = "An error occurred while processing message: '{}'";

}
