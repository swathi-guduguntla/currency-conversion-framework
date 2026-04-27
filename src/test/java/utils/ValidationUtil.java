package utils;

public class ValidationUtil {

    public static boolean isWithinTolerance(double expected, double actual) {
        double tolerance = expected * 0.01;

        double min = expected - tolerance;
        double max = expected + tolerance;
        System.out.println("Within tolerance min and max values : " + min + "     " + max);
        return actual >= min && actual <= max;
    }
}
