package model;

import controller.GuiController;
import controller.MovieController;
import controller.WatchlistController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.object.Movie;
import model.object.Watchlist;

import java.io.IOException;

public class MainApp extends Application {

    private MovieManagement mgmt;
    private Stage movieStage, watchlistStage;
    private GuiController guiController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Open database connection!");
        mgmt = new MovieManagement("database.properties");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../view/gui.fxml"));
        stage.setTitle("Movie Management");
        stage.setScene(new Scene(loader.load()));
        guiController = (GuiController) loader.getController();
        guiController.setup(this, mgmt);
        stage.setResizable(false);
        stage.show();
    }

    public void openMovieWindow(Movie movie) throws IOException {
        movieStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../view/movie.fxml"));
        movieStage.setTitle("Movie Management");
        movieStage.setScene(new Scene(loader.load()));
        ((MovieController) loader.getController()).setup(this, mgmt, movie);
        movieStage.setResizable(false);
        movieStage.show();
    }

    public void openWatchlistWindow(Watchlist watchlist) throws IOException {
        watchlistStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../view/watchlist.fxml"));
        watchlistStage.setTitle("Movie Management");
        watchlistStage.setScene(new Scene(loader.load()));
        ((WatchlistController) loader.getController()).setup(this, mgmt, watchlist);
        watchlistStage.setResizable(false);
        watchlistStage.show();
    }

    public void closeMovieWindow() {
        if (movieStage != null) movieStage.close();
        guiController.onMovieWindowClosed();
    }

    public void closeWatchlistWindow() {
        if (watchlistStage != null) watchlistStage.close();
        guiController.onWatchlistWindowClosed();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Close database connection!");
        if (mgmt!= null) mgmt.close();
        super.stop();
    }

    public void closeApplication(Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "A system-critical error has occurred. Please inform your administrator!").showAndWait();
        Platform.exit();
    }

    public Stage getMovieStage() {
        return movieStage;
    }
}
