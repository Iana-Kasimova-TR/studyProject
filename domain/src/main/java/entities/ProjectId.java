package entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anakasimova on 06/07/2018.
 */
public class ProjectId {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    public int getValue() {
        return value;
    }

    private final int value;

    public ProjectId() {
        this.value = ID_GENERATOR.getAndIncrement();
    }

    public ProjectId(int value){
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
        if(!(o instanceof ProjectId)) return false;


        ProjectId id = (ProjectId) o;

        return new EqualsBuilder()
                .append(id, id.value)
                .isEquals();
    }
}
