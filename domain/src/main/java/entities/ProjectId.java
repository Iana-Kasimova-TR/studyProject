package entities;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anakasimova on 06/07/2018.
 */
public class ProjectId {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final int id;

    public ProjectId() {
        this.id = ID_GENERATOR.getAndIncrement();
    }
}
