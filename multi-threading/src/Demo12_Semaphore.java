import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Demo12_Semaphore {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0;i<200;i++){
            executorService.submit(()->{
                Connection.getInstance().connect();
            });
        }
        executorService.shutdown();
    }
}

class Connection{

    private static Connection connection = new Connection();

    private Connection(){

    }

    public static Connection getInstance(){
        return connection;
    }

    private int connections = 0;

    private Semaphore semaphore = new Semaphore(10, true);

    public void connect(){
        try {
            semaphore.acquire();
            synchronized (this){
                connections++;
                System.out.println("Current Connections: " + connections);
            }
            Thread.sleep(1000);
            synchronized (this){
                connections--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

}
