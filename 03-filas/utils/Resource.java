package utils;

public class Resource {
    private final int time;
    private final int resource;
    
    public Resource(int time, int resource) throws Error {
        this.time = time;
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }

    public int getTime() {
        return time;
    }
}
