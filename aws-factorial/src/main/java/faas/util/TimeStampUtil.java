package faas.util;

public class TimeStampUtil {
    public static long getTimeStamp() {
        return System.currentTimeMillis() % 1000000;
    }
}
