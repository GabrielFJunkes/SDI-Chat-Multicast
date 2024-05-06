package utils;

import java.io.FileOutputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Vector;

public class Logger {
    private Vector<Log> logs;
    private String name;

    public Logger(String name) {
        this.logs = new Vector<Log>();
        this.name = name;
    }

    /**
     * @param message
     * @return index for the added message
     */
    public int addMessage(Message message) {
        Log log = new Log(message);
        logs.add(log);
        return logs.size();
    }

    /**
     * @param index
     */
    public void startMessage(int index) {
        if (index < 0 || index >= this.logs.size()) {
            return;
        }
        this.logs.get(index).startMessage();
    }

    public void saveToFile() {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("logs/log-" + this.name + ".txt"), "utf-8"))) {
            for (Log log : logs) {
                writer.write(log.toString());
            }
        } catch (Exception e) {
            for (Log log : logs) {
                System.out.println(log);
            }
        }

    }

}
