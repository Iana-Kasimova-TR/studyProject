package dao.mappers;

import entities.Priority;
import entities.Task;
import entities.TaskId;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Iana_Kasimova on 03-Sep-18.
 */
public class TaskMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task(rs.getString("task_name"));
        task.setDescription(rs.getString("task_description"));
        task.setDeadline(LocalDateTime.ofInstant(rs.getDate("task_deadline").toInstant(), ZoneId.systemDefault()));
        task.setPercentOfReadiness(rs.getLong("task_percent_of_readiness"));
        task.setPriority(Priority.valueOf(rs.getString("task_priority")));
        task.setId(new TaskId(rs.getInt("tas_id")));
        task.setDone(rs.getBoolean("task_is_done"));
        task.setRemindDate(LocalDateTime.ofInstant(rs.getDate("task_remind_date").toInstant(), ZoneId.systemDefault()));
        return task;
    }
}
