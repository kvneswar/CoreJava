import java.util.Scanner;

public class Demo2_Volatile {

    public static void main(String... args){
        Processor processor = new Processor();
        processor.start();

        System.out.println("Enter return key to stop... ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        processor.shutdown();
    }
}

/*
    <i>volatile</i> prevents Threads to cache the variables when they are not changed with in that thread.
    <ol>
        <li>If we need to change the variables in one thread from another thread then we need to use volatile keyword or synchronization</li>
    </ol>
*/

class Processor extends Thread{

    private volatile boolean running = true;

    @Override
    public void run() {
        int counter = 0;
        while(running){
            System.out.println("Counter: " + counter++);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown(){
        System.out.println("Received request to stop...");
        this.running = false;
    }

}