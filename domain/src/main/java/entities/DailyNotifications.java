package entities;

import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public class DailyNotifications {

    private Date dailyNotificationDate;

    private List<Task> dailyReminders;


    public DailyNotifications(Date dailyNotificationDate, List<Task> dailyReminders) {
        this.dailyNotificationDate = dailyNotificationDate;
        this.dailyReminders = dailyReminders;
    }

    public List<Task> getDailyReminders() {
        return dailyReminders;
    }
}
