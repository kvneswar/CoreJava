import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo4_MultipleLocks {

    public static void main(String... args){
        Worker worker = new Worker();
        worker.main();
    }

}

class Worker{

    private Random random = new Random();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    /*
        If we use the Synchronized keyword on method, it will acquire the lock on object.
        So no two threads can access any of the below methods at the same time.

        But each method is accessing different list objects. so these methods can get executed in parallel by different threads.
        So we can use Synchronized block, it won't acquire the lock on whole worker object.
    */

    public void stageOne(){
        synchronized (lock1){
            try {
                Thread.sleep(1);
            }catch (InterruptedException e){
                System.err.println(e);
            }
            list1.add(random.nextInt(100));
        }
    }

    public void stageTwo(){
        synchronized(lock2){
            try {
                Thread.sleep(1);
            }catch (InterruptedException e){
                System.err.println(e);
            }
            list2.add(random.nextInt(100));
        }
    }

    public void process(){
        for(int i=0; i<1000; i++){
            stageOne();
            stageTwo();
        }
    }

    public void main(){
        System.out.println("Starting...");
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            process();
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            process();
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
        System.out.println("Size: list1-"+list1.size() + ", list2-"+list2.size());

    }
}
