package utils;

public class Log {
    private Message message;
    private long receivedTime;
    private long startedTime;

    public Log(Message message) {
        this.message = message;
        this.receivedTime = System.currentTimeMillis();
    }

    public void startMessage() {
        this.startedTime = System.currentTimeMillis();
    }

    public String toString() {
        return this.message
                + " " +
                this.receivedTime
                + " " +
                this.startedTime
                + " " +
                (this.startedTime + this.message.getTime())
                + "\n";
    }
}
