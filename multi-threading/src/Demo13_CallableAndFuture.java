import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo13_CallableAndFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            Random random = new Random();
            int duration = random.nextInt(4000);
            if(duration > 2000){
                throw new RuntimeException("Sleeping for too long..."+duration);
            }
            System.out.println("Starting ...");
            try{
                Thread.sleep(duration);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Finished ...");
            return duration;
        });

        executorService.shutdown();
        try {
            Integer duration = future.get();
            System.out.println("Duration: "+duration);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
