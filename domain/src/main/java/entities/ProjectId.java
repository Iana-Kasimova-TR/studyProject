package entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anakasimova on 06/07/2018.
 */
public class ProjectId implements Serializable{

    private int value;

    public int getValue() {
        return value;
    }

    public ProjectId(Integer value){
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
                .append(value, id.value)
                .isEquals();
    }
}
