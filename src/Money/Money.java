package Money;

public class Money {
    private String currency;
    private char category;

    public Money(String currency , char category) {
        this.currency = currency;
        this.category = category;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public char getCategory() {
        return category;
    }

    public void setCategory(char category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency='" + currency + '\'' +
                ", category=" + category +
                '}';
    }
}
