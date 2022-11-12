package MoneySlot;

public class CardBalance {
    private double moneyFromCard;
    public CardBalance(){
        this.moneyFromCard=0.0;
    }

    public double getMoneyFromCard() {
        return moneyFromCard;
    }

    public void setMoneyFromCard(double moneyFromCard) {
        this.moneyFromCard = moneyFromCard;
    }

    @Override
    public String toString() {
        return "CardBalance{" +
                "moneyFromCard=" + moneyFromCard +
                '}';
    }
}
