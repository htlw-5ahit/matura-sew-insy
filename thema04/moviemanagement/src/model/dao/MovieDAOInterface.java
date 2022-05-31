package model.dao;

import model.object.Movie;

import java.util.Collection;

public interface MovieDAOInterface extends AutoCloseable {

    public int insertMovie(Movie m) throws Exception;
    public Movie getMovie(String name) throws Exception;
    public boolean insertImage(Movie m, String imagePath) throws Exception;
    public String getImage(Movie m, String directoryPath) throws Exception;
    public Collection<Movie> getAllMovies() throws Exception;
    public boolean removeMovie(Movie m) throws Exception;

}
