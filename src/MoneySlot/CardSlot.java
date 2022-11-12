package MoneySlot;

public class CardSlot {
    private CardBalance cardBalance;

    public CardSlot( ) {
        this.cardBalance = new CardBalance();
    }

    public CardBalance getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(CardBalance cardBalance) {
        this.cardBalance = cardBalance;
    }
}
