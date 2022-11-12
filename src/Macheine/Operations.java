package Macheine;

import Items.Item;
import Money.Money;

import java.io.IOException;

public interface Operations {
    public abstract boolean purchase(int rowIndex, int colIndex);

    public abstract boolean purchaseWithCard(int rowIndex, int colIndex);

    public abstract void calculateChange(Item item);

    public abstract boolean validateChange();

    public void refillStock() throws IOException;

    public void refillBalance() throws IOException;
}
