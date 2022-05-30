import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class HumanStorageSingleObjects implements HumanStorage {

    @Override
    public Collection<Human> read(String file) throws IOException, ClassNotFoundException {
        Collection<Human> humans = new ArrayList<Human>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int size = (int) ois.readObject();
            for (int i = 0; i < size; i++) humans.add((Human) ois.readObject());
        }
        return humans;
    }

    @Override
    public void write(Collection<Human> humans, String file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            // oos.writeInt(humans.size()); ORDER:
            oos.writeObject(new Integer(humans.size()));
            for (Human human : humans) oos.writeObject(humans);
        }
    }

}
