package entities.hibernateusertype;

import entities.TaskId;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

/**
 * Created by anakasimova on 02/10/2018.
 */
public class TaskIdHibernateUserType extends AbstractSingleColumnStandardBasicType {

    public TaskIdHibernateUserType() {
        super(IntegerType.INSTANCE.getSqlTypeDescriptor(), new JavaType());
    }

    @Override
    public String getName() {
        return null;
    }

    public static class JavaType extends AbstractTypeDescriptor<TaskId> {
        public JavaType() {
            super(TaskId.class);
        }

        @Override
        public TaskId fromString(String string) {
            return null;
        }

        @Override
        public <X> X unwrap(TaskId value, Class<X> type, WrapperOptions options) {
            return (X) Integer.valueOf(value.getValue());
        }

        @Override
        public <X> TaskId wrap(X value, WrapperOptions options) {
            return new TaskId((Integer) value);
        }
    }
}
