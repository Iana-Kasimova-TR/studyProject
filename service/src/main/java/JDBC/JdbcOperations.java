package JDBC;

import java.util.List;

/**
 * Created by Iana_Kasimova on 22-Aug-18.
 */
public interface JdbcOperations {
    <T> T execute(ConnectionCallback<T> var1) throws DataAccessException;

    <T> T query(String var1, ResultSetExtractor<T> var2) throws DataAccessException;

    <T> T queryForObject(String var1, Object[] var2, Class<T> var3) throws DataAccessException;

    SqlRowSet queryForRowSet(String var1) throws DataAccessException;

    int[] batchUpdate(String var1, List<Object[]> var2) throws DataAccessException;
}
