package Items;

public class Snack extends Item{

    public Snack(String name , double purchasingPrice, double seelingPrice  ,int availableItem) {
        super(name , purchasingPrice , seelingPrice,availableItem);

    }

    @Override
    public void calculateProfit(double purchasingPrice, double sellingPrice) {
        this.setProfit(purchasingPrice-sellingPrice);
    }

}
