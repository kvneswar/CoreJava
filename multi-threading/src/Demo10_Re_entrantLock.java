import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo10_Re_entrantLock {

    public static void main(String[] args) {
        Runner runner = new Runner();
        Thread t1 = new Thread(() -> {
            try {
                runner.firstThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                runner.secondThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runner.finished();
    }

}

class Runner {

    private int count;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment(){
        for (int i=0; i<10000; i++){
            count++;
        }
    }

    public void firstThread() throws InterruptedException{
        lock.lock();
        System.out.println("Waiting ....");
        condition.await();
        System.out.println("Woken up ....");
        try{
            increment();
            System.out.println("Increment done on first thread");
        }finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException{
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Press return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");
        condition.signal();

        try{
            increment();
            System.out.println("Increment done on second thread");
        }finally {
            lock.unlock();
        }
    }

    public void finished(){
        System.out.println("Count is: "+count);
    }

}

