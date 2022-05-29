import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

    public static final int randomSleepMin = 500;
    public static final int randomSleepMax = 1000;

    private final Lock lock;
    private final Condition condition;

    private Random random;
    private Queue queue;

    public Consumer(Lock lock, Condition condition, Queue queue) {
        this.lock = lock;
        this.condition = condition;
        this.queue = queue;
        this.random = new Random();
    }

    @Override
    public void run() {

        while (true) {
            lock.lock();

            // warte, falls förderband leer
            while (queue.size() < 1) {
                System.out.println(Thread.currentThread().getName() + " Forderband ist leer, leg mich schlafen!");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " packe ein " + queue.remove());

            condition.signalAll();
            lock.unlock();

            // simuliere verabeitungszeit
            try {
                Thread.sleep(random.nextInt(500) + 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
