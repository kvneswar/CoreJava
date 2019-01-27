public class Demo3_Synchronization {

    private int counter=0;

    public synchronized void increment(){
        counter++;
    }

    public static void main(String... args) throws Exception{
        Demo3_Synchronization demo3_synchronization = new Demo3_Synchronization();
        demo3_synchronization.doWork();
    }

    private void doWork() throws Exception{

        Thread t1 = new Thread(() -> {
            for(int i=0; i<10000; i++)
                increment();
        });

        Thread t2 = new Thread(() -> {
            for(int i=0; i<10000; i++)
                increment();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter);
    }


}
