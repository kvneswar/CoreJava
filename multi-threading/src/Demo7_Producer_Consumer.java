import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Demo7_Producer_Consumer {

    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(10);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }

    public static void producer() throws InterruptedException {
        Random random = new Random();
        while (true){
            blockingQueue.put(random.nextInt(100));
        }
    }

    public static void consumer() throws InterruptedException {
        Random random = new Random();
        while (true){
            Thread.sleep(100);
            if(random.nextInt(10) == 0){
                Integer value = blockingQueue.take();
                System.out.println("Taken value: " + value + ", queue size: "+blockingQueue.size());
            }
        }
    }

}
