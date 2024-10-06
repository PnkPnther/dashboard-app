package com.example.demo;
import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    static int selectedIndex;
    private double xOffset = 0;
    private double yOffset = 0;

    //VOLATILE KEYWORD IS USED TO MODIFY THE VALUE OF A VARIABLE BY DIFFERENT THREADS. IT IS ALSO USED TO MAKE CLASSES THREAD SAFE
    private volatile boolean stop = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //POPULATES DATE PICKER FIELD TO CURRENT DATE
        datePicked.setValue(LocalDate.now());
        //INITIALIZE METHOD CALLS timeNow() AND dateNow() METHODS TO DISPLAY CURRENT TIME AND DATE
        timeNow();
        dateNow();
    }
    //@FXML STATEMENTS TO BIND GUI COMPONENTS TO CONTROLLER METHODS
    @FXML
    Button addButton;
    @FXML
    Button deleteButton;
    @FXML
    TextField eventDescription;
    @FXML
    DatePicker datePicked;
    @FXML
    ListView<LocalEvent> eventList;
    @FXML
    Label stockPriceLabel;
    @FXML
    Button enterTickerButton;
    @FXML
    TextField tickerInput;
    @FXML
    Label time;
    @FXML
    Label date;
    @FXML
    Button closeButton;
    @FXML
    Button minButton;
    @FXML
    Pane topPane;

    //PARAM. E FOR EVENT IS ONLY THERE FOR PROGRAM TO COMPILE, PARAM. E IS NOT USED

    //CLOSE BUTTON FUNCTION
    public void handleCloseButton(Event e) {
        //STOPS TIME THREAD
        stop = true;
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    //MINIMIZE BUTTON FUNCTION
    public void handleMinButton(Event e) {
        Stage stage = (Stage) minButton.getScene().getWindow();
        stage.setIconified(true);
    }

    //REGISTERS CLICK ON TITLE BAR AND CALCULATES POSITION OF WINDOW
    public void handleClickAction(MouseEvent e) {
        Stage stage = (Stage) topPane.getScene().getWindow();
        xOffset = stage.getX() - e.getScreenX();
        yOffset = stage.getY() - e.getScreenY();
    }

    //MOVES WINDOW ONCE TITLE BAR IS CLICKED
    public void handleMovementAction(MouseEvent e) {
        Stage stage = (Stage) topPane.getScene().getWindow();
        stage.setX(e.getScreenX() + xOffset);
        stage.setY(e.getScreenY() +yOffset);
    }

    //METHOD GETS USER INPUT FOR STOCK TICKER FOR THE DESIRED STOCK QUOTE, THEN SENDS REQUESTED TICKER TO
    //"StockPrice.stockPrice" TO RECEIVE AND DISPLAY SCRAPED STOCK PRICE.
    String requestedTicker;
    String requestedTickerUpper;
    public void askStockPrice(Event e) throws IOException {
        requestedTicker = tickerInput.getText();
        //VARIABLE requestedTicker IS CONVERTED TO UPPERCASE TO HANDLE LOWERCASE ENTRIES
        requestedTickerUpper = requestedTicker.toUpperCase();
        String stockInfo = StockPrice.stockPrice(requestedTickerUpper);
        stockPriceLabel.setText(stockInfo);
    }

    //DISPLAYS AND UPDATES CURRENT TIME EVERY SECOND WITH THE USE OF A THREAD
    public void  timeNow() {
        Thread thread = new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            while(!stop) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String timeNow = sdf.format(new Date());
                Platform.runLater(()->{
                    time.setText(timeNow);
                });
            }
        });
        thread.start();
    }

    //DISPLAYS CURRENT DATE
    public void dateNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("'Today is: '\n EEEE, MMMM dd, yyyy");
        String dateNow = sdf.format(new Date());
        date.setText(dateNow);
    }

    //CREATES LIST FOR EVENTS
    ObservableList<LocalEvent> list = FXCollections.observableArrayList();

    //ADD FUNCTION FOR ADDING EVENT FROM LIST
    @FXML
    private void addEvent(Event e){
        list.add(new LocalEvent(datePicked.getValue(), eventDescription.getText()));
        eventList.setItems(list);
    }

    //GETS INDEX OF SELECTED EVENT ON GUI
    @FXML
    public int handleMouseSelect(MouseEvent e) {
        selectedIndex = eventList.getSelectionModel().getSelectedIndex();
        return selectedIndex;
    }

    //DELETE FUNCTION FOR REMOVING EVENT FROM LIST
    @FXML
    private void deleteEvent(Event e){
        list.remove(selectedIndex);
    }
}