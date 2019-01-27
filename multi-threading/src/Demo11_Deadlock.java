import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo11_Deadlock {

    public static void main(String[] args) {
        Runner1 runner1 = new Runner1();
        Thread t1 = new Thread(() -> {
            try {
                runner1.firstThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                runner1.secondThread();
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
        runner1.finished();
    }

}


class Runner1{

    private Account account1 = new Account();
    private Account account2 = new Account();

    /*
        1. Remove the locks and see check the balance, it won't be 20,000 always.
        2. Change the order of locks in any of the threads then deadlock will happen. Fix: order should be same.
        3. Use getLock to avoid deadlock.
    */
    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();

    private void acquireLocks(Lock lock1, Lock lock2) throws InterruptedException {
        while (true){
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try{
                gotFirstLock = lock1.tryLock();
                gotSecondLock = lock2.tryLock();
            }finally {
                if(gotFirstLock && gotSecondLock){
                    return;
                }
                if(gotFirstLock){
                    lock1.unlock();
                }
                if(gotSecondLock){
                    lock2.unlock();
                }
            }
            Thread.sleep(1);
        }
    }

    public void firstThread() throws InterruptedException{
        Random random = new Random();
        for (int i=0; i<10000; i++){
            /*lock1.lock();
            lock2.lock();*/
            acquireLocks(lock1, lock2);
            try{
                Account.transfer(account1, account2, random.nextInt(100));
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException{
        Random random = new Random();
        for (int i=0; i<10000; i++){
            /*
              // Deadlock
              lock2.lock();
              lock1.lock();
            */
            /*lock1.lock();
            lock2.lock();*/
            acquireLocks(lock2, lock1);   //Another fix for deadlock
            try{
                Account.transfer(account2, account1, random.nextInt(100));
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished(){
        System.out.println("Account1 Balance: " + account1.getBalance());
        System.out.println("Account2 Balance: " + account2.getBalance());
        System.out.println("Total Balance: " + (account1.getBalance() + account2.getBalance()));
    }

}

class Account{

    private int balance = 10000;

    public void deposit(int amount){
        balance += amount;
    }

    public void withdraw(int amount){
        balance -= amount;
    }

    public int getBalance(){
        return balance;
    }

    public static void transfer(Account account1, Account account2, int amount){
        account1.withdraw(amount);
        account2.deposit(amount);
    }
}