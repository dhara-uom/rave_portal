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

import org.apache.airavata.client.AiravataAPIFactory;
import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.AiravataAPIInvocationException;
import org.apache.airavata.client.api.ProvenanceManager;
import org.apache.airavata.client.api.WorkflowManager;
import org.apache.airavata.registry.api.PasswordCallback;
import org.apache.airavata.registry.api.exception.worker.ExperimentLazyLoadedException;
import org.apache.airavata.registry.api.impl.WorkflowExecutionDataImpl;
import org.apache.airavata.registry.api.workflow.ExperimentData;
import org.apache.airavata.registry.api.workflow.NodeExecutionData;
import org.apache.airavata.registry.api.workflow.OutputData;
import org.apache.airavata.rest.client.PasswordCallbackImpl;
import org.apache.airavata.workflow.model.wf.Workflow;
import org.apache.airavata.workflow.model.wf.WorkflowInput;
import org.dhara.portal.web.configuration.AiravataConfig;
import org.dhara.portal.web.configuration.PortalConfiguration;
import org.dhara.portal.web.exception.PortalException;
import org.dhara.portal.web.helper.ExperimentDataHelper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Airavata service implementation
 */
public class AiravataClientAPIServiceImpl extends Observable implements AiravataClientAPIService, Observer {

    private AiravataConfig airavataConfig;
    private PortalConfiguration portalConfiguration;
    private List<MonitorMessage> events = new ArrayList<MonitorMessage>();
    private String experimentId;

    public AiravataClientAPIServiceImpl() {

    }

    /**
     * @see org.dhara.portal.web.airavataService.AiravataClientAPIService#getAllWorkflows()
     */
    public List<Workflow> getAllWorkflows() throws PortalException {
        List<Workflow> workflows = null;
        AiravataAPI airavataAPI=getAiravataAPI();
        WorkflowManager workflowManager=airavataAPI.getWorkflowManager();
        try {
            workflows=workflowManager.getWorkflows();

        } catch (AiravataAPIInvocationException e) {
            e.printStackTrace();
        }
        return workflows;
    }

    /**
     * @see org.dhara.portal.web.airavataService.AiravataClientAPIService#getExperimentData()
     */
    public List<ExperimentData> getExperimentData() throws PortalException, AiravataAPIInvocationException {
        AiravataAPI airavataAPI=getAiravataAPI();
        List<ExperimentData> experimentByUser = airavataAPI.getProvenanceManager().getExperimentDataList();
        return experimentByUser;
    }

    /**
     * @see org.dhara.portal.web.airavataService.AiravataClientAPIService#getWorkflow(String) ()
     */
    public Workflow getWorkflow(String identifier) throws PortalException {
        AiravataAPI airavataAPI=getAiravataAPI();
        Workflow workflow = null;
        WorkflowManager workflowManager=airavataAPI.getWorkflowManager();
        try {
            workflow=workflowManager.getWorkflow(identifier);
        } catch (AiravataAPIInvocationException e) {
            e.printStackTrace();
        }
        return workflow;
    }

    /**
     * @see org.dhara.portal.web.airavataService.AiravataClientAPIService#executeWorkflow(java.util.Map, String) ()
     */
    public Map<String,Object> executeWorkflow(Map<String, Object> inputs, String workflowId) throws Exception {
        AiravataAPI airavataAPI=getAiravataAPI();
        Workflow workflow = airavataAPI.getWorkflowManager().getWorkflow(workflowId);
        List<WorkflowInput> workflowInputs = workflow.getWorkflowInputs();
        for (WorkflowInput workflowInput : workflowInputs) {
            Object value=inputs.get(workflowInput.getName());
            if ("int".equals(workflowInput.getType())||"integer".equals(workflowInput.getType())) {
                workflowInput.setValue((Integer)value);
            } else if("String".equals(workflowInput.getType())){
                workflowInput.setValue((String)value);
            } else {
                workflowInput.setValue((Object)value);
            }
        }

        String experimentId=airavataAPI.getExecutionManager().runExperiment(workflowId, workflowInputs);
        Map<String,Object> outputs=new HashMap<String, Object>();
        MonitorWorkflow monitorWorkflow=new MonitorWorkflow();
        monitorWorkflow.monitor(experimentId,airavataAPI);
        airavataAPI.getExecutionManager().waitForExperimentTermination(experimentId);
        ExperimentData experimentData =airavataAPI.getProvenanceManager().getExperimentData(experimentId);
        List<WorkflowExecutionDataImpl> workflowInstanceData = experimentData.getWorkflowExecutionDataList();
        for (WorkflowExecutionDataImpl executionDataImpl : workflowInstanceData) {
            List<NodeExecutionData> nodeDataList = executionDataImpl.getNodeDataList();
            for (NodeExecutionData nodeExecutionData : nodeDataList) {
                List<OutputData> outputData = nodeExecutionData.getOutputData();
                for (OutputData data : outputData) {
                    outputs.put(data.getName(),data.getValue());
                }
            }
        }
        return outputs;
    }

    /**
     * Create Apache Airavata API instance from configuration
     * @return
     * @throws PortalException
     */
    private AiravataAPI getAiravataAPI() throws PortalException {
        AiravataAPI airavataAPI;
        int port = getAiravataConfig().getPort();
        String serverUrl = getAiravataConfig().getServerUrl();
        String serverContextName = getAiravataConfig().getServerContextName();
        String username = getAiravataConfig().getUserName();
        String password = getAiravataConfig().getPassword();
        String gatewayName = getAiravataConfig().getGatewayName();
        String registryURL = "http://" + serverUrl + ":" + port + "/" + serverContextName + "/api";
        AiravataAPI api= null;
        try{
            PasswordCallback passwordCallback = new PasswordCallbackImpl(username, password);
            api = AiravataAPIFactory.getAPI(new URI(registryURL), gatewayName, username, passwordCallback);
            airavataAPI = api;
        } catch (Exception e) {
            throw new PortalException("Error creating airavata api instance",e);
        }
        return airavataAPI;
    }

    /**
     * @see AiravataClientAPIService#getExperimentData()
     */
    public List<NodeExecutionData> getWorkflowExperimentData(String experimentId) throws PortalException, AiravataAPIInvocationException,
            ExperimentLazyLoadedException {

        ExperimentData data = getAiravataAPI().getProvenanceManager().getExperimentData(experimentId);
        List<NodeExecutionData> nodeData = data.getNodeDataList();

        return nodeData;
    }

    /**
     * @see org.dhara.portal.web.airavataService.AiravataClientAPIService#getNodeData(org.apache.airavata.registry.api.workflow.ExperimentData) ()
     */
    public List<NodeExecutionData> getNodeData(ExperimentData experimentData)
            throws ExperimentLazyLoadedException, PortalException, AiravataAPIInvocationException {
        String experimentId = experimentData.getExperimentId();
        String workflowInstanceId = experimentData.getWorkflowExecutionDataList().get(0).getWorkflowInstanceId();
        ProvenanceManager provenanceManager = getAiravataAPI().getProvenanceManager();
        List<NodeExecutionData> nodeData =
                provenanceManager.getWorkflowInstanceData(experimentId,workflowInstanceId).getNodeDataList();
        return nodeData;
    }

    /**
     * @see org.dhara.portal.web.airavataService.AiravataClientAPIService#executeWorkflow(java.util.Map, String)
     */
    public String executeExperiment(Object[] inputs, String workflowId) throws Exception {

        AiravataAPI airavataAPI=getAiravataAPI();
        Workflow workflow = airavataAPI.getWorkflowManager().getWorkflow(workflowId);

        List<WorkflowInput> workflowInputs = workflow.getWorkflowInputs();
        for (int count =0; count<inputs.length;count++) {
            Object value=inputs[count];
            WorkflowInput workflowInput = workflowInputs.get(count);
            workflowInput.setValue(value);
        }

        String experimentId=airavataAPI.getExecutionManager().runExperiment(workflowId, workflowInputs);
        this.experimentId = experimentId;
        return experimentId;

    }

    /**
     * @see org.dhara.portal.web.airavataService.AiravataClientAPIService#monitorWorkflow(String)
     */
    public void monitorWorkflow(String experimentId) throws PortalException, IOException, AiravataAPIInvocationException, URISyntaxException {
        AiravataAPI airavataAPI=getAiravataAPI();
        MonitorWorkflow monitorWorkflow = new MonitorWorkflow();
        MonitorListener monitorListener = new MonitorListener();
        monitorListener.addObserver(monitorWorkflow);
        monitorWorkflow.addObserver(this);
        MonitorWorkflow.monitorWorkflow(experimentId,airavataAPI,monitorListener);
    }

    /**
     * Notify observers
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        setChanged();
        ExperimentDataHelper experimentDataHelper = new ExperimentDataHelper();
        experimentDataHelper.setExperimentId(this.experimentId);
        experimentDataHelper.setMonitorMessage((MonitorMessage)arg);
        notifyObservers(experimentDataHelper);
    }

    /**
     * @see AiravataClientAPIService#getEvents()
     */
    public List<MonitorMessage> getEvents() {
        return events;
    }

    public void setPortalConfiguration(PortalConfiguration portalConfiguration) {
        this.portalConfiguration = portalConfiguration;
        setAiravataConfig(portalConfiguration.getAiravataConfig());
    }

    public AiravataConfig getAiravataConfig() {
        return airavataConfig;
    }

    public void setAiravataConfig(AiravataConfig airavataConfig) {
        this.airavataConfig = airavataConfig;
    }

    public PortalConfiguration getPortalConfiguration() {
        return portalConfiguration;
    }
}
