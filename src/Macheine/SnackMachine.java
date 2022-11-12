package Macheine;

import Items.Item;
import Items.Snack;
import Money.Money;
import Money.Coin;
import Money.Note;
import MoneySlot.MoneySlot;
import MoneySlot.NoteBalance;
import MoneySlot.CoinBalance;
import MoneySlot.CardBalance;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SnackMachine implements Operations {
    private MoneySlot currentBalance;
    private double change;
    private MoneySlot moneySlot;
    private Stack<Snack>[][] snacks;
    private  Coin coin;

    private SnackMachine(MoneySlot currentBalance,MoneySlot moneySlot){

        this.currentBalance = currentBalance;
        this.currentBalance.calculateBalanceInUSD();
        this.moneySlot= moneySlot;
        this.change = 0.0;
        this.snacks = new Stack[5][5];
        for(int row = 0 ; row <5 ; row++){
            for(int col =0 ; col<5 ; col++){
                snacks[row][col] = new Stack<Snack>();
            }
        }
        try {
            this.refillStock();
        } catch (IOException e) {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setContentText("Sorry , invalid Snack File"+"\n"
            +e.getMessage());
            failureAlert.showAndWait();
            if(failureAlert.getResult().equals(ButtonType.OK)){
                System.exit(1);
            }

        }
        try {
            this.refillBalance();
        } catch (IOException e) {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setContentText("Sorry , invalid Money File"+"\n"
                    +e.getMessage());
            failureAlert.showAndWait();
            if(failureAlert.getResult().equals(ButtonType.OK)){
                System.exit(1);
            }
        }

    }
   static SnackMachine snackMachine;
    public static SnackMachine getInstance(MoneySlot moneySlot ,MoneySlot moneyslot){
        if(snackMachine==null){
            snackMachine = new SnackMachine(moneySlot,moneyslot);
        }
        return snackMachine;
    }
    public Stack<Snack>[][] getSnacks() {
        return snacks;
    }

    public void setSnacks(Stack<Snack>[][] snacks) {
        this.snacks = snacks;
    }

    public MoneySlot getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(MoneySlot currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public MoneySlot getMoneySlot() {
        return moneySlot;
    }

    public void setMoneySlot(MoneySlot moneySlot) {
        this.moneySlot = moneySlot;
    }
    public boolean validateChange(){
        if(currentBalance.getBalanceInUSD() >= this.change && this.getChange()>0){
            double remainingChange = this.getChange();
            MoneySlot tempMoney = new MoneySlot("USD");
            tempMoney.getCoinSlot().getCoinBalance().setNumberOf10c(currentBalance.getCoinSlot().getCoinBalance().getNumberOf10c());
            tempMoney.getCoinSlot().getCoinBalance().setNumberOf20c(currentBalance.getCoinSlot().getCoinBalance().getNumberOf20c());
            tempMoney.getCoinSlot().getCoinBalance().setNumberOf50c(currentBalance.getCoinSlot().getCoinBalance().getNumberOf50c());
            tempMoney.getCoinSlot().getCoinBalance().setNumberOf1Dollar(currentBalance.getCoinSlot().getCoinBalance().getNumberOf1Dollar());
            tempMoney.getNoteSlot().getNoteBalance().setNumOf50Doller(currentBalance.getNoteSlot().getNoteBalance().getNumOf50Doller());
            tempMoney.getNoteSlot().getNoteBalance().setNumOf20Doller(currentBalance.getNoteSlot().getNoteBalance().getNumOf20Doller());
            NoteBalance currentNoteBalance = tempMoney.getNoteSlot().getNoteBalance();
            CoinBalance currentCoinBalance = tempMoney.getCoinSlot().getCoinBalance();
            int numberOfUnitsToBeRemoved = 0;
            double valueOfUnitsToBeRemoved = 0.0;
            System.out.println(currentCoinBalance.getNumberOf1Dollar());
            numberOfUnitsToBeRemoved = (int) (remainingChange / 50.0);
            if(currentNoteBalance.getNumOf50Doller()>=numberOfUnitsToBeRemoved){
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved*50;
                currentNoteBalance.setNumOf50Doller(currentNoteBalance.getNumOf50Doller()-numberOfUnitsToBeRemoved);
                remainingChange -=valueOfUnitsToBeRemoved;
            }else if(numberOfUnitsToBeRemoved > currentNoteBalance.getNumOf50Doller()){
                valueOfUnitsToBeRemoved = currentNoteBalance.getNumOf50Doller() * 50.0;
                currentNoteBalance.setNumOf50Doller(0);
                remainingChange -= valueOfUnitsToBeRemoved;
            }
            //////////////////////////////////////////////////////////////////////////////
            numberOfUnitsToBeRemoved = (int) (remainingChange / 20.0);
            if(currentNoteBalance.getNumOf20Doller()>= numberOfUnitsToBeRemoved){
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved *20;
                currentNoteBalance.setNumOf20Doller(currentNoteBalance.getNumOf20Doller()- numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            }else if (numberOfUnitsToBeRemoved > currentNoteBalance.getNumOf20Doller()) {
                valueOfUnitsToBeRemoved = currentNoteBalance.getNumOf20Doller() * 20.0;
                currentNoteBalance.setNumOf20Doller(0);
                remainingChange -= valueOfUnitsToBeRemoved;
            }
            ///////////////////////////////////////////////////////////////
            numberOfUnitsToBeRemoved = (int) (remainingChange / 1.0);
            if (currentCoinBalance.getNumberOf1Dollar() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 1.0;
                currentCoinBalance.setNumberOf1Dollar(currentCoinBalance.getNumberOf1Dollar() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentCoinBalance.getNumberOf1Dollar()) {
                valueOfUnitsToBeRemoved = currentCoinBalance.getNumberOf1Dollar() * 1.0;
                currentCoinBalance.setNumberOf1Dollar(0);
                remainingChange -= valueOfUnitsToBeRemoved;

            }
            ///////////////////////////////////////////////////////////////
            numberOfUnitsToBeRemoved = (int) (remainingChange / 0.5);
            if (currentCoinBalance.getNumberOf50c() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 0.5;
                currentCoinBalance.setNumberOf50c(currentCoinBalance.getNumberOf50c() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentCoinBalance.getNumberOf50c()) {
                valueOfUnitsToBeRemoved = currentCoinBalance.getNumberOf50c() * 0.5;
                currentCoinBalance.setNumberOf50c(0);
                remainingChange -= valueOfUnitsToBeRemoved;

            }

            //-----------------------------------------------
            numberOfUnitsToBeRemoved = (int) (remainingChange / 0.2);
            if (currentCoinBalance.getNumberOf20c() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 0.2;
                currentCoinBalance.setNumberOf20c(currentCoinBalance.getNumberOf20c() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentCoinBalance.getNumberOf20c()) {
                valueOfUnitsToBeRemoved = currentCoinBalance.getNumberOf20c() * 0.2;
                currentCoinBalance.setNumberOf20c(0);
                remainingChange -= valueOfUnitsToBeRemoved;

            }

            //-----------------------------------------------
            numberOfUnitsToBeRemoved = (int) (remainingChange / 0.1);
            if (currentCoinBalance.getNumberOf10c() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 0.1;
                currentCoinBalance.setNumberOf10c(currentCoinBalance.getNumberOf10c() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentCoinBalance.getNumberOf10c()) {
                valueOfUnitsToBeRemoved = currentCoinBalance.getNumberOf10c() * 0.1;
                currentCoinBalance.setNumberOf10c(0);
                remainingChange -= valueOfUnitsToBeRemoved;
            }
            if(remainingChange ==0.0){
                this.currentBalance = tempMoney;
                this.currentBalance.calculateBalanceInUSD();
                this.setChange(0.0);
                tempMoney.calculateBalanceInUSD();
                return true;
            }
        }
        if(this.getChange()==0){
            return true;
        }
        return false;
    }
    public void returnMoney() {
        this.setChange(0.0);
        NoteBalance currentNoteBalance = this.getCurrentBalance().getNoteSlot().getNoteBalance();
        CoinBalance currentCoinBalance = this.getCurrentBalance().getCoinSlot().getCoinBalance();
        NoteBalance toBeCanceledNotes = this.getMoneySlot().getNoteSlot().getNoteBalance();
        CoinBalance toBeCanceledCoins = this.getMoneySlot().getCoinSlot().getCoinBalance();
        if (currentNoteBalance.getNumOf20Doller() >= toBeCanceledNotes.getNumOf20Doller())
            currentNoteBalance.setNumOf20Doller(currentNoteBalance.getNumOf20Doller() - toBeCanceledNotes.getNumOf20Doller());
        if (currentNoteBalance.getNumOf50Doller() >= toBeCanceledNotes.getNumOf50Doller())
            currentNoteBalance.setNumOf50Doller(currentNoteBalance.getNumOf50Doller() - toBeCanceledNotes.getNumOf50Doller());
        if (currentCoinBalance.getNumberOf1Dollar() > toBeCanceledCoins.getNumberOf1Dollar())
            currentCoinBalance.setNumberOf1Dollar(currentCoinBalance.getNumberOf1Dollar() - toBeCanceledCoins.getNumberOf1Dollar());
        if (currentCoinBalance.getNumberOf50c() > toBeCanceledCoins.getNumberOf50c())
            currentCoinBalance.setNumberOf50c(currentCoinBalance.getNumberOf50c() - toBeCanceledCoins.getNumberOf50c());
        if (currentCoinBalance.getNumberOf20c() > toBeCanceledCoins.getNumberOf20c())
            currentCoinBalance.setNumberOf20c(currentCoinBalance.getNumberOf20c() - toBeCanceledCoins.getNumberOf20c());
        if (currentCoinBalance.getNumberOf10c() > toBeCanceledCoins.getNumberOf10c())
            currentCoinBalance.setNumberOf10c(currentCoinBalance.getNumberOf10c() - toBeCanceledCoins.getNumberOf10c());
        this.clearMoneySlot();
        this.currentBalance.calculateBalanceInUSD();
        this.moneySlot.calculateBalanceInUSD();
    }
    public void clearMoneySlot() {
        NoteBalance toBeCanceledNotes = this.getMoneySlot().getNoteSlot().getNoteBalance();
        CoinBalance toBeCanceledCoins = this.getMoneySlot().getCoinSlot().getCoinBalance();
        toBeCanceledNotes.setNumOf50Doller(0);
        toBeCanceledNotes.setNumOf20Doller(0);
        toBeCanceledCoins.setNumberOf1Dollar(0);
        toBeCanceledCoins.setNumberOf50c(0);
        toBeCanceledCoins.setNumberOf20c(0);
        toBeCanceledCoins.setNumberOf10c(0);
    }
    public void clearCardSLot()
    {
        CardBalance toBeCanceledCard = this.getMoneySlot().getCardSLot().getCardBalance();
        toBeCanceledCard.setMoneyFromCard(0.0);
    }


    @Override
    public void refillStock() throws IOException {
        File snaksFile = new File("snaks.txt");
        BufferedReader br = new BufferedReader(new FileReader(snaksFile));
        String line;
        int cont=0;
        int rowIndex = 0;
        int colIndex = 0;
        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            String name = info[0];

            double purchasingPrice = Double.parseDouble(info[1]);
            double sellingPrice = Double.parseDouble(info[2]);
            int availableItems = Integer.parseInt(info[3]);
            Snack currentSnaks = new Snack(name, purchasingPrice, sellingPrice, availableItems);
            cont++;
            for (int i = 0; i < currentSnaks.getAvailableItem(); i++) {
                snacks[rowIndex][colIndex].push(currentSnaks);
            }
            colIndex++;
            if (colIndex == 5) {
                if (rowIndex < 5) {
                    rowIndex++;
                    colIndex = 0;
                }

            }
        }

    }

    @Override
    public void refillBalance() throws IOException {
        File snaksFile = new File("mony.txt");
        BufferedReader br = new BufferedReader(new FileReader(snaksFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data[1].trim().charAt(0) == 'c' || Integer.parseInt(data[2].trim()) == 1) {
                Coin newCoin = new Coin(Integer.parseInt(data[2].trim()),data[0].trim(), data[1].trim().charAt(0));
                    if (newCoin.getCurrency().equalsIgnoreCase("USD"))
                    this.getCurrentBalance().UpdateBalance(newCoin);
                else
                    System.out.println("USD only");
            } else {
                Note newNote = new Note(Integer.parseInt(data[2].trim()),data[0].trim(), data[1].trim().charAt(0));
                if (newNote.getCurrency().equalsIgnoreCase("USD"))
                    this.getCurrentBalance().UpdateBalance(newNote);
                else
                    System.out.println("USD only");
            }

        }
    }
    @Override
    public boolean purchase(int rowIndex, int colIndex) {

        if(validateChange()){
            snacks[rowIndex][colIndex].peek().setAvailableItem(snacks[rowIndex][colIndex].peek().getAvailableItem()-1);
            snacks[rowIndex][colIndex].pop();
            this.clearMoneySlot();
            return true;
        }
        return false;
    }

    @Override
    public boolean purchaseWithCard(int rowIndex, int colIndex) {
        this.getMoneySlot().getCardSLot().getCardBalance().setMoneyFromCard(this.getSnacks()[rowIndex][colIndex].peek().getSeelingPric());
        snacks[rowIndex][colIndex].peek().setAvailableItem(snacks[rowIndex][colIndex].peek().getAvailableItem()-1);
        snacks[rowIndex][colIndex].pop();
        this.setChange(0.0);
        this.getCurrentBalance().getCardSLot().getCardBalance().setMoneyFromCard(this.getCurrentBalance().getCardSLot().getCardBalance().getMoneyFromCard() + this.getMoneySlot().getCardSLot().getCardBalance().getMoneyFromCard());
        this.getMoneySlot().calculateBalanceInUSD();
        this.getCurrentBalance().calculateBalanceInUSD();
        this.clearCardSLot();
        return true;
    }

    @Override
    public void calculateChange(Item item) {
        if(this.getMoneySlot().getBalanceInUSD()>item.getSeelingPric()){
            this.setChange(this.getMoneySlot().getBalanceInUSD()-item.getSeelingPric());
        }
    }



}
