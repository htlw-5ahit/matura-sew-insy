package model.dao;

import model.database.DatabaseManager;
import model.database.ObjectStatus;
import model.object.Movie;
import model.object.Watchlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class WatchlistDAO implements WatchlistDAOInterface {

    private DatabaseManager dbmgr;
    private PreparedStatement insertStmt, updateStmt, insertConnectionStmt, selectWatchlist, selectAllWatchlist,
            selectMoviesStmt, selectWatchlistAttrStmt, selectConnectionStmt, deleteConStmt, deleteStmt, deleteMultipleStmt;

    public WatchlistDAO(DatabaseManager dbmgr) throws SQLException {
        this.dbmgr = dbmgr;
        insertStmt = dbmgr.getConnection().prepareStatement("INSERT INTO WATCHLIST (WATCHLIST_TITLE, GENRE, DESCRIPTION) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        updateStmt = dbmgr.getConnection().prepareStatement("UPDATE WATCHLIST SET WATCHLIST_TITLE = ?, GENRE = ?, DESCRIPTION = ? WHERE ID = ?");
        insertConnectionStmt = dbmgr.getConnection().prepareStatement("INSERT INTO WATCHLIST_MOVIES (WATCHLIST_ID, MOVIE_ID, POSITION_NO) VALUES (?, ?, ?)");
        selectMoviesStmt = dbmgr.getConnection().prepareStatement("SELECT MOVIES.*, WM.POSITION_NO FROM MOVIES INNER JOIN WATCHLIST_MOVIES WM on MOVIES.ID = WM.MOVIE_ID WHERE WM.WATCHLIST_ID = ?");
        selectWatchlist = dbmgr.getConnection().prepareStatement("SELECT * FROM WATCHLIST WHERE WATCHLIST_TITLE = ?");
        selectAllWatchlist = dbmgr.getConnection().prepareStatement("SELECT * FROM WATCHLIST");
        selectWatchlistAttrStmt = dbmgr.getConnection().prepareStatement("SELECT * FROM WATCHLIST WHERE WATCHLIST_TITLE = ? AND GENRE = ? AND DESCRIPTION = ?");
        selectConnectionStmt = dbmgr.getConnection().prepareStatement("SELECT * FROM WATCHLIST_MOVIES WHERE WATCHLIST_ID = ? AND MOVIE_ID = ?");
        deleteConStmt = dbmgr.getConnection().prepareStatement("DELETE FROM WATCHLIST_MOVIES WHERE WATCHLIST_ID = ? AND MOVIE_ID = ?");
        deleteStmt = dbmgr.getConnection().prepareStatement("DELETE FROM WATCHLIST WHERE ID = ?");
        deleteMultipleStmt = dbmgr.getConnection().prepareStatement("DELETE FROM WATCHLIST_MOVIES WHERE WATCHLIST_ID = ?");
    }

    @Override
    public void close() throws Exception {
        if (insertStmt != null) insertStmt.close();
        if (updateStmt != null) updateStmt.close();
        if (insertConnectionStmt != null) insertConnectionStmt.close();
        if (selectWatchlist != null) selectWatchlist.close();
        if (selectAllWatchlist != null) selectAllWatchlist.close();
        if (selectMoviesStmt != null) selectMoviesStmt.close();
        if (selectWatchlistAttrStmt != null) selectWatchlistAttrStmt.close();
        if (selectConnectionStmt != null) selectConnectionStmt.close();
        if (deleteConStmt != null) deleteConStmt.close();
        if (deleteStmt != null) deleteStmt.close();
        if (deleteMultipleStmt != null) deleteMultipleStmt.close();
    }

    @Override
    public int insertWatchList(Watchlist w) throws Exception {
        int statusCode = 2;

        // newly created object
        if (w.getObjectStatus() == ObjectStatus.NEWLY_CREATED) {
            // check if movie exists
            int watchlistId = existsWatchlist(w);

            if (watchlistId == -1) {
                // object not in database => insert new tuple
                insertStmt.clearParameters();
                insertStmt.setString(1, w.getTitle());
                insertStmt.setString(2, w.getGenre());
                insertStmt.setString(3, w.getDescription());
                if (insertStmt.executeUpdate() > 0)
                    try (ResultSet rs1 = insertStmt.getGeneratedKeys()) {
                        if (rs1.next()) {
                            w.setId(rs1.getInt("ID"));
                            statusCode = 0;
                        }
                    }
            } else
                // object exactly like that in database
                w.setId(watchlistId);
        // update object in database with values from java object
        } else if (w.getObjectStatus() == ObjectStatus.UPDATED) {
            updateStmt.setString(1, w.getTitle());
            updateStmt.setString(2, w.getGenre());
            updateStmt.setString(3, w.getDescription());
            updateStmt.setInt(4, w.getId());
            updateStmt.executeUpdate();
        }

        boolean autoCommit = dbmgr.getConnection().getAutoCommit();
        dbmgr.getConnection().setAutoCommit(false);

        // remove existing connection between watchlist and movies
        deleteMultipleStmt.setInt(1, w.getId());
        deleteMultipleStmt.executeUpdate();

        // create connection between movies and watchlist
        try {
            for (Map.Entry<Integer, Movie> entry : w.getMovies().entrySet()) {
                dbmgr.getMovieDAO().insertMovie(entry.getValue());

                // check if connection exists
                if (!isWatchlistMovieConnected(entry.getValue(), w)) {
                    insertConnectionStmt.clearParameters();
                    insertConnectionStmt.setInt(1, w.getId());
                    insertConnectionStmt.setInt(2, entry.getValue().getId());
                    insertConnectionStmt.setInt(3, entry.getKey());
                    statusCode = (insertConnectionStmt.executeUpdate() == 1 ? 0 : 1);
                }
            }
            dbmgr.getConnection().commit();
        } catch (SQLException e) {
            dbmgr.getConnection().rollback();
        } finally {
            dbmgr.getConnection().setAutoCommit(autoCommit);
        }

        return statusCode;
    }

    @Override
    public int addMovieToWatchlist(Movie m, Watchlist w) throws Exception {
        int statusCode = 2;
        int position = w.addMovie(m); // add movie to watchlist movie list in java
        dbmgr.getMovieDAO().insertMovie(m); // add/update movie; must do to get id if not available

        if (!isWatchlistMovieConnected(m, w)) {
            insertConnectionStmt.clearParameters();
            insertConnectionStmt.setInt(1, w.getId());
            insertConnectionStmt.setInt(2, m.getId());
            insertConnectionStmt.setInt(3, position);
            statusCode = (insertConnectionStmt.executeUpdate() == 1 ? 0 : 1);
        }
        return statusCode;
    }

    @Override
    public Watchlist getWatchlist(String watchlistName) throws SQLException {
        Watchlist watchlist = null;
        selectWatchlist.clearParameters();
        selectWatchlist.setString(1, watchlistName);
        try (ResultSet rs = selectWatchlist.executeQuery()) {
            if (rs.next())
                watchlist = new Watchlist(rs.getInt("ID"), rs.getString("WATCHLIST_TITLE"),
                        rs.getString("GENRE"), rs.getString("DESCRIPTION"),
                        getMovies(rs.getInt("ID")));
        }
        return watchlist;
    }

    @Override
    public Collection<Watchlist> getAllWatchLists() throws SQLException {
        HashSet<Watchlist> watchlists = new HashSet<>();
        try (ResultSet rs = selectAllWatchlist.executeQuery()) {
            while (rs.next())
                watchlists.add(new Watchlist(rs.getInt("ID"), rs.getString("WATCHLIST_TITLE"),
                        rs.getString("GENRE"), rs.getString("DESCRIPTION"),
                        getMovies(rs.getInt("ID"))));
        }
        return watchlists;
    }

    @Override
    public boolean removeMovieFromWatchList(Movie m, Watchlist w) throws SQLException {
        // iter array and remove when found
        if (w.getMovies().containsValue(m))
            for (Integer key : w.getMovies().keySet())
                if (w.getMovies().get(key).equals(m)){
                    w.getMovies().remove(key);
                    break;
                }

        deleteConStmt.clearParameters();
        deleteConStmt.setInt(1, w.getId());
        deleteConStmt.setInt(2, m.getId());
        return deleteConStmt.executeUpdate() == 1;
    }

    private int existsWatchlist(Watchlist w) throws SQLException {
        int id = -1;
        selectWatchlistAttrStmt.clearParameters();
        selectWatchlistAttrStmt.setString(1, w.getTitle());
        selectWatchlistAttrStmt.setString(2, w.getGenre());
        selectWatchlistAttrStmt.setString(3, w.getDescription());
        try (ResultSet rs = selectWatchlistAttrStmt.executeQuery()) {
            if (rs.next())
                id = rs.getInt("ID");
        }
        return id;
    }

    private boolean isWatchlistMovieConnected(Movie m, Watchlist w) throws SQLException {
        boolean available = false;
        selectConnectionStmt.clearParameters();
        selectConnectionStmt.setInt(1, w.getId());
        selectConnectionStmt.setInt(2, m.getId());
        try (ResultSet rs = selectConnectionStmt.executeQuery()) {
            available = rs.next();
        }
        return available;
    }

    private TreeMap<Integer, Movie> getMovies(int watchlistId) throws SQLException {
        TreeMap<Integer, Movie> movies = new TreeMap<>();
        selectMoviesStmt.clearParameters();
        selectMoviesStmt.setInt(1, watchlistId);
        try (ResultSet rs = selectMoviesStmt.executeQuery()) {
            while (rs.next())
                movies.put(rs.getInt("POSITION_NO"), new Movie(rs.getInt("ID"), rs.getString("TITLE"),
                        rs.getString("DIRECTOR"), rs.getString("COMPANY"),
                        rs.getString("CASTS"), rs.getInt("YEAR"),
                        rs.getInt("RUNNINGTIME"), ObjectStatus.DATABASE,
                        rs.getBlob("IMAGE"), rs.getString("IMAGE_TYPE")));
        }
        return movies;
    }

    @Override
    public boolean removeWatchlist(Watchlist w) throws Exception {
        // prepare stmt for deleting watchlist
        deleteStmt.clearParameters();
        deleteStmt.setInt(1, w.getId());
        // execute and return status code
        return removeAllMoviesFromWatchlist(w) && deleteStmt.executeUpdate() > 0;
    }

    @Override
    public boolean removeAllMoviesFromWatchlist(Watchlist w) throws Exception {
        // prepare stmt for deleting movie watchlist connection
        deleteMultipleStmt.clearParameters();
        deleteMultipleStmt.setInt(1, w.getId());
        return deleteMultipleStmt.executeUpdate() >= 0;
    }
}
