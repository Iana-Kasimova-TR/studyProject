package utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by anakasimova on 02/10/2018.
 */
public class CustomIdGenerator implements IdentifierGenerator {


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        try {

            String query = String.format("select %s from %s",
                    session.getEntityPersister(obj.getClass().getName(), obj)
                            .getIdentifierPropertyName(),
                    obj.getClass().getSimpleName());

            List<Object> ids = session.createQuery(query).list();

            Integer max = ids.stream().mapToInt(element -> getValueId(element))
                    .max()
                    .orElse(0);


            Field fieldKey = obj.getClass().getDeclaredField("id");
            fieldKey.setAccessible(true);
            Class typeKey = fieldKey.getType();
            Constructor constructor = typeKey.getConstructor(Integer.class);
            Serializable key = (Serializable) constructor.newInstance(new Object[]{(max +1)});
            return key;
        }catch (Exception e){
            throw new RuntimeException("something wrong with generate id! " + e);
        }


    }

    private Integer getValueId(Object element){
        try {
            Field field = element.getClass().getDeclaredField("value");
            field.setAccessible(true);
            return field.getInt(element);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
