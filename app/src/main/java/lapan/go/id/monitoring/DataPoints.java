package lapan.go.id.monitoring;

public class DataPoints {
    long time;
    int temperature;

    public DataPoints() {
    }

    public DataPoints(long time, int temperature) {
        this.time = time;
        this.temperature = temperature;
    }

    public long getTime() {
        return time;
    }

    public int getTemperature() {
        return temperature;
    }
}