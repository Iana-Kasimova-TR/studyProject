package dao.daoImpl;

import dao.TaskDAO;
import entities.Task;
import entities.TaskId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Iana_Kasimova on 01-Oct-18.
 */
public class TaskDAOHiberImpl implements TaskDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Task saveOrUpdateTask(Task task) {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(task);
        transaction.commit();
        return null;
    }

    @Override
    public Task getTask(TaskId id) {
        return null;
    }

    @Override
    public List<Task> getTasksByFinishDate(LocalDateTime time) {
        return null;
    }

    @Override
    public List<Task> getTasksByRemindDate(LocalDateTime time) {
        return null;
    }

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
