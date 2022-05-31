package model.object;

import model.database.ObjectStatus;

import java.util.Map;
import java.util.TreeMap;

public class Watchlist {

    private int id, objectStatus;
    private String title, genre, description;
    private TreeMap<Integer, Movie> movies;

    public Watchlist() {
        this(-1, null, null,null);
    }

    public Watchlist(String title, String genre, String description) {
        this (-1, title, genre, description);
    }

    public Watchlist(int id, String title, String genre, String description) {
        this(id, title, genre, description, new TreeMap<>());
    }

    public Watchlist(int id, String title, String genre, String description, TreeMap<Integer, Movie> movies) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.movies = movies;
    }

    public int getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(int objectStatus) {
        this.objectStatus = objectStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public TreeMap<Integer, Movie> getMovies() {
        return movies;
    }

    public int addMovie(Movie m) {
        int position = movies.size() + 1;
        movies.put(position, m);
        return position;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Watchlist{id=" + id  + ", title='" + title + "', genre='" + genre + "', description='" + description + "'}");
        for (Map.Entry<Integer, Movie> entry : movies.entrySet())
            sb.append("\n\t- " + entry.getKey() + ": " + entry.getValue().toString());
        return sb.toString();
    }
}
