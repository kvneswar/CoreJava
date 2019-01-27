import java.util.Random;

public class Demo14_InterruptingException {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting...");
        Thread thread = new Thread(() -> {
            Random random = new Random();
            for (int i=0; i<1E8; i++){
                /*try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }*/
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Interrupted");
                    break;
                }
                Math.sin(random.nextDouble());
            }
        });
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
        thread.join();
        System.out.println("Finished");
    }
}
