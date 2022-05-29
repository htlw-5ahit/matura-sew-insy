import java.util.concurrent.Semaphore;

public class Main2 {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1,true);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        semaphore.acquire();
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                }
            }
        };

        Thread t1 = new Thread(runnable,"tick");
        Thread t2 = new Thread(runnable,"tack");

        t1.start();
        t2.start();
    }
}