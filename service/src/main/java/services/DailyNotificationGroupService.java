package services;

import entities.DailyNotificationGroup;
import entities.Project;
import entities.Task;
import validation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface DailyNotificationGroupService{

    DailyNotificationGroup createDailyNotificationGroup(@NonNull LocalDateTime dailyDate);

    DailyNotificationGroup createDailyNotifiactionGroup(@NonNull LocalDateTime dateTime, @NonNull List<Project> projects, @NonNull List<Task> tasks);
}
