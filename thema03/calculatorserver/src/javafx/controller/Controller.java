package javafx.controller;

import common.CalculatorTask;
import common.MathematicalOperation;
import common.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.model.ClientService;
import javafx.model.SocketHandler;
import javafx.scene.control.Label;

public class Controller {

    private ClientService service;
    @FXML private Label resultLabel;

    public void setSocketHandler(SocketHandler handler) {
        this.service = handler.getService();

        // set on success event --> called when result was sent from server
        service.setOnSucceeded(event -> {
            CalculatorTask task = service.getValue(); // get result from other thread
            if (task.getOperation() == MathematicalOperation.QUIT) // check if the exit operation is returned
                Platform.exit(); // if quit accepted by server, close programm
            else
                resultLabel.setText(String.valueOf(task.getResult())); // set the result from server into label
        });
    }

    @FXML void pressedResult(ActionEvent event) {
        String[] input = resultLabel.getText().split(" ");

        CalculatorTask task = new CalculatorTask(
                Double.parseDouble(input[0]), Double.parseDouble(input[2]),
                Utils.getOperationByString(input[1])
        );

        // set task and restart service
        service.reset();
        service.setTask(task);
        service.start();
    }

    @FXML void pressedCancel(ActionEvent event) {
        resultLabel.setText("");
    }

    @FXML void pressedComma(ActionEvent event) {
        appendText(".");
    }

    @FXML void pressedDelete(ActionEvent event) {
        resultLabel.setText(resultLabel.getText().substring(0, resultLabel.getText().length() - 1));
    }

    @FXML void pressedDivision(ActionEvent event) {
        appendText(" / ");
    }

    @FXML void pressedEight(ActionEvent event) {
        appendText("8");
    }

    @FXML void pressedFive(ActionEvent event) {
        appendText("5");
    }

    @FXML void pressedFour(ActionEvent event) {
        appendText("4");
    }

    @FXML void pressedMinus(ActionEvent event) {
        appendText(" - ");
    }

    @FXML void pressedNine(ActionEvent event) {
        appendText("9");
    }

    @FXML void pressedOne(ActionEvent event) {
        appendText("1");
    }

    @FXML void pressedPlus(ActionEvent event) {
        appendText(" + ");
    }

    @FXML void pressedSeven(ActionEvent event) {
        appendText("7");
    }

    @FXML void pressedSix(ActionEvent event) {
        appendText("6");
    }

    @FXML void pressedThree(ActionEvent event) {
        appendText("3");
    }

    @FXML void pressedTimes(ActionEvent event) {
        appendText(" * ");
    }

    @FXML void pressedTwo(ActionEvent event) {
        appendText("2");
    }

    @FXML void pressedZero(ActionEvent event) {
        appendText("0");
    }

    @FXML public void pressedNumberMinus(ActionEvent actionEvent) {
        appendText("-");
    }

    private void appendText(String s) {
        resultLabel.setText(resultLabel.getText() + s);
    }
}
