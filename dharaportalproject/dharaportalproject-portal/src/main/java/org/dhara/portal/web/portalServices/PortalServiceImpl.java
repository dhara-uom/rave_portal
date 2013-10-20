package org.dhara.portal.web.portalServices;

import org.dhara.portal.web.dataAccessLayer.PortalDAO;
import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowDeployment;
import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowInvocation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 8/10/13
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class PortalServiceImpl implements PortalService{
    private PortalDAO portalDAO;

    public PortalDAO getPortalDAO() {
        return portalDAO;
    }

    public void setPortalDAO(PortalDAO portalDAO) {
        this.portalDAO = portalDAO;
    }

    public void saveWorkfloeDeployment(PersistWorkflowDeployment persistWorkflowDeployment) {
        portalDAO.saveWorkfloeDeployment(persistWorkflowDeployment);
    }

    public void saveWorkfloeInvocation(PersistWorkflowInvocation persistWorkflowInvocations) {
        portalDAO.saveWorkfloeInvocation(persistWorkflowInvocations);
    }

    public List<PersistWorkflowInvocation> getAllWorkfloeInvocations() {
        return portalDAO.getAllWorkfloeInvocations();
    }

    public List<PersistWorkflowDeployment> getAllWorkfloeDeployments() {
        return portalDAO.getAllWorkfloeDeployments();
    }
}
