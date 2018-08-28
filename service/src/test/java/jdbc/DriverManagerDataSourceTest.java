package jdbc;

import JDBC.JdbcTemplate;
import JDBC.RowMapper;
import dependencyInversion.context.AnnotationApplicationContext;
import dependencyInversion.context.ApplicationContext;
import dependencyInversion.context.BeanDefinitionSource;
import dependencyInversion.context.ClassPathAnnotationBeanDefinitionSource;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Iana_Kasimova on 28-Aug-18.
 */
public class DriverManagerDataSourceTest {
   private static BeanDefinitionSource source = new ClassPathAnnotationBeanDefinitionSource();

    @Test
    public void testGetDriverManager() throws Exception {
        final ApplicationContext context = new AnnotationApplicationContext(source);
        final JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.executeQuery("DROP TABLE TEST IF EXISTS;");
        jdbcTemplate.executeQuery("CREATE TABLE TEST (ID INT PRIMARY KEY, NAME VARCHAR(255));");
        jdbcTemplate.executeUpdate("insert into TEST values (?, ?)", new Object[]{
                1, "test"
        });
        final Collection<Row> rows = jdbcTemplate.queryForList("select * from TEST", new RowMapper<Row>() {
            @Override
            public Row map(ResultSet resultSet) throws SQLException {
                final Row row = new Row();
                row.setId(resultSet.getInt("ID"));
                row.setName(resultSet.getString("NAME"));
                return row;
            }
        });
        System.out.println(rows);
        assert !rows.isEmpty();
    }

}