package entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof TaskId)) return false;


        TaskId id = (TaskId) o;

        return new EqualsBuilder()
                .append(id, id.id)
                .isEquals();
    }
}
