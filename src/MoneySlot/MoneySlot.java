package MoneySlot;

import Money.Coin;
import Money.Money;
import Money.Note;
public class MoneySlot {
    private String currency;
    private CoinSlot coinSlot;
    private NoteSlot noteSlot;
    private CardSlot cardSLot;
    private double balanceInUSD;

    public MoneySlot(String currency) {
        this.currency=currency;
        this.coinSlot = new CoinSlot();
        this.noteSlot = new NoteSlot();
        this.cardSLot = new CardSlot();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public CoinSlot getCoinSlot() {
        return coinSlot;
    }

    public void setCoinSlot(CoinSlot coinSlot) {
        this.coinSlot = coinSlot;
    }

    public NoteSlot getNoteSlot() {
        return noteSlot;
    }

    public void setNoteSlot(NoteSlot noteSlot) {
        this.noteSlot = noteSlot;
    }

    public CardSlot getCardSLot() {
        return cardSLot;
    }

    public void setCardSLot(CardSlot cardSLot) {
        this.cardSLot = cardSLot;
    }

    public double getBalanceInUSD() {
        return balanceInUSD;
    }

    public void setBalanceInUSD(double balanceInUSD) {
        this.balanceInUSD = balanceInUSD;
    }
    public void UpdateBalance(Money mony){
        if(mony instanceof Coin){
            Coin c = (Coin)mony;
            this.coinSlot.updateCoin(c);
        }else if(mony instanceof Note){
            Note note = (Note)mony;
            this.noteSlot.UpdateNote(note);
        }
        this.calculateBalanceInUSD();
    }
        public void calculateBalanceInUSD(){
        double noteSum = ((this.noteSlot.getNoteBalance().getNumOf20Doller()*20)+
                (this.noteSlot.getNoteBalance().getNumOf50Doller()*50));

        double coinSum = ((this.coinSlot.getCoinBalance().getNumberOf10c()*0.1)+
                (this.coinSlot.getCoinBalance().getNumberOf20c()*0.2)
                +(this.coinSlot.getCoinBalance().getNumberOf50c()*0.5)
                +(this.coinSlot.getCoinBalance().getNumberOf1Dollar()*1)
                );
        this.setBalanceInUSD(noteSum+coinSum+this.getCardSLot().getCardBalance().getMoneyFromCard());
    }
}
