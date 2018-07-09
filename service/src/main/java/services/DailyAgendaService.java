package services;

import entities.DailyAgenda;
import entities.Project;
import entities.Task;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface DailyAgendaService {

    DailyAgenda createDailyAgenda(LocalDateTime dailyDate);

    DailyAgenda createDailyAgenda(LocalDateTime dailyDate, List<Project> projects, List<Task> tasks);

}
