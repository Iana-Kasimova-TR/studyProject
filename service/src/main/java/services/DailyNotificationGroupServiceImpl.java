package services;

import dao.TaskDAO;
import entities.DailyNotificationGroup;
import entities.Project;
import entities.Task;
import validation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 08/07/2018.
 */
@Named
public class DailyNotificationGroupServiceImpl implements DailyNotificationGroupService {

    @Inject
    private TaskDAO taskDAO;

    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public DailyNotificationGroup createDailyNotificationGroup( @NonNull LocalDateTime dailyDate) {
        List<Task> tasks = taskDAO.getTasksByRemindDate(dailyDate);
        DailyNotificationGroup dailyNotificationGroup = new DailyNotificationGroup(dailyDate, tasks);
        return dailyNotificationGroup;
    }

    @Override
    public DailyNotificationGroup createDailyNotifiactionGroup(@NonNull LocalDateTime dateTime, @NonNull List<Project> projects, @NonNull List<Task> tasks) {
        List<Task> allTasks = taskDAO.getTasksByRemindDate(dateTime);
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
