package MoneySlot;

public  class CoinBalance {

    private int numberOf10c;
    private int numberOf20c;
    private int numberOf50c;
    private int numberOf1Dollar;

    public CoinBalance() {

        this.numberOf10c=0;
        this.numberOf20c=0;
        this.numberOf50c=0;
        this.numberOf1Dollar=0;
    }

    public int getNumberOf10c() {
        return numberOf10c;
    }

    public void setNumberOf10c(int numberOf10c) {
        this.numberOf10c = numberOf10c;
    }

    public int getNumberOf20c() {
        return numberOf20c;
    }

    public void setNumberOf20c(int numberOf20c) {
        this.numberOf20c = numberOf20c;
    }

    public int getNumberOf50c() {
        return numberOf50c;
    }

    public void setNumberOf50c(int numberOf50c) {
        this.numberOf50c = numberOf50c;
    }

    public int getNumberOf1Dollar() {
        return numberOf1Dollar;
    }

    public void setNumberOf1Dollar(int numberOf1Dollar) {
        this.numberOf1Dollar = numberOf1Dollar;
    }

    @Override
    public String toString() {
        return "CoinBalance{" +
                "numberOf10c=" + numberOf10c +
                ", numberOf20c=" + numberOf20c +
                ", numberOf50c=" + numberOf50c +
                ", numberOf1Dollar=" + numberOf1Dollar +
                '}';
    }
}
