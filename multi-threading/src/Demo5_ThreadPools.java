import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo5_ThreadPools {

    public static void main(String... args){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i=0; i<5; i++){
            executorService.submit(new Processor1(i));
        }
        executorService.shutdown();
        System.out.println("All tasks submitted");
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All tasks completed....");
    }
}

class Processor1 implements Runnable{

    @Override
    public void run() {
        System.out.println("Starting: " + id +", "+ Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed: " + id +", "+ Thread.currentThread().getName());
    }

    private int id;

    public Processor1(int id){
        this.id = id;
    }
}
