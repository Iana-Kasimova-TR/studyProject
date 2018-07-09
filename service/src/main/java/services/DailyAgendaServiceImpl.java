package services;

import dao.TaskDAO;
import dao.TaskDAOImpl;
import entities.DailyAgenda;
import entities.Project;
import entities.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 08/07/2018.
 */
public class DailyAgendaServiceImpl implements DailyAgendaService {

    TaskDAO taskDAO = new TaskDAOImpl();

    @Override
    public DailyAgenda createDailyAgenda(LocalDateTime dailyDate) {
        List<Task> tasks = taskDAO.getTasksByFinishDate(dailyDate);
        DailyAgenda dailyAgenda = new DailyAgenda(dailyDate, tasks);
        return dailyAgenda;
    }

    @Override
    public DailyAgenda createDailyAgenda(LocalDateTime dailyDate, List<Project> projects, List<Task> tasks) {
        List<Task> allTasks = taskDAO.getTasksByFinishDate(dailyDate);
        List<Task> neededTasks = new ArrayList<>();
        for (Project project : projects) {
            neededTasks.addAll(project.getTasks());
        }
        neededTasks.addAll(tasks);
        allTasks.retainAll(neededTasks);
        DailyAgenda dailyAgenda = new DailyAgenda(dailyDate, allTasks);
        return dailyAgenda;
    }
}
