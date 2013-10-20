package org.dhara.portal.web.airavataService;

import org.apache.airavata.client.api.AiravataAPIInvocationException;
import org.apache.airavata.registry.api.exception.worker.ExperimentLazyLoadedException;
import org.apache.airavata.registry.api.workflow.ExperimentData;
import org.apache.airavata.registry.api.workflow.NodeExecutionData;
import org.apache.airavata.workflow.model.wf.Workflow;
import org.dhara.portal.web.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 6/7/13
 * Time: 12:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AiravataClientAPIService {
    /**
     * Get all workflows available in the apache airavata server
     * @return workflow list
     * @throws PortalException
     */
    public List<Workflow> getAllWorkflows() throws PortalException;

    /**
     * Get workflow by workflow id
     * @param identifier unique id for the workflow
     * @return workflow associate with the given workflow id
     * @throws PortalException
     */
    public Workflow getWorkflow(String identifier) throws PortalException;

    /**
     * Execute a available workflow in airavata
     * @param inputs inputs of airavata workflow
     * @param workflowId if of the workflow need to be implemented
     * @return
     * @throws Exception
     */
    public Map<String,Object> executeWorkflow(Map<String,Object> inputs,String workflowId) throws Exception;

    /**
     *  Query previous data associated with experiments performed in airavata
     * @return experiments data list
     * @throws PortalException
     * @throws AiravataAPIInvocationException
     */
    public List<ExperimentData> getExperimentData() throws PortalException, AiravataAPIInvocationException;

    /**
     * Get the data of nodes in a experiment performed in the airavata
     * @param experimentData
     * @return  node execution data list will be returned
     * @throws ExperimentLazyLoadedException
     * @throws PortalException
     * @throws AiravataAPIInvocationException
     */
    public List<NodeExecutionData> getNodeData(ExperimentData experimentData) throws ExperimentLazyLoadedException, PortalException, AiravataAPIInvocationException;

    /**
     * Get workflow experiment data
     * @return
     * @throws PortalException
     * @throws AiravataAPIInvocationException
     * @throws ExperimentLazyLoadedException
     */
    public List<NodeExecutionData> getWorkflowExperimentData(String experimentId) throws PortalException, AiravataAPIInvocationException, ExperimentLazyLoadedException;
}