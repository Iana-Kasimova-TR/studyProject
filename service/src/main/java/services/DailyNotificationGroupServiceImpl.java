package services;

import dao.TaskDAO;
import entities.DailyNotificationGroup;
import entities.Project;
import entities.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 08/07/2018.
 */
public class DailyNotificationGroupServiceImpl implements DailyNotificationGroupService {

    TaskDAO taskDAO;

    public DailyNotificationGroupServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public DailyNotificationGroup createDailyNotificationGroup(LocalDateTime dailyDate) {
        List<Task> tasks = taskDAO.getTasksByRemindDate(dailyDate);
        DailyNotificationGroup dailyNotificationGroup= new DailyNotificationGroup(dailyDate, tasks);
        return dailyNotificationGroup;
    }

    @Override
    public DailyNotificationGroup createDailyNotifiactionGroup(LocalDateTime dateTime, List<Project> projects, List<Task> tasks) {
        List<Task> allTasks = taskDAO.getTasksByRemindDate(dateTime);
        List<Task> neededTasks = new ArrayList<>();
        for(Project project: projects){
            neededTasks.addAll(project.getTasks());
        }
        neededTasks.addAll(tasks);
        allTasks.retainAll(neededTasks);
        DailyNotificationGroup dailyNotificationGroup= new DailyNotificationGroup(dateTime, allTasks);
        return dailyNotificationGroup;
    }
}
