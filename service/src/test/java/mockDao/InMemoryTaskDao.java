package mockDao;

import dao.TaskDAO;
import entities.Task;
import entities.TaskId;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by anakasimova on 10/07/2018.
 */
public class InMemoryTaskDao implements TaskDAO {
    private final Map<TaskId, Task> storage = new HashMap<>();

    @Override
    public Task saveOrUpdateTask(Task task) {
        if (task.getId() == null) {
            task.setId(new TaskId());
        }
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public boolean deleteTask(Task task) {
        storage.remove(task.getId());
        return true;
    }

    @Override
    public Task getTask(TaskId id) {
        return storage.get(id);
    }

    @Override
    public List<Task> getTasksByFinishDate(LocalDateTime time) {
        return storage.values().stream().filter( task -> task.getDeadline() !=null)
                .filter(task -> task.getDeadline().equals(time))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getTasksByRemindDate(LocalDateTime time) {
        return null;
    }
}
