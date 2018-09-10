package dao.daoImpl;

import dao.ProjectDAO;
import dao.mappers.ProjectMapper;
import dao.mappers.TaskMapper;
import dao.utils.ProjectAttribute;
import entities.Priority;
import entities.Project;
import entities.ProjectId;
import entities.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by anakasimova on 24/07/2018.
 */
@Named
public class ProjectDAOImpl implements ProjectDAO {

    @Inject
    private TaskDAOImpl taskDAO;

    @Inject
    private JdbcTemplate jdbcTemplate;


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTaskDAO(TaskDAOImpl taskDAO) {
        this.taskDAO = taskDAO;
    }


    @Override
    public Project saveOrUpdateProject(Project project) {

        if (project == null) {
            throw new RuntimeException("project should not be null!");
        }

        String sqlQuery;
        Map<ProjectAttribute, Object> params = new EnumMap<>(ProjectAttribute.class);
        params.put(ProjectAttribute.ID, project.getId());
        params.put(ProjectAttribute.TITLE, project.getTitle());
        params.put(ProjectAttribute.DESCRIPTION, project.getDescription());
        params.put(ProjectAttribute.IS_DELETED, project.isDeleted());

        if (params.get(ProjectAttribute.ID) == null) {
            sqlQuery = String.format("INSERT INTO PROJECTS(%s) VALUES (%s)",
                    params.keySet().stream().map(el -> el.name()).collect(Collectors.joining(", ")),
                    params.values().stream().map(el -> "?").collect(Collectors.joining(", "))
            );
        } else {
            sqlQuery = String.format("UPDATE PROJECTS SET %s where %s=%s",
                    params.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().name(), entry -> "?")).entrySet().stream()
                            .collect(Collectors.toList()).toString().replace("[", "").replace("]", ""),
                    params.keySet().stream().filter(el -> el.name().equals("ID")).findAny().orElse(null),
                    params.get(ProjectAttribute.ID)
            );
        }

        ProjectId projectId = new ProjectId(jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlQuery, new String[]{"id"});
            int index = 1;
            for (Map.Entry<ProjectAttribute, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    ps.setNull(index, entry.getKey().typeOfAttribute);
                } else if (entry.getValue() instanceof String) {
                    ps.setString(index, (String) entry.getValue());
                } else if (entry.getValue() instanceof Integer) {
                    ps.setInt(index, (Integer) entry.getValue());
                } else if (entry.getValue() instanceof Boolean) {
                    ps.setBoolean(index, (Boolean) entry.getValue());
                }
                index++;
            }
            return ps;
        }, new GeneratedKeyHolder()));

        project.setId(projectId);

        return project;
    }


    @Override
    public boolean deleteProject(Project project){

        if(project == null){
            throw new RuntimeException("project should not be null!");
        }
        //delete project
        project.setDeleted(true);
        saveOrUpdateProject(project);
        //delete task from project
        for(Task task : project.getTasks()){
            taskDAO.deleteTask(task);
        }

        return true;
    }

    @Override
    public Project getProject(ProjectId id) {
        if(id == null){
            throw new RuntimeException("project should not be null!");
        }

        if(!projectExists(id)){
            return null;
        }

        Project projectFromDB = (Project) jdbcTemplate.queryForObject("select TITLE, DESCRIPTION, IS_DELETED from PROJECTS WHERE ID=?", new ProjectMapper(), id.getValue());
        projectFromDB.setTasks(taskDAO.getTasksFromDBForProject(projectFromDB));
        return  projectFromDB;

    }

    private boolean projectExists(ProjectId id) {
        int count = jdbcTemplate.queryForObject("select count(*) from PROJECTS where ID=?", new Integer[]{id.getValue()}, Integer.class);
        if (count  == 0){
            return false;
        }
        int deleted = jdbcTemplate.queryForObject("select is_deleted from PROJECTS where ID=?", new Integer[]{id.getValue()}, Integer.class);
        if (deleted  == 1){
            return false;
        }
        return true;
    }


}
