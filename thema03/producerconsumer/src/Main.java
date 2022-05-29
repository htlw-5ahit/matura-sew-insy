import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {

        Queue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(new Producer(lock, condition, queue)).start();

        Thread c1 = new Thread(new Consumer(lock, condition, queue));
        Thread c2 = new Thread(new Consumer(lock, condition, queue));
        Thread c3 = new Thread(new Consumer(lock, condition, queue));

        c1.setName("c1");
        c2.setName("c2");
        c3.setName("c3");

        c1.start();
        c2.start();
        c3.start();
    }
}
