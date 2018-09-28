package dao.utils;


import entities.Project;
import entities.ProjectId;
import entities.Task;
import entities.TaskId;
import org.mockito.internal.util.reflection.Fields;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by anakasimova on 22/09/2018.
 */
@Named
public class DaoClassManager {

    @Inject
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public  <T> T saveEntity(T entity){

        String sqlQuery;
        List<Field> fieldsOfEntity;

       try {
           if (entity.getClass().isAnnotationPresent(Entity.class)) {

               if (entity.getClass().isAnnotationPresent(Table.class)) {

                   String tableName = entity.getClass().getAnnotation(Table.class).name();
                   fieldsOfEntity = Arrays.asList(entity.getClass().getDeclaredFields()).stream().filter(el -> el.isAnnotationPresent(Column.class)).collect(Collectors.toList());
                   Field identifier = fieldsOfEntity.stream().filter(el -> el.isAnnotationPresent(EmbeddedId.class)).findAny().orElse(null);
                   fieldsOfEntity.remove(identifier);
                   sqlQuery = getQuery(identifier, entity, fieldsOfEntity, tableName);

                   KeyHolder keyHolder = new GeneratedKeyHolder();
                   jdbcTemplate.update(con -> {
                       PreparedStatement ps = con.prepareStatement(sqlQuery, new String[]{"id"});
                       int index = 1;
                       for (Field field : fieldsOfEntity) {
                           try {
                               field.setAccessible(true);
                               if (field.get(entity)== null) {
                                   ps.setNull(index, convertSQLTypeToJavaSQLType(field.getAnnotation(Column.class).columnDefinition()));
                               } else if (String.class.isAssignableFrom(field.getType())) {
                                   ps.setString(index, (String) field.get(entity));
                               } else if (int.class.isAssignableFrom(field.getType())) {
                                   ps.setInt(index, (Integer) field.get(entity));
                               } else if (boolean.class.isAssignableFrom(field.getType())) {
                                   ps.setBoolean(index, (Boolean) field.get(entity));
                               }
                               else if(double.class.isAssignableFrom(field.getType())) {
                                   ps.setDouble(index, (Double) field.get(entity));
                               }
                               else if(Project.class.isAssignableFrom(field.getType())) {
                                   ps.setInt(index, ((Project) field.get(entity)).getId().getValue());
                               }
                               else if(Task.class.isAssignableFrom(field.getType())) {
                                   ps.setInt(index, ((Task) field.get(entity)).getId().getValue());
                               }
                               else if(LocalDateTime.class.isAssignableFrom(field.getType())) {
                                   ps.setDate(index, Date.valueOf(((LocalDateTime) field.get(entity)).toLocalDate()));
                               }
                           } catch (IllegalAccessException e) {
                               throw new RuntimeException(e.getMessage());
                           }
                           index++;
                       }
                       return ps;
                   }, keyHolder);

                   if(identifier.get(entity) == null) {
                       identifier.set(entity, identifier.getType().getDeclaredConstructor(int.class).newInstance(keyHolder.getKey().intValue()));
                   }
               }
           }

       }catch (Exception e){
           throw new RuntimeException(e.getMessage());
       }

       return entity;

    }

    private <T> String getQuery(Field identifier, T entity, List<Field> fieldsOfEntity, String tableName) throws IllegalAccessException {
        identifier.setAccessible(true);
        int id = 0;
        if (identifier.get(entity) == null) {
            return String.format("INSERT INTO " + tableName + "(%s) VALUES (%s)",
                    fieldsOfEntity.stream().map(el -> el.getAnnotation(Column.class).name()).collect(Collectors.joining(", ")),
                    fieldsOfEntity.stream().map(el -> "?").collect(Collectors.joining(", ")));
        }else{

            if(TaskId.class.isAssignableFrom(identifier.getType())){
                id = ((TaskId) identifier.get(entity)).getValue();
            }else if (ProjectId.class.isAssignableFrom(identifier.getType())){
                id = ((ProjectId) identifier.get(entity)).getValue();
            }

            return String.format("UPDATE " + tableName + " SET %s where %s=%s",
                    fieldsOfEntity.stream().map(el -> el.getAnnotation(Column.class).name() + "=?").collect(Collectors.joining(", ")),
                    identifier.getAnnotation(Column.class).name(),
                    id);
        }

    }

    private int convertSQLTypeToJavaSQLType(String type) {
        if(type.equals("VARCHAR(255)")){
            return Types.CHAR;
        }
        else if(type.equals("BOOLEAN")){
            return Types.BOOLEAN;
        }else if(type.equals("DATE")){
            return Types.DATE;
        }else if(type.equals("DOUBLE")){
            return Types.DOUBLE;
        }else if(type.equals("INT")){
            return Types.INTEGER;
        }else{
            throw new RuntimeException("it is not supported type of column definition! " + type);
        }

    }
}
