package dao.daoImpl;

import dao.TaskDAO;
import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Iana_Kasimova on 01-Oct-18.
 */
@Repository("TaskDAOHiber")
public class TaskDAOHiberImpl implements TaskDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Task saveOrUpdateTask(Task task) {
        Session session = getCurrentSession();
        TaskId id = (TaskId) session.save(task);
        task.setId(id);
        return task;
    }

    @Override
    public Task getTask(TaskId id) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Task_.id), id), builder.equal(root.get(Task_.deleted), false));
        return getCurrentSession().createQuery(criteria).getSingleResult();
    }

    @Override
    public List<Task> getTasksByFinishDate(LocalDateTime time) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Task_.deadline), time));
        criteria.where(builder.equal(root.get(Task_.deleted), false));
        return getCurrentSession().createQuery(criteria).getResultList();
    }

    @Override
    public List<Task> getTasksByRemindDate(LocalDateTime time) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Task_.remindDate), time));
        criteria.where(builder.equal(root.get(Task_.deleted), false));
        return getCurrentSession().createQuery(criteria).getResultList();
    }

    @Override
    public Collection<Task> getAllTasks() {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);
        criteria.select(root);
        return getCurrentSession().createQuery(criteria).getResultList();
    }

    @Override
    public Collection<Task> getTasksForProject(Project project) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Task_.project), project), builder.equal(root.get(Task_.deleted), false));
        return getCurrentSession().createQuery(criteria).getResultList();
    }

    protected Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }
}
