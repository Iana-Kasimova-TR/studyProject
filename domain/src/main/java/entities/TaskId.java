package entities;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anakasimova on 06/07/2018.
 */
public class TaskId {

    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final int id;

    public TaskId() {
        this.id = ID_GENERATOR.getAndIncrement();
    }
}
