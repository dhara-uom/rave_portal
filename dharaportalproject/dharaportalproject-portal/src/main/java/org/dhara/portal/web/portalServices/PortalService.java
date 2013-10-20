package org.dhara.portal.web.portalServices;

import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowDeployment;
import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowInvocation;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 8/10/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PortalService {
    public void saveWorkfloeDeployment(PersistWorkflowDeployment persistWorkflowDeployment);
    public void saveWorkfloeInvocation(PersistWorkflowInvocation persistWorkflowInvocations);
    public List<PersistWorkflowInvocation> getAllWorkfloeInvocations();
    public List<PersistWorkflowDeployment> getAllWorkfloeDeployments();
}
