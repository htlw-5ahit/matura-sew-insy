package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.MainApp;
import model.MovieManagement;
import model.database.ObjectStatus;
import model.object.Movie;
import model.object.Watchlist;

import java.util.Collection;

public class WatchlistController {

    private MainApp app;
    private MovieManagement mgmt;
    private Watchlist watchlist;
    private boolean isNewlyCreated;

    @FXML private TextField titleField, genreField, descriptionField;
    @FXML private Button closeButton, exitButton, deleteButton, moveLeftButton, moveRightButton;
    @FXML private ListView<String> allMoviesListView, selectedMoviesListView;
    private String currentAllMoviesList, currentSelectedMoviesList;

    public void setup(MainApp app, MovieManagement mgmt, Watchlist watchlist) {
        this.app = app;
        this.mgmt = mgmt;
        this.watchlist = watchlist;

        initializeListView();

        if (this.watchlist == null) { // if watchlist is null => generate empty one
            this.watchlist = new Watchlist();
            this.isNewlyCreated = true;
        } else { // if movie exists => set values into text fields
            this.isNewlyCreated = false;
            titleField.setText(watchlist.getTitle());
            genreField.setText(watchlist.getGenre());
            descriptionField.setText(watchlist.getDescription());

            for (Movie movie : watchlist.getMovies().values()) {
                allMoviesListView.getItems().remove(movie.getTitle());
                selectedMoviesListView.getItems().add(movie.getTitle());
            }

            // enable delete button
            deleteButton.setVisible(true);
        }

        // initialize button as disabled
        moveRightButton.setDisable(true);
        moveLeftButton.setDisable(true);

        allMoviesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentAllMoviesList = newValue;
                moveRightButton.setDisable(newValue == null);
            }
        });
        selectedMoviesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentSelectedMoviesList = newValue;
                moveLeftButton.setDisable(newValue == null);
            }
        });
    }

    public void initializeListView() {
        try {
            Collection<Movie> movies = mgmt.getAllMovies();
            for (Movie movie : movies)
                allMoviesListView.getItems().add(movie.getTitle());
        } catch (Exception e) {
            app.closeApplication(e);
        }
    }

    @FXML void onClosePressed(ActionEvent event) {
        try {
            // set values from gui to movie object
            watchlist.setTitle(titleField.getText());
            watchlist.setGenre(genreField.getText());
            watchlist.setDescription(descriptionField.getText());

            // remove all movies from watchlist object, to add afterwards selected
            watchlist.getMovies().clear();

            // add movies from selected list view to watchlist object
            int i = 0;
            for (String item : selectedMoviesListView.getItems())
                watchlist.getMovies().put(i++, mgmt.getMovie(item));

            // change movie object status
            watchlist.setObjectStatus(isNewlyCreated ? ObjectStatus.NEWLY_CREATED : ObjectStatus.UPDATED);

            // insert movie
            mgmt.insertWatchlist(watchlist);
        } catch (Exception e) {
            app.closeApplication(e);
        } finally {
            app.closeWatchlistWindow();
        }
    }

    @FXML void onExitPressed(ActionEvent event) {
        app.closeWatchlistWindow();
    }

    @FXML public void onMoveRightPressed(ActionEvent event) {
        String item = currentAllMoviesList; // save current item temporary because of update after remove item
        if (item != null) {
            allMoviesListView.getItems().remove(item);
            selectedMoviesListView.getItems().add(item);
        }
    }

    @FXML public void onMoveLeftPressed(ActionEvent event) {
        String item = currentSelectedMoviesList; // save current item temporary because of update after remove item
        if (item != null) {
            allMoviesListView.getItems().add(item);
            selectedMoviesListView.getItems().remove(item);
        }
    }

    @FXML void onDeletePressed(ActionEvent event) {
        try {
            mgmt.removeWatchlist(watchlist);
        } catch (Exception e) {
            app.closeApplication(e);
        } finally {
            app.closeWatchlistWindow();
        }
    }
}
