package utils;

import java.io.FileOutputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Vector;

public class Logger {
    private Vector<Log> logs;
    private String name;

    public Logger(String name) {
        this.logs = new Vector<Log>();
        this.name = name;
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                saveToFile();
            }
        }, "Shutdown-thread"));
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

    public void saveToFile() {
        Random rand = new Random();
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
