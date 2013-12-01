package org.dhara.portal.web.portalServices;

import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowDeployment;
import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowInvocation;

import java.util.List;

/**
 * Portal services interface
 */
public interface PortalService {
    /**
     * Save workflow deployment
     * @param persistWorkflowDeployment
     */
    public void saveWorkfloeDeployment(PersistWorkflowDeployment persistWorkflowDeployment);

    /**
     * Save workflow invocation
     * @param persistWorkflowInvocations
     */
    public void saveWorkfloeInvocation(PersistWorkflowInvocation persistWorkflowInvocations);

    /**
     * Get all workflow invocation through portal
     * @return
     */
    public List<PersistWorkflowInvocation> getAllWorkfloeInvocations();

    /**
     * Get all deployed workflows
     * @return
     */
    public List<PersistWorkflowDeployment> getAllWorkfloeDeployments();
}
