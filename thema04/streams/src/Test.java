import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Test {

    public static void main(String[] args) {

        Collection<Human> humans = new ArrayList<>();
        HumanStorage humanStorageCSV = new HumanStorageCSV();
        HumanStorage humanStorageBinary = new HumanStorageBinary();
        HumanStorageObjects humanStorageObjects = new HumanStorageObjects();
        HumanStorageSingleObjects humanStorageSingleObjects = new HumanStorageSingleObjects();

        try {
            humans.add(new Human("Muhamed", 'm', 18, 1.8f));
            humans.add(new Human("Alexander", 'm', 17, 1.81f));
            humans.add(new Human("Simon", 'm', 17, 1.83f));
            humans.add(new Human("Niklas", 'm', 17, 1.75f));

            humanStorageCSV.write(humans, "humans.csv");


            System.out.println("\nhumanStorageBinary");
            humanStorageBinary.write(humans, "humans.bin");
            Collection<Human> binaryHumans = humanStorageBinary.read("humans.bin");
            for (Human human : binaryHumans) System.out.println(human);

            System.out.println("\nhumanStorageObjects");
            humanStorageObjects.write(humans, "humans.dat");
            Collection<Human> objectsHumans = humanStorageObjects.read("humans.dat");
            for (Human human : objectsHumans) System.out.println(human);

            System.out.println("\nhumanStorageSingleObjects");
            humanStorageSingleObjects.write(humans, "humans.objects");
            Collection<Human> singleObjectsHumans = humanStorageSingleObjects.read("humans.objects");
            for (Human human : singleObjectsHumans) System.out.println(human);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
