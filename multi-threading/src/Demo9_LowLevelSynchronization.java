import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo9_LowLevelSynchronization {

    public static void main(String[] args) {
        Processor4 processor4 = new Processor4();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            processor4.consumer();
        });
        executorService.submit(() -> {
            processor4.producer();
        });
        executorService.shutdown();
    }
}


class Processor4{

    LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT =10;
    private Object lock = new Object();

    public void producer(){
        int value = 0;
        while (true){
            synchronized (lock){
                while (list.size() == LIMIT){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(value++);
                lock.notify();
            }
        }
    }

    public void consumer(){
        Random random = new Random();
        while (true){
            synchronized (lock) {
                while (list.size() == 0){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("List size: "+list.size());
                int value = list.removeFirst();
                System.out.println(", value: "+value);
                lock.notify();
            }
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}