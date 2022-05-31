package model;

import model.dao.MovieDAOInterface;
import model.dao.WatchlistDAOInterface;
import model.database.DatabaseManager;
import model.object.Movie;
import model.object.Watchlist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class MovieManagement implements AutoCloseable {

    private final DatabaseManager dbmgr;
    private final WatchlistDAOInterface wdao;
    private final MovieDAOInterface mdao;

    public MovieManagement(String propertiesFile) throws IOException, SQLException {
        dbmgr = new DatabaseManager(propertiesFile);
        wdao = dbmgr.getWatchlistDAO();
        mdao = dbmgr.getMovieDAO();
    }

    @Override
    public void close() throws Exception {
        if (dbmgr != null) dbmgr.close();
        if (wdao != null) wdao.close();
        if (mdao != null) mdao.close();
    }

    public void insertMovie(Movie movie) throws Exception {
        mdao.insertMovie(movie);
    }

    public void insertMovieImage(Movie movie, String path) throws Exception {
        mdao.insertImage(movie, path);
    }

    public Collection<Watchlist> getAllWatchlist() throws Exception {
        return wdao.getAllWatchLists();
    }

    public Movie getMovie(String movieName) throws Exception {
        return mdao.getMovie(movieName);
    }

    public Watchlist getWatchlist(String watchlistName) throws Exception {
        return wdao.getWatchlist(watchlistName);
    }

    public int insertWatchlist(Watchlist watchlist) throws Exception {
        return wdao.insertWatchList(watchlist);
    }

    public Collection<Movie> getAllMovies() throws Exception {
        return mdao.getAllMovies();
    }

    public boolean removeWatchlist(Watchlist watchlist) throws Exception {
        return wdao.removeWatchlist(watchlist);
    }

    public boolean removeMovie(Movie movie) throws Exception {
        return mdao.removeMovie(movie);
    }
}
