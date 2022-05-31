package test;

import model.database.DatabaseManager;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {
        try (DatabaseManager dbmgr = new DatabaseManager("database.properties")) {
            String path = "./images/";
            HashMap<String, String> movies = new HashMap<>();

            /*movies.put("Things Heard & Seen", path + "things.jpg");
            movies.put("Rampage – Big Meets Bigger", path + "bigmeetsbigger.jpg");
            movies.put("Minions", path + "minions.jpg");
            movies.put("The Boss Baby", path + "bossbaby.jpg");
            movies.put("Ice Age", path + "iceage.jpg");
            movies.put("Soul", path + "soul.jpg");
            movies.put("CHIPS", path + "chips.jpg");
            movies.put("Fast & Furious: Hobbs & Shaw", path + "faf.jpg");
            movies.put("The Gentlemen", path + "gentleman.jpg");
            movies.put("ES Kapitel 2", path + "es.jpg");
            movies.put("Joker", path + "joker.jpg");
            movies.put("Tom Clancy’s Gnadenlos", path + "gnadenlos.jpg");
            movies.put("The Wolf's Call", path + "wolfscall.jpg");
            movies.put("Ballon", path + "ballon.jpg");*/



            for (Map.Entry<String, String> entry : movies.entrySet()) {
                dbmgr.getMovieDAO().insertImage(
                        dbmgr.getMovieDAO().getMovie(entry.getKey()),
                        entry.getValue()
                );
            }
        }

    }
}
