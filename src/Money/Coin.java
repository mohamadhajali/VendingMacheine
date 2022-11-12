package Money;

public class Coin extends Money {
private int coinValue;

    public Coin(int coinValue, String currency, char category) {
        super(currency, category);
        this.coinValue = coinValue;
    }

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "coinValue=" + coinValue +
                '}';
    }
}
