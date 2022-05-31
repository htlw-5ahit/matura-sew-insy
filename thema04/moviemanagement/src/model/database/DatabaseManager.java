package model.database;

import model.dao.MovieDAO;
import model.dao.MovieDAOInterface;
import model.dao.WatchlistDAO;
import model.dao.WatchlistDAOInterface;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager implements AutoCloseable {

    private Connection connection;
    private MovieDAOInterface movieDAO;
    private WatchlistDAOInterface watchlistDAO;

    public DatabaseManager(String propertiesFile) throws SQLException, IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(propertiesFile))) {
            Properties properties = new Properties();
            properties.load(bis);
            initialize(properties.getProperty("Url"), properties.getProperty("Username"), properties.getProperty("Password"));
        }
    }

    public DatabaseManager(String url, String username, String password) throws SQLException {
        initialize(url, username, password);
    }

    private void initialize(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        movieDAO = new MovieDAO(this);
        watchlistDAO = new WatchlistDAO(this);
    }

    @Override
    public void close() throws Exception {
        if (connection != null) connection.close();
        if (watchlistDAO != null) watchlistDAO.close();
        if (movieDAO != null) movieDAO.close();
    }

    public MovieDAOInterface getMovieDAO() {
        return movieDAO;
    }

    public WatchlistDAOInterface getWatchlistDAO() {
        return watchlistDAO;
    }

    public Connection getConnection() {
        return connection;
    }
}
