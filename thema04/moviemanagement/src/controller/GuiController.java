package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.MainApp;
import model.MovieManagement;
import model.object.Movie;
import model.object.Watchlist;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class GuiController {

    private MainApp app;
    private MovieManagement mgmt;
    private String currentWatchlist;

    @FXML private GridPane movieGridPane, watchlistGridPane;
    @FXML private Button editWatchlistButton, editMovieButton;
    @FXML private ListView<String> watchlistListView, movieListView;
    @FXML private ImageView movieImageView;
    @FXML private Label movieNameLabel, movieDirectorLabel, movieCompanyLabel, movieCastLabel, movieYearLabel,
            movieRunningTimeLabel, watchlistNameLabel, watchlistGenreLabel, watchlistDescriptionLabel;

    public void setup(MainApp app, MovieManagement mgmt) {
        this.app = app;
        this.mgmt = mgmt;
        initializeListView();
    }

    public void initializeListView() {
        updateWatchlistListView();

        // create list view click event for watchlist
        watchlistListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null)
                    try {
                        currentWatchlist = newValue;
                        updateMovieListView();
                    } catch (Exception e) {
                        app.closeApplication(e);
                    }
            }
        });

        // create list view click event for movie
        movieListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null)
                    try {
                        Movie movie = mgmt.getMovie(newValue);

                        movieNameLabel.setText(movie.getTitle());
                        movieDirectorLabel.setText(movie.getDirector());
                        movieCastLabel.setText(movie.getCast());
                        movieCompanyLabel.setText(movie.getCompany());
                        movieYearLabel.setText(String.valueOf(movie.getYear()));
                        movieRunningTimeLabel.setText(movie.getRunningTime() + " Minutes");

                        if (movie.getImage() != null) {
                            try (InputStream in = movie.getImage().getBinaryStream()) {
                                movieImageView.setImage(new Image(in));
                                movieImageView.setVisible(true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // if no image is available => hide image view
                            movieImageView.setVisible(false);
                        }
                        // enable movie edit button
                        editMovieButton.setDisable(false);
                    } catch (Exception e) {
                        app.closeApplication(e);
                    }
                else
                    // disable movie edit button
                    editMovieButton.setDisable(true);
            }
        });

        // movie grid + movie edit button visible property => only show when movie is selected
        movieGridPane.visibleProperty().bind(movieNameLabel.textProperty().greaterThan(""));
        editMovieButton.visibleProperty().bind(movieNameLabel.textProperty().greaterThan(""));
        editWatchlistButton.visibleProperty().bind(watchlistNameLabel.textProperty().greaterThan(""));

    }

    public void updateMovieListView() {
        try {
            // get watchlist
            Watchlist watchlist = mgmt.getWatchlist(currentWatchlist);

            // insert attributes into labels
            watchlistNameLabel.setText(watchlist.getTitle());
            watchlistDescriptionLabel.setText(watchlist.getDescription());
            watchlistGenreLabel.setText(watchlist.getGenre());

            // insert movies into movies list view
            movieListView.getItems().clear(); // clear old values
            for (Movie movie : watchlist.getMovies().values())
                movieListView.getItems().add(movie.getTitle()); // add movie name to list

            // show javafx elements
            movieListView.setVisible(true);
            watchlistGridPane.setVisible(true);
        } catch (Exception e) {
            app.closeApplication(e);
        }
    }

    public void updateWatchlistListView() {
        try {
            // remove all items from watchlist list view
            watchlistListView.getItems().clear();

            // get all watchlists
            Collection<Watchlist> watchlists = mgmt.getAllWatchlist();

            // insert all watchlist entries to watchlist list view
            for (Watchlist watchlist : watchlists)
                watchlistListView.getItems().add(watchlist.getTitle());
        } catch (Exception e) {
            app.closeApplication(e);
        }
    }

    @FXML void onMovieAddPressed(ActionEvent event) {
        try {
            app.openMovieWindow(null);
        } catch (IOException e) {
            app.closeApplication(e);
        }
    }

    @FXML void onMovieEditPressed(ActionEvent event) {
        try {
            String selectedItem = movieListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null)
                app.openMovieWindow(mgmt.getMovie(selectedItem));
        } catch (Exception e) {
            app.closeApplication(e);
        }
    }

    @FXML void onWatchlistPressed(ActionEvent event) {
        try {
            app.openWatchlistWindow(null);
        } catch (IOException e) {
            app.closeApplication(e);
        }
    }

    @FXML void onWatchlistEditPressed(ActionEvent event) {
        try {
            String selectedItem = watchlistListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null)
                app.openWatchlistWindow(mgmt.getWatchlist(selectedItem));
        } catch (Exception e) {
            app.closeApplication(e);
        }
    }

    public void onMovieWindowClosed() {
        if (currentWatchlist != null) updateMovieListView();
    }

    public void onWatchlistWindowClosed() {
        updateWatchlistListView();

        movieNameLabel.setText("");
        watchlistNameLabel.setText("");
        movieListView.setVisible(false);
        watchlistGridPane.setVisible(false);
        movieImageView.setVisible(false);
    }
}
