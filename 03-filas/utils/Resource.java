package utils;

public class Resource {
    private final long time;
    private final int resource;

    public Resource(long time, int resource) throws Error {
        this.time = time;
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }

    public long getTime() {
        return time;
    }

    public String toString() {
        return this.time + " " + this.resource;
    }
}
