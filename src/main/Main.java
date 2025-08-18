import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        var pool = new FixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.execute(() -> createTask(finalI, ThreadLocalRandom.current().nextInt(1500, 3000)));
        }

        pool.shutdown();
        pool.execute(() -> createTask(222, 1541));

//        pool.awaitTermination(6, TimeUnit.SECONDS);
    }

    private static void createTask(int taskNumber, int sleepTimeMs) {
        String formatted = "Task %d started in thread %d".formatted(taskNumber, Thread.currentThread().threadId());
        System.out.println(formatted);
        try {
            Thread.sleep(sleepTimeMs);
            String finished = "Task %d finished in thread %d".formatted(taskNumber, Thread.currentThread().threadId());
            System.out.println(finished);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
