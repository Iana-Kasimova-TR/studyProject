package entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anakasimova on 06/07/2018.
 */
@Embeddable
public class TaskId {

    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final int value;

    public int getValue() {
        return value;
    }


    public TaskId(int value) {
        this.value = value;
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof TaskId)) return false;


        TaskId id = (TaskId) o;

        return new EqualsBuilder()
                .append(value, id.value)
                .isEquals();
    }
}
