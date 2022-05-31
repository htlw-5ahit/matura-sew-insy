package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.MainApp;
import model.MovieManagement;
import model.database.ObjectStatus;
import model.object.Movie;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieController implements Initializable {

    private MainApp app;
    private MovieManagement mgmt;
    private Movie movie;
    private boolean isNewlyCreated;

    @FXML private TextField titleField, directorField, companyField, castField, yearField, runningTimeField;
    @FXML private Button fileButton, closeButton, exitButton, deleteButton;
    @FXML private Label fileLabel;
    @FXML private ImageView imageView;
    private FileChooser fileChooser;
    private File file;

    public void setup(MainApp app, MovieManagement mgmt, Movie movie) {
        this.app = app;
        this.mgmt = mgmt;
        this.movie = movie;

        if (this.movie == null) { // if movie is null => generate empty one
            this.movie = new Movie();
            this.isNewlyCreated = true;
        } else { // if movie exists => set values into text fields
            this.isNewlyCreated = false;
            titleField.setText(movie.getTitle());
            directorField.setText(movie.getDirector());
            companyField.setText(movie.getCompany());
            castField.setText(movie.getCast());
            yearField.setText(String.valueOf(movie.getYear()));
            runningTimeField.setText(String.valueOf(movie.getRunningTime()));

            if (movie.getImage() != null)
                try (InputStream in = movie.getImage().getBinaryStream()) {
                    imageView.setImage(new Image(in));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            // enable delete button
            deleteButton.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize file chooser
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        //TODO binding
    }

    @FXML void onFileChooserPressed(ActionEvent event) {
        file = fileChooser.showOpenDialog(app.getMovieStage());
        if (file != null) {
            fileLabel.setText(file.getName());
            imageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML void onClosePressed(ActionEvent event) {
        try {
            // set values from gui to movie object
            movie.setTitle(titleField.getText());
            movie.setDirector(directorField.getText());
            movie.setCompany(companyField.getText());
            movie.setCast(castField.getText());
            movie.setYear(Integer.parseInt(yearField.getText()));
            movie.setRunningTime(Integer.parseInt(runningTimeField.getText()));

            // change movie object status
            movie.setObjectStatus(isNewlyCreated ? ObjectStatus.NEWLY_CREATED : ObjectStatus.UPDATED);

            // insert movie
            mgmt.insertMovie(movie);

            // insert movie image
            if (file != null)
                mgmt.insertMovieImage(movie, file.getPath());
        } catch (Exception e) {
            app.closeApplication(e);
        } finally {
            app.closeMovieWindow();
        }
    }

    @FXML void onExitPressed(ActionEvent event) {
        app.closeMovieWindow();
    }

    @FXML void onDeletePressed(ActionEvent event) {
        try {
            mgmt.removeMovie(movie);
        } catch (Exception e) {
            app.closeApplication(e);
        } finally {
            app.closeMovieWindow();
        }
    }
}