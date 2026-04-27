package utils;

import constants.ErrorMessages;

public class ConversionUtil {
    public static double convert(double amount, double rate, String from, String to) {

        if (amount <= 0) {
            throw new RuntimeException(ErrorMessages.INVALID_AMOUNT);
        }
        if (from.equalsIgnoreCase(to)) {
            throw new RuntimeException(ErrorMessages.SAME_CURRENCY);
        }

        return amount * rate;
    }
}
