package model.object;

import model.database.ObjectStatus;

import java.sql.Blob;

public class Movie {

    private int id, year, runningTime, objectStatus;
    private String title, director, company, cast, imageType;
    private Blob image;

    public Movie() {
        this(null, null, null, null, -1, -1);
    }

    public Movie(String title, String director, String company, String cast, int year, int runningTime) {
        this(-1, title, director, company, cast, year, runningTime, ObjectStatus.NEWLY_CREATED, null,null);
    }

    public Movie(int id, String title, String director, String company, String cast, int year, int runningTime, int objectStatus, Blob image, String imageType) {
        this.id = id;
        this.year = year;
        this.runningTime = runningTime;
        this.title = title;
        this.director = director;
        this.company = company;
        this.cast = cast;
        this.objectStatus = objectStatus;
        this.image = image;
        this.imageType = imageType;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
        this.objectStatus = ObjectStatus.UPDATED;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", year=" + year +
                ", runningTime=" + runningTime +
                ", objectStatus=" + objectStatus +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", company='" + company + '\'' +
                ", cast='" + cast + '\'' +
                ", imageType='" + imageType + '\'' +
                ", image=" + image +
                '}';
    }
}
