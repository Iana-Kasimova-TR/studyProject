package entities.equators;

import entities.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Equator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by anakasimova on 26/09/2018.
 */
public class TaskEquator implements Equator<Task>{
    @Override
    public boolean equate(Task o1, Task o2) {
        boolean isEquals;

        if(o1 == o2) return true;

        isEquals =  new EqualsBuilder()
                .append(o1.getId(), o2.getId())
                .append(o1.getTitle(), o2.getTitle())
                .append(o1.getDescription(), o2.getDescription())
                .append(o1.isDone(), o2.isDone())
                .append(o1.getDeadline(), o2.getDeadline())
                .append(o1.getRemindDate(), o2.getRemindDate())
                .append(o1.getPriority(), o2.getPriority())
                .append(o1.getPercentOfReadiness(), o2.getPercentOfReadiness())
                .isEquals();

        if(isEquals){
            isEquals = CollectionUtils.isEqualCollection(o1.getSubTasks(), o2.getSubTasks(), new TaskEquator());
        }

        return isEquals;
    }

    @Override
    public int hash(Task o) {
        return new HashCodeBuilder(17,37)
                .append(o.getId())
                .append(o.getTitle())
                .append(o.getDescription())
                .append(o.isDone())
                .append(o.getDeadline())
                .append(o.getRemindDate())
                .append(o.getPriority())
                .append(o.getPercentOfReadiness())
                .append(o.isDeleted())
                .append(o.isDeletedFromProject())
                .toHashCode();
    }
}
