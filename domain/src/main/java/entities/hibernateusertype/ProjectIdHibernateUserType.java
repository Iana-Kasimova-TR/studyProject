package entities.hibernateusertype;

import entities.ProjectId;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

public class ProjectIdHibernateUserType extends AbstractSingleColumnStandardBasicType {

    public ProjectIdHibernateUserType() {
        super(IntegerType.INSTANCE.getSqlTypeDescriptor(), new JavaType());
    }

    @Override
    public String getName() {
        return null;
    }

    public static class JavaType extends AbstractTypeDescriptor<ProjectId> {
        public JavaType() {
            super(ProjectId.class);
        }

        @Override
        public ProjectId fromString(String string) {
            return null;
        }

        @Override
        public <X> X unwrap(ProjectId value, Class<X> type, WrapperOptions options) {
            return (X) Integer.valueOf(value.getValue());
        }

        @Override
        public <X> ProjectId wrap(X value, WrapperOptions options) {
            return new ProjectId((Integer) value);
        }
    }
}
