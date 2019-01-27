import java.util.stream.IntStream;

public class Demo1_Create_Thread {

    public static void main(String... args){
        SampleThread sampleThread = new SampleThread();
        sampleThread.start();

        Thread thread = new Thread(new SampleRunnable());
        thread.start();
    }
}

class SampleThread extends Thread{
    private static void accept(int param) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": " + param);
    }

    @Override
    public void run() {
        IntStream.range(1, 10)
                .forEach(SampleThread::accept);
    }
}

class SampleRunnable implements Runnable{
    @Override
    public void run() {
      IntStream.range(1, 10)
        .forEach(SampleRunnable::accept);
    }

    private static void accept(int param){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": " + param);
    }
}
