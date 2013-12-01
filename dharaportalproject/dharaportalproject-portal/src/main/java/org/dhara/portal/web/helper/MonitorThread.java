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
package org.dhara.portal.web.helper;

import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.airavataService.AiravataClientAPIServiceImpl;
import org.dhara.portal.web.controllers.RestMonitorAPIController;

/**
 * Helper thread class for workflow monitoring
 */
public class MonitorThread implements Runnable {

    private String name;
    private Object[] inputs;

    private AiravataClientAPIServiceImpl airavataClientAPIService;
    private RestMonitorAPIController restWorkflowMonitorAP;

    public MonitorThread(AiravataClientAPIService airavataClientAPIService,String threadName, Object[] inputs){
        this.name = threadName;
        this.inputs = inputs;
        AiravataClientAPIServiceImpl temp = new AiravataClientAPIServiceImpl();
        temp.setPortalConfiguration(((AiravataClientAPIServiceImpl)airavataClientAPIService).getPortalConfiguration());
        temp.setAiravataConfig(((AiravataClientAPIServiceImpl)airavataClientAPIService).getAiravataConfig());
        this.airavataClientAPIService = temp;
        this.restWorkflowMonitorAP = new RestMonitorAPIController();
    }

    public String getExperimentId() throws Exception {
        String id = this.restWorkflowMonitorAP.registerObserver(airavataClientAPIService,inputs, name);
        return id;
    }

    public void run(){
        try {
            this.restWorkflowMonitorAP.monitor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Object[] getInputs() {
        return inputs;
    }

    public void setInputs(Object[] inputs) {
        this.inputs = inputs;
    }
}
