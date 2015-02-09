package at.fhv.puzzle2.communication;

import java.util.function.Consumer;

public class ThreadUtil {
    public static <T> void runInThread(Consumer<T> consumer, T element) {
        Runnable runnable = () -> consumer.accept(element);

        new Thread(runnable).start();
    }
}
