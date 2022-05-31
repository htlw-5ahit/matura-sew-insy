package model.database;

public class ObjectStatus {

    public static final int NEWLY_CREATED = 0; // object created in java and is not in database
    public static final int UPDATED = 1; // object updated in java - not in database
    public static final int DATABASE = 2; // object same as in database

}
