import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class FixedThreadPool {
    private final Thread[] threads;
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private volatile boolean isShutdown = false;

    public FixedThreadPool(int poolSize) {
        threads = new Thread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            Thread thread = new Thread(() -> {
                while(true) {
                    Runnable task;
                    synchronized (taskQueue) {
                        task = taskQueue.poll();
                        if (null == task) {
                            if (isShutdown) {
                                System.out.println("Thread " + Thread.currentThread().threadId() + " finished. Going out.");
                                break;
                            }
                            try {
                                System.out.println("Thread " + Thread.currentThread().threadId() + " waits");
                                taskQueue.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    if (task != null) {
                        System.out.println("Thread " + Thread.currentThread().threadId() + " works");
                        task.run();
                    }
                }
            });
            thread.start();
            threads[i] = thread;
        }
    }

    public void execute(Runnable runnable) {
        synchronized (taskQueue) {
            if (isShutdown) {
                throw new IllegalStateException("FixedThreadPool is going to shutdown");
            }
            taskQueue.add(runnable);
            taskQueue.notifyAll();
        }
    }

    public void shutdown() {
        synchronized (taskQueue) {
            isShutdown = true;
            taskQueue.notifyAll();
        }
    }

    public void awaitTermination(int timeout, TimeUnit timeUnit) {
        synchronized (taskQueue) {
            isShutdown = true;
            long millis = timeUnit.toMillis(timeout);
            try {
                taskQueue.wait(millis);
                System.out.println("Interrupt every one");
                for (Thread thread : threads) {
                    thread.interrupt();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
