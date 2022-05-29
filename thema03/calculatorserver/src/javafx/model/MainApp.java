package javafx.model;

import javafx.application.Application;
import javafx.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private SocketHandler socketHandler;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            socketHandler = new SocketHandler("127.0.0.1", 2000); // open socket (e-handling!)

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("../view/gui.fxml"));
            stage.setTitle("4301_CalculatorServer");
            stage.setScene(new Scene(loader.load()));
            ((Controller) loader.getController()).setSocketHandler(socketHandler);
            stage.setResizable(false);
            stage.setOnCloseRequest(e -> socketHandler.close()); // send quit command to server und close socket + streams
            stage.show();
        } catch (IOException e) {
            System.out.println("Couldn't open calculator... Please start the server first!");
        }
    }
}
