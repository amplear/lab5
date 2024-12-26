import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
public class lab5 {

    public static void main (String[] args) {
        CompletableFuture<String> fetchDataFromDatabase = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Fetching data from the database...");
                TimeUnit.SECONDS.sleep(2); 
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Data from DB";
        });
        CompletableFuture<String> processData = fetchDataFromDatabase.thenApply(data -> {
            System.out.println("Processing data: " + data);
            return "Processed " + data;
        });
        try {
            System.out.println("Final result: " + processData.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
