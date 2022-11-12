package MoneySlot;

import Money.Coin;

public class CoinSlot {
        private CoinBalance coinBalance;

    public CoinSlot() {
        this.coinBalance = new CoinBalance();
    }

    public void setCoinBalance(CoinBalance coinBalance) {
        this.coinBalance = coinBalance;
    }

    public CoinBalance getCoinBalance() {
        return coinBalance;
    }
    public void updateCoin(Coin coin){
        if(coin.getCategory()=='c'){
            if(coin.getCoinValue() == 10){
                this.coinBalance.setNumberOf10c(this.getCoinBalance().getNumberOf10c()+1);
            }else if(coin.getCoinValue() == 20){
                this.coinBalance.setNumberOf20c(this.getCoinBalance().getNumberOf20c()+1);
            }else if(coin.getCoinValue()==50){
                this.coinBalance.setNumberOf50c(this.getCoinBalance().getNumberOf50c()+1);
            }
        }else{
            this.coinBalance.setNumberOf1Dollar(this.getCoinBalance().getNumberOf1Dollar()+1);
        }
    }
}
