/***********************************************************************************************************************
 *
 * Dhara- A Geoscience Gateway
 * ==========================================
 *
 * Copyright (C) 2013 by Dhara
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/
package org.dhara.portal.web.airavataService;

import org.apache.airavata.client.api.AiravataAPIInvocationException;
import org.apache.airavata.registry.api.exception.worker.ExperimentLazyLoadedException;
import org.apache.airavata.registry.api.workflow.ExperimentData;
import org.apache.airavata.registry.api.workflow.NodeExecutionData;
import org.apache.airavata.workflow.model.wf.Workflow;
import org.dhara.portal.web.exception.PortalException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Apache Airavata Service interface
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

    /**
     * Execute a workflow through API
     * @param inputs workflow inputs
     * @param workflowId workflow identifier
     * @return
     * @throws Exception
     */
    public String executeExperiment(Object[] inputs, String workflowId) throws Exception;

    /**
     * Get events of executed workflow
     * @return
     */
    public List<MonitorMessage> getEvents();

    /**
     * Start workflow monitoring
     * @param experimentId experiement id of executed workflow
     * @throws PortalException
     * @throws IOException
     * @throws AiravataAPIInvocationException
     * @throws URISyntaxException
     */
    public void monitorWorkflow(String experimentId) throws PortalException, IOException, AiravataAPIInvocationException, URISyntaxException;
}