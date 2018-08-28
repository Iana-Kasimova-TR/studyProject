package JDBC;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Iana_Kasimova on 28-Aug-18.
 */
public class JdbcTemplate {

    @Inject
    private DataSource dataSource;

    public void executeQuery(String query){
        try(final Connection connection = dataSource.getConnection()){
            final Statement statement = connection.createStatement();
            statement.executeQuery(query);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public <T>Collection<T> queryForList(String query, RowMapper<T> mapper, Object... params){
        try(final Connection connection = dataSource.getConnection()){
            final PreparedStatement statement = connection.prepareStatement(query);
            prepareStatement(statement, params);
            try(final ResultSet resultSet = statement.executeQuery();){
                final Collection<T> result = new ArrayList<T>();
                while(resultSet.next()){
                    result.add(mapper.map(resultSet));
                }
                return result;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void prepareStatement(PreparedStatement query, Object[] params) throws SQLException{

        for(int i = 0; i<params.length; i++){
            if(params[i] instanceof String){
                query.setString(i+1, (String) params[i])
            }else if(params[i] instanceof Integer){
                query.setInt(i+1, (int) params[i]);
            }else{
                throw new RuntimeException("not valid type of parameters!");
            }
        }
    }

}
