package entities;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
public class DailyAgenda {

    private final LocalDateTime dailyAgendaDate;

    private final List<Task> dailyTasks;

    public DailyAgenda(LocalDateTime dailyDate, List<Task> dailyTasks) {
        this.dailyAgendaDate = dailyDate;
        this.dailyTasks = dailyTasks;
    }

    public List<Task> getDailyTasks() {
        return Collections.unmodifiableList(dailyTasks);
    }
}
