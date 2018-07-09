package entities;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public class DailyNotificationGroup {

    private final LocalDateTime dailyNotificationDate;

    private final List<Task> dailyReminders;

    public DailyNotificationGroup(LocalDateTime dailyNotificationDate, List<Task> dailyReminders) {
        this.dailyNotificationDate = dailyNotificationDate;
        this.dailyReminders = dailyReminders;
    }

    public List<Task> getDailyReminders() {
        return Collections.unmodifiableList(dailyReminders);
    }
}
