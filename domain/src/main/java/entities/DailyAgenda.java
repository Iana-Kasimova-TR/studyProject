package entities;

import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
public class DailyAgenda {

    private Date dailyAgendaDate;

    private List<Task> dailyTasks;

    public DailyAgenda(Date dailyDate, List<Task> dailyTasks) {
        this.dailyAgendaDate = dailyDate;
        this.dailyTasks = dailyTasks;
    }

    public List<Task> getDailyTasks() {
        return dailyTasks;
    }
}
