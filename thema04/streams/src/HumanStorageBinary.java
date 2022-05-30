import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class HumanStorageBinary implements HumanStorage {

    @Override
    public Collection<Human> read(String file) throws IOException {
        Collection<Human> humans = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int size = dis.readInt();
            for (int i = 0; i < size; i++)
                humans.add(new Human(dis.readUTF(), dis.readChar(), dis.readInt(), dis.readFloat()));
        }
        return humans;
    }

    @Override
    public void write(Collection<Human> humans, String file) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            // write amount of humans to file
            dos.writeInt(humans.size());

            for (Human human : humans) {
                dos.writeUTF(human.getName());
                dos.writeChar(human.getSex());
                dos.writeInt(human.getAge());
                dos.writeFloat(human.getHeight());
            }
        }
    }

}
