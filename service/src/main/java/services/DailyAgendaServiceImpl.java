package services;

import dao.TaskDAO;
import entities.DailyAgenda;
import entities.Project;
import entities.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by anakasimova on 08/07/2018.
 */
@Named
public class DailyAgendaServiceImpl implements DailyAgendaService {

    @Inject
    @Named("taskDAO")
    private TaskDAO taskDAO;


    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public DailyAgenda createDailyAgenda(LocalDateTime dailyDate) {
        List<Task> tasks = taskDAO.getTasksByFinishDate(dailyDate);
        DailyAgenda dailyAgenda = new DailyAgenda(dailyDate, tasks);
        return dailyAgenda;
    }

    @Override
    public DailyAgenda createDailyAgenda(LocalDateTime dailyDate, List<Project> projects, List<Task> tasks) {
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
