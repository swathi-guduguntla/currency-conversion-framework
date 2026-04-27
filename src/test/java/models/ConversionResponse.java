package models;

public class ConversionResponse {

    private String from;
    private String to;
    private double amount;

    public ConversionResponse(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public double getAmount() { return amount; }
}
