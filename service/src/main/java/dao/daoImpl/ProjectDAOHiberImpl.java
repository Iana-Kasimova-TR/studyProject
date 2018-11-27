package dao.daoImpl;

import dao.ProjectDAO;
import entities.Project;
import entities.ProjectId;
import entities.Project_;
import org.apache.commons.logging.impl.Jdk14Logger;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Created by Iana_Kasimova on 01-Oct-18.
 */
@Repository("ProjectDAOHiber")
public class ProjectDAOHiberImpl implements ProjectDAO {
    private static final Logger LOGGER = Logger.getLogger(ProjectDAOHiberImpl.class.getName());
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Project saveOrUpdateProject(Project project) {
        Session session = getCurrentSession();
        ProjectId id = (ProjectId) session.save(project);
        project.setId(id);
        return project;
    }

    @Override
    public Collection<Project> findAll() {
        LOGGER.info("ProjectDAOHiberImpl");
        return getCurrentSession()
                .createQuery("from Project")
                .getResultList();
    }

    @Override
    public Project getProject(ProjectId id) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
        Root<Project> root = criteria.from(Project.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Project_.id), id), builder.equal(root.get(Project_.deleted), false));
        return getCurrentSession().createQuery(criteria).getSingleResult();
    }

    private Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }

}
