import java.io.IOException;
import java.util.Collection;

public interface HumanStorage {

    Collection<Human> read(String file) throws IOException, ClassNotFoundException;
    void write(Collection<Human> humans, String file) throws IOException;

}
