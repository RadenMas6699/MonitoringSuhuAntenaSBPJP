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






//package logo_lapan.go.id.monitoring;
//
//public class DataPoints {
//    int xValue, yValue;
//
//    public DataPoints() {
//    }
//
//    public DataPoints(int xValue, int yValue) {
//        this.xValue = xValue;
//        this.yValue = yValue;
//    }
//
//    public int getxValue() {
//        return xValue;
//    }
//
//    public int getyValue() {
//        return yValue;
//    }
//}
