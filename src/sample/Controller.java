package sample;
import Items.Item;
import Items.Snack;
import Macheine.SnackMachine;
import Money.Coin;
import Money.Money;
import Money.Note;
import MoneySlot.MoneySlot;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class Controller implements Initializable {
    public static SnackMachine snackMachine;
    @FXML
    GridPane grid;
    @FXML
    RadioButton CASH, VISA;
    @FXML
    HBox MoneyButton;
    int selectedRow, selectedCol;
    @FXML
    TextField Price;
    @FXML
    TextField totalCash;
    @FXML
     TextField change;
    @FXML
    Button confirm;
    DecimalFormat df = new DecimalFormat("###.##");

    @Override
public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup paymentGroup = new ToggleGroup();
        CASH.setToggleGroup(paymentGroup);
        VISA.setToggleGroup(paymentGroup);
        snackMachine =SnackMachine.getInstance(new MoneySlot("USD"),new MoneySlot("USD"));
        System.out.println(snackMachine.getCurrentBalance().getBalanceInUSD());
        this.setItemINGrid();
    }
        public void setItemINGrid(){
                for(int row=0; row<5 ;row++){
                    for(int col = 0 ; col<5 ;col++){
                        if(snackMachine.getSnacks()[row][col].size()!=0)
                        grid.add(createCustomizeLabel(snackMachine.getSnacks()[row][col].peek()),row,col);
                    }
                }
        }
    public VBox createCustomizeLabel(Item item) {
        VBox container = new VBox();
        Label itemName = new Label(item.getName());
        Label itemPrice = new Label("Selling Price "+String.valueOf(item.getSeelingPric()));
        Label remainingUnits = new Label("Available Item  "+String.valueOf(item.getAvailableItem()));
        container.getChildren().addAll(itemName, itemPrice, remainingUnits);
        container.setSpacing(10.0);
        container.setAlignment(Pos.CENTER);
        container.setBorder(new Border(new BorderStroke(Color.AQUA, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        container.setId("unSelected");
        container.setOnMouseClicked(eventHandler());
        return container;

    }
    EventHandler<? super MouseEvent> eventHandler() {
        return event -> {
            VBox prevSelected = (VBox) grid.lookup("#selected");
            if (prevSelected != null) {
                prevSelected.setId("unSelected");
                prevSelected.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }

            try {
                Node node = (Node) event.getTarget();
                selectedRow = grid.getRowIndex(node);
                selectedCol = grid.getColumnIndex(node);
                ((VBox) node).setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5, 5, 5, 5))));
                ((VBox) node).setId("selected");
                CASH.setDisable(false);
                VISA.setDisable(false);
                this.update();
            }catch  (NullPointerException e) {
                System.out.println("Click The box itself");
                this.cancel();
            }

        };

    }
    public void money(Event event){
        Object node = event.getTarget();
        Button button = (Button) node;
        String []data = button.getText().split(" ");
        if(data[1].charAt(0)== 'c' || Integer.parseInt(data[0].trim())==1){
            Coin coin = new Coin(Integer.parseInt(data[0].trim()),"USD", data[1].charAt(0));
            snackMachine.getMoneySlot().UpdateBalance(coin);
            snackMachine.getCurrentBalance().UpdateBalance(coin);
        }else {
            Note note = new Note(Integer.parseInt(data[0].trim()) ,"USD", data[1].charAt(0));
            snackMachine.getMoneySlot().UpdateBalance(note);
            snackMachine.getCurrentBalance().UpdateBalance(note);
        }
        this.update();
    }
    public void update(){
        if(snackMachine.getSnacks()[selectedCol][selectedRow].size()!=0){
            Price.setText("PRICE IS "+snackMachine.getSnacks()[selectedCol][selectedRow].peek().getSeelingPric()+"\n");
            totalCash.setText(String.valueOf(df.format(snackMachine.getMoneySlot().getBalanceInUSD())));
            snackMachine.calculateChange(snackMachine.getSnacks()[selectedCol][selectedRow].peek());
            snackMachine.getMoneySlot().calculateBalanceInUSD();
            snackMachine.getCurrentBalance().calculateBalanceInUSD();
            change.setText(String.valueOf(df.format(snackMachine.getChange())));
            if(snackMachine.getSnacks()[selectedCol][selectedRow].peek().getSeelingPric()<=snackMachine.getMoneySlot().getBalanceInUSD()){
                confirm.setDisable(false);
            }
        }
    }
    public void cancel() {
        snackMachine.returnMoney();
        this.clear();
    }
    public void paymentMethod(){
        if(snackMachine.getSnacks()[selectedCol][selectedRow].peek().getSeelingPric()<=snackMachine.getMoneySlot().getBalanceInUSD()){
            confirm.setDisable(false);
        }
        if(CASH.isSelected()){
        MoneyButton.setDisable(false);
        }else if(VISA.isSelected()){
            MoneyButton.setDisable(true);
            totalCash.setText(String.valueOf(snackMachine.getSnacks()[selectedCol][selectedRow].peek().getSeelingPric()));
            change.setText(String.valueOf(0));
            MoneyButton.setDisable(true);
            confirm.setDisable(false);
        }
    }
    public void toConfirm(){
        if(CASH.isSelected()){
            if(snackMachine.purchase(selectedCol,selectedRow)){
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setContentText("Please take your snack and change");
                this.update();
                successAlert.show();
                grid.getChildren().clear();
                this.setItemINGrid();
                this.clear();
            } else {
                Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                failureAlert.setContentText("Sorry, SNACKERS cannot serve you with this order. We don't have enough variance of change");
                failureAlert.show();
                this.clear();
                this.cancel();
            }

        }else if (VISA.isSelected()) {

            if (snackMachine.purchaseWithCard(selectedCol,selectedRow)) {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setContentText("Please take your snack fees charged successfully of your card");
                this.update();
                successAlert.show();
                grid.getChildren().clear();
                this.clear();

            }
        }
    }
    public void clear(){
        Price.clear();
        snackMachine.setChange(0.0);
        totalCash.clear();
        change.clear();
        CASH.setSelected(false);
        CASH.setDisable(true);
        VISA.setSelected(false);
        VISA.setDisable(true);
        MoneyButton.setDisable(true);
        confirm.setDisable(true);
        this.setItemINGrid();
    }
}
