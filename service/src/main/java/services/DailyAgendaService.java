package services;

import dao.TaskDAO;
import entities.DailyAgenda;
import entities.Project;
import entities.Task;
import validation.NonNull;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface DailyAgendaService {

    DailyAgenda createDailyAgenda(@NonNull LocalDateTime dailyDate);

    DailyAgenda createDailyAgenda(@NonNull LocalDateTime dailyDate, @NonNull List<Project> projects, @NonNull List<Task> tasks);

    void setTaskDAO(@NonNull TaskDAO taskDAO);

}
