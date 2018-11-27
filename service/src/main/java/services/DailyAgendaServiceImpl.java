package services;

import entities.DailyAgenda;
import entities.Project;
import entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by anakasimova on 08/07/2018.
 */
//@Named
@Transactional
@Service
public class DailyAgendaServiceImpl implements DailyAgendaService {

    @Inject
    private TaskService taskService;

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public DailyAgenda createDailyAgenda(LocalDateTime dailyDate) {
        List<Task> tasks = taskService.findTaskByDeadline(dailyDate);
        DailyAgenda dailyAgenda = new DailyAgenda(dailyDate, tasks);
        return dailyAgenda;
    }

    @Override
    public DailyAgenda createDailyAgenda(LocalDateTime dailyDate, List<Project> projects, List<Task> tasks) {
        List<Task> neededTasks = taskService.findTaskByDeadline(dailyDate);
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
