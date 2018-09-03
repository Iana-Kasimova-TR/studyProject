package services;

import entities.DailyAgenda;
import entities.Project;
import entities.Task;
import dependencyInversion.validation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface DailyAgendaService {

    DailyAgenda createDailyAgenda(@NonNull LocalDateTime dailyDate);

    DailyAgenda createDailyAgenda(@NonNull LocalDateTime dailyDate, @NonNull List<Project> projects, @NonNull List<Task> tasks);

}
