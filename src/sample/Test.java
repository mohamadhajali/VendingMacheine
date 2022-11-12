package sample;
import Items.Snack;
import Macheine.SnackMachine;
import Money.Note;
import MoneySlot.MoneySlot;
import org.junit.*;
public class Test {
    SnackMachine machine;
    int avilableItemBefore;
    int avilableItemAfter;
    Snack currentSnack;
    int row;
    int col;
    @org.junit.Before
    public void implementMachine(){
        machine = SnackMachine.getInstance(new MoneySlot("USD"),new MoneySlot("USD"));
    }
    @org.junit.Test
    public void purchaseCashTest(){
         row = 0;
         col = 0;
        Note note = new Note(20,"USD", '$');
        machine.getMoneySlot().UpdateBalance(note);
        machine.getCurrentBalance().UpdateBalance(note);
         currentSnack = machine.getSnacks()[col][row].peek();
        avilableItemBefore=machine.getSnacks()[col][row].peek().getAvailableItem();
        machine.calculateChange(currentSnack);
        machine.getMoneySlot().calculateBalanceInUSD();
        machine.getCurrentBalance().calculateBalanceInUSD();
        machine.getSnacks()[col][row].peek();
        System.out.println(machine.getSnacks()[col][row].peek().getAvailableItem());
        boolean result =machine.purchase(col,row);
        System.out.println(machine.getSnacks()[col][row].peek().getAvailableItem());
        Assert.assertTrue(result);
    }
    @org.junit.Test
    public void purchaseCardTest(){
        int row = (int)Math.floor(Math.random()*(4+1));
        int col = (int)Math.floor(Math.random()*(4+1));
        boolean result =machine.purchaseWithCard(col,row);
        Assert.assertTrue(result);
    }
    @org.junit.Test
    public void cheackSnack(){
        avilableItemAfter = machine.getSnacks()[col][row].peek().getAvailableItem();
        Assert.assertTrue(avilableItemAfter>avilableItemBefore);
    }

}
