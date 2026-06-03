package net.replaceitem.discarpet.util;

import java.util.concurrent.*;

public class InterruptibleFuture {
    private static final ScheduledExecutorService TIMEOUT_SCHEDULER = Executors.newSingleThreadScheduledExecutor(Thread.ofPlatform().daemon().factory());

    public static <T> CompletableFuture<T> supplyAsyncWithTimeout(InterruptibleSupplier<T> supplier, long time, TimeUnit timeUnit, ExecutorService executor, String interruptedMessage) {
        var resultFuture = new CompletableFuture<T>();
        var future = executor.submit(() -> {
            try {
                if(resultFuture.isDone()) return;
                var res = supplier.get();
                resultFuture.complete(res);
            } catch (Exception e) {
                resultFuture.completeExceptionally(e);
            }
        });
        var timeoutTask = TIMEOUT_SCHEDULER.schedule(() -> {
            if(resultFuture.isDone()) return;
            var completed = resultFuture.completeExceptionally(new TimeoutException(interruptedMessage));
            if(completed) future.cancel(true);
        }, time, timeUnit);
        resultFuture.whenComplete((_, _) -> timeoutTask.cancel(false));
        return resultFuture;
    }

    public interface InterruptibleSupplier<T> {
        T get() throws InterruptedException;
    }
}
