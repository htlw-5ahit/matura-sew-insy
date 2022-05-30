import java.io.*;
import java.util.Collection;

public class HumanStorageObjects implements HumanStorage {

    @Override
    public Collection<Human> read(String file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            return (Collection<Human>) ois.readObject();
        }
    }

    @Override
    public void write(Collection<Human> humans, String file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(humans);
        }
    }
}
