package services;

import dao.TaskDAO;
import entities.DailyNotificationGroup;
import entities.Project;
import entities.Task;
import dependencyInversion.validation.NonNull;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 08/07/2018.
 */
@Named
@Transactional
public class DailyNotificationGroupServiceImpl implements DailyNotificationGroupService {

    @Inject
    private TaskService taskService;

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public DailyNotificationGroup createDailyNotificationGroup(LocalDateTime dailyDate) {
        List<Task> tasks = taskService.findTaskByRemind(dailyDate);
        DailyNotificationGroup dailyNotificationGroup = new DailyNotificationGroup(dailyDate, tasks);
        return dailyNotificationGroup;
    }

    @Override
    public DailyNotificationGroup createDailyNotifiactionGroup(LocalDateTime dateTime, List<Project> projects, List<Task> tasks) {
        List<Task> allTasks = taskService.findTaskByRemind(dateTime);
        List<Task> neededTasks = new ArrayList<>();
        for(Project project: projects){
            neededTasks.addAll(project.getTasks());
        }
        neededTasks.addAll(tasks);
        allTasks.retainAll(neededTasks);
        DailyNotificationGroup dailyNotificationGroup = new DailyNotificationGroup(dateTime, allTasks);
        return dailyNotificationGroup;
    }



}
