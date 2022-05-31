package model.dao;

import model.object.Movie;
import model.object.Watchlist;

import java.util.Collection;

public interface WatchlistDAOInterface extends AutoCloseable {

    public int insertWatchList(Watchlist w) throws Exception;
    public int addMovieToWatchlist(Movie m, Watchlist w) throws Exception;
    public Watchlist getWatchlist(String watchlistName) throws Exception;
    public Collection<Watchlist> getAllWatchLists() throws Exception;
    public boolean removeMovieFromWatchList(Movie m, Watchlist w) throws Exception;
    public boolean removeWatchlist(Watchlist w) throws Exception;
    public boolean removeAllMoviesFromWatchlist(Watchlist w) throws Exception;

}
