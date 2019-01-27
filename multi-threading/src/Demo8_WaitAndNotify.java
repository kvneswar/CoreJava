import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo8_WaitAndNotify {

    public static void main(String[] args) {
        Processor3 processor3 = new Processor3();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try {
                processor3.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(()->{
            try {
                processor3.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

}

class Processor3{

    /*
       If we use infinite loop, it will use more system resources, instead we can use wait(), notify().
     */

    public void producer() throws InterruptedException {
        synchronized (this){
            System.out.println("Producer thread is running...");
            wait();
            System.out.println("Resumed...");
        }

    }

    public void consumer() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this){
            System.out.println("Waiting for return key...");
            scanner.nextLine();
            System.out.println("Return key pressed...");
            notify();
        }
    }
}