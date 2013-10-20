package org.dhara.portal.web.dataAccessLayer;

import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowDeployment;
import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowInvocation;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 8/10/13
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class PortalDAOImpl implements PortalDAO{
    private SessionFactory sessionFactory;
    public void saveWorkfloeDeployment(PersistWorkflowDeployment persistWorkflowDeployment) {
        getSessionFactory().getCurrentSession().save(persistWorkflowDeployment);
    }

    public void saveWorkfloeInvocation(PersistWorkflowInvocation persistWorkflowInvocations) {
        getSessionFactory().getCurrentSession().save(persistWorkflowInvocations);
    }

    public List<PersistWorkflowInvocation> getAllWorkfloeInvocations() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PersistWorkflowInvocation.class);
        return (List<PersistWorkflowInvocation>) criteria.list();
    }

    public List<PersistWorkflowDeployment> getAllWorkfloeDeployments() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PersistWorkflowDeployment.class);
        return (List<PersistWorkflowDeployment>) criteria.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
