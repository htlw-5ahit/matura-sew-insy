import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class HumanStorageCSV implements HumanStorage {

    @Override
    public Collection<Human> read(String file) throws IOException {
        return null;
    }

    @Override
    public void write(Collection<Human> humans, String file) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            // write headline to csv
            pw.println("Name;Sex;Age;Height");

            for (Human human : humans)
                pw.printf("%s;%c;%d;%.2f%n", human.getName(), human.getSex(), human.getAge(), human.getHeight());
        }
    }
}
