import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {

    public static final int maxBallOnBelt = 10;

    private final Lock lock;
    private final Condition condition;

    private Queue queue;
    private Random random;

    public Producer(Lock lock, Condition condition, Queue queue) {
        this.lock = lock;
        this.condition = condition;
        this.queue = queue;
        this.random = new Random();
    }

    @Override
    public void run() {
        int i;
        while (true) {
            lock.lock();

            while (queue.size() >= maxBallOnBelt) {

                try {
                    //System.err.println("Fließband voll");
                    condition.await();
                } catch (InterruptedException e) {
                    //System.err.println("Wer unterbricht mich?");
                }
            }

            // simulation produktionsdauer
            try {
                Thread.sleep(random.nextInt(500) + 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i = random.nextInt(10);
            queue.add(i);

            System.err.println("Producer: +1; Belt: " + queue.size());

            condition.signalAll();
            lock.unlock();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
}
