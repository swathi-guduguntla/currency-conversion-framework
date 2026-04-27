package utils;

public class ConversionUtil {

    public static double convert(double amount, double rate) {
        if (amount <= 0) {
            throw new RuntimeException("Invalid amount");
        }
        return amount * rate;
    }
}
