package services;

import entities.DailyNotificationGroup;
import entities.Project;
import entities.Task;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface DailyNotificationGroupService{

    DailyNotificationGroup createDailyNotificationGroup(LocalDateTime dailyDate);

    DailyNotificationGroup createDailyNotifiactionGroup(LocalDateTime dateTime, List<Project> projects, List<Task> tasks);
}
