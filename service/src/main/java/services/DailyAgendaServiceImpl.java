package services;

import dao.TaskDAO;
import entities.DailyAgenda;
import entities.Project;
import entities.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.mockito.InjectMocks;

import javax.inject.Inject;

/**
 * Created by anakasimova on 08/07/2018.
 */
public class DailyAgendaServiceImpl implements DailyAgendaService {

    @Inject
    private TaskDAO taskDAO;


    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public DailyAgenda createDailyAgenda(LocalDateTime dailyDate) {
        if(dailyDate == null || !(dailyDate instanceof LocalDateTime)){
            throw new RuntimeException("not legal arguments for creating daily agenda!");
        }
        List<Task> tasks = taskDAO.getTasksByFinishDate(dailyDate);
        DailyAgenda dailyAgenda = new DailyAgenda(dailyDate, tasks);
        return dailyAgenda;
    }

    @Override
    public DailyAgenda createDailyAgenda(LocalDateTime dailyDate, List<Project> projects, List<Task> tasks) {
        if(dailyDate == null
                || !(dailyDate instanceof LocalDateTime)
                || projects == null
                || tasks == null){
            throw new RuntimeException("not legal arguments for creating daily agenda!");
        }
        List<Task> neededTasks = taskDAO.getTasksByFinishDate(dailyDate);
        List<Task> allTasks = new ArrayList<>();
        for (Project project : projects) {
            allTasks.addAll(project.getTasks());
        }
        allTasks.addAll(tasks);
        allTasks.retainAll(neededTasks);
        DailyAgenda dailyAgenda = new DailyAgenda(dailyDate, new ArrayList<>(new HashSet<>(allTasks)));
        return dailyAgenda;
    }
}
