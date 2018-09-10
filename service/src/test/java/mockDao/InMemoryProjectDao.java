package mockDao;

import dao.ProjectDAO;
import entities.Project;
import entities.ProjectId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anakasimova on 16/07/2018.
 */
public class InMemoryProjectDao {

  /*  private final Map<ProjectId, Project> storage = new HashMap<>();

    public Project saveOrUpdateProject(Project project){
        if(project.getId() == null){
            project.setId(new ProjectId());
        }
        storage.put(project.getId(), project);
        return project;
    }

    public boolean deleteProject(Project project){
        storage.remove(project.getId());
        return true;
    }

    public Project getProject(ProjectId id){
        return storage.get(id);
    }*/
}
