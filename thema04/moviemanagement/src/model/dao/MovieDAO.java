package model.dao;

import model.database.DatabaseManager;
import model.database.ObjectStatus;
import model.object.Movie;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

public class MovieDAO implements MovieDAOInterface {

    private PreparedStatement selectStmt, selectIdStmt, insertStmt, updateStmt, selectImageStmt,
            updateImageStmt, moviesStmt, removeStmt, removeConStmt;

    public MovieDAO(DatabaseManager dbmgr) throws SQLException {
        selectStmt = dbmgr.getConnection().prepareStatement("SELECT * FROM MOVIES WHERE TITLE = ?");
        selectIdStmt = dbmgr.getConnection().prepareStatement("SELECT ID FROM MOVIES WHERE TITLE = ? AND DIRECTOR = ? AND COMPANY = ? AND YEAR = ? AND RUNNINGTIME = ? AND CASTS = ?");
        insertStmt = dbmgr.getConnection().prepareStatement("INSERT INTO MOVIES (TITLE, DIRECTOR, COMPANY, YEAR, RUNNINGTIME, CASTS) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        updateStmt = dbmgr.getConnection().prepareStatement("UPDATE MOVIES SET TITLE = ?, DIRECTOR = ?, COMPANY = ?, YEAR = ?, RUNNINGTIME = ?, CASTS = ? WHERE ID = ?");
        selectImageStmt = dbmgr.getConnection().prepareStatement("SELECT IMAGE, IMAGE_TYPE FROM MOVIES WHERE ID = ?");
        updateImageStmt = dbmgr.getConnection().prepareStatement("UPDATE MOVIES SET IMAGE = ?, IMAGE_TYPE = ? WHERE ID = ?");
        moviesStmt = dbmgr.getConnection().prepareStatement("SELECT * FROM MOVIES");
        removeStmt = dbmgr.getConnection().prepareStatement("DELETE FROM MOVIES WHERE ID = ?");
        removeConStmt = dbmgr.getConnection().prepareStatement("DELETE FROM WATCHLIST_MOVIES WHERE MOVIE_ID = ?");
    }

    @Override
    public void close() throws Exception {
        if (selectStmt != null) selectStmt.close();
        if (selectIdStmt != null) selectIdStmt.close();
        if (insertStmt != null) insertStmt.close();
        if (updateStmt != null) updateStmt.close();
        if (selectImageStmt != null) selectImageStmt.close();
        if (updateImageStmt != null) updateImageStmt.close();
        if (moviesStmt != null) moviesStmt.close();
        if (removeStmt != null) removeStmt.close();
        if (removeConStmt != null) removeConStmt.close();
    }

    @Override
    public Movie getMovie(String name) throws Exception {
        selectStmt.clearParameters();
        selectStmt.setString(1, name);
        try (ResultSet rs = selectStmt.executeQuery()) {
            if (rs.next()) {
                return new Movie(rs.getInt("ID"), rs.getString("TITLE"),
                        rs.getString("DIRECTOR"), rs.getString("COMPANY"),
                        rs.getString("CASTS"), rs.getInt("YEAR"),
                        rs.getInt("RUNNINGTIME"), ObjectStatus.DATABASE,
                        rs.getBlob("IMAGE"), rs.getString("IMAGE_TYPE"));
            }
        }
        return null;
    }

    @Override
    public int insertMovie(Movie m) throws SQLException {
        int statusCode = 2;

        // newly generated object
        if (m.getObjectStatus() == ObjectStatus.NEWLY_CREATED) {
            // check if movie exists
            int movieId = existsMovie(m);

            if (movieId == -1) {
                // object not in database => insert new tuple
                insertStmt.clearParameters();
                insertStmt.setString(1, m.getTitle());
                insertStmt.setString(2, m.getDirector());
                insertStmt.setString(3, m.getCompany());
                insertStmt.setInt(4, m.getYear());
                insertStmt.setInt(5, m.getRunningTime());
                insertStmt.setString(6, m.getCast());
                if (insertStmt.executeUpdate() > 0)
                    try (ResultSet rs = insertStmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            statusCode = 0;
                            m.setId(rs.getInt("ID"));
                        }
                    }
            } else
                // object exactly like that in database
                m.setId(movieId);
        // object already in database => update
        // update object in database with values from java object
        } else if (m.getObjectStatus() == ObjectStatus.UPDATED) {
            updateStmt.clearParameters();
            updateStmt.setString(1, m.getTitle());
            updateStmt.setString(2, m.getDirector());
            updateStmt.setString(3, m.getCompany());
            updateStmt.setInt(4, m.getYear());
            updateStmt.setInt(5, m.getRunningTime());
            updateStmt.setString(6, m.getCast());
            updateStmt.setInt(7, m.getId());
            statusCode = (updateStmt.executeUpdate() == 1 ? 1 : 2);

        // movie from database and not changed ==> movie not changed
        } else if(m.getObjectStatus() == ObjectStatus.DATABASE)
            statusCode = 2;

        // set object status to database => same values in java and db
        m.setObjectStatus(ObjectStatus.DATABASE);

        return statusCode;
    }

    public PreparedStatement getInsertStmt() {
        return insertStmt;
    }

    private int existsMovie(Movie m) throws SQLException {
        int id = -1;
        selectIdStmt.clearParameters();
        selectIdStmt.setString(1, m.getTitle());
        selectIdStmt.setString(2, m.getDirector());
        selectIdStmt.setString(3, m.getCompany());
        selectIdStmt.setInt(4, m.getYear());
        selectIdStmt.setInt(5, m.getRunningTime());
        selectIdStmt.setString(6, m.getCast());
        try (ResultSet rs = selectIdStmt.executeQuery()) {
            if (rs.next())
                id = rs.getInt("ID");
        }
        return id;
    }

    @Override
    public boolean insertImage(Movie m, String imagePath) throws Exception {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(imagePath));) {
            updateImageStmt.clearParameters();
            updateImageStmt.setBinaryStream(1, bis);
            updateImageStmt.setString(2, imagePath.substring(imagePath.lastIndexOf(".") + 1));
            updateImageStmt.setInt(3, m.getId());
            return updateImageStmt.executeUpdate() == 1;
        }
    }

    @Override
    public String getImage(Movie m, String directoryPath) throws Exception {
        String filename = null;
        selectImageStmt.clearParameters();
        selectImageStmt.setInt(1, m.getId());
        try (ResultSet rs = selectImageStmt.executeQuery()) {
            if (rs.next()) {
                filename = directoryPath + m.getId() + "." + rs.getString("IMAGE_TYPE");
                try (DataInputStream dis = new DataInputStream(new BufferedInputStream(rs.getBinaryStream("IMAGE")));
                     DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
                    byte[] buffer = new byte[8 * 1024];
                    int bytesRead;
                    while ((bytesRead = dis.read(buffer)) != -1)
                        dos.write(buffer, 0, bytesRead);
                }
            }
        }
        return filename;
    }

    @Override
    public Collection<Movie> getAllMovies() throws Exception {
        HashSet<Movie> movies = new HashSet<>();
        try (ResultSet rs = moviesStmt.executeQuery()) {
            while (rs.next()) {
                movies.add(new Movie(rs.getInt("ID"), rs.getString("TITLE"),
                        rs.getString("DIRECTOR"), rs.getString("COMPANY"),
                        rs.getString("CASTS"), rs.getInt("YEAR"),
                        rs.getInt("RUNNINGTIME"), ObjectStatus.DATABASE,
                        rs.getBlob("IMAGE"), rs.getString("IMAGE_TYPE")));
            }
        }
        return movies;
    }

    @Override
    public boolean removeMovie(Movie m) throws Exception {
        // remove movie watchlist connection
        removeConStmt.clearParameters();
        removeConStmt.setInt(1, m.getId());
        // remove movie
        removeStmt.clearParameters();
        removeStmt.setInt(1, m.getId());
        return removeConStmt.executeUpdate() >= 0 && removeStmt.executeUpdate() > 0;
    }
}
