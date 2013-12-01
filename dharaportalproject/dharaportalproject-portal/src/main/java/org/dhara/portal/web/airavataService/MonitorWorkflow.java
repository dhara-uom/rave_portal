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

import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.AiravataAPIInvocationException;
import org.apache.airavata.ws.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;

public class MonitorWorkflow extends Observable implements Observer {
    private static final Logger log = LoggerFactory.getLogger(MonitorWorkflow.class);

    /**
     * Set workflow monitoring configurations and start monitoring
     * @param experimentId experiment id of executed workflow
     * @param airavataAPI  airavata API instance
     * @throws AiravataAPIInvocationException
     * @throws URISyntaxException
     */
    public void monitor(final String experimentId,AiravataAPI airavataAPI) throws AiravataAPIInvocationException, URISyntaxException {
        MonitorListener monitorListener = new MonitorListener();
        Monitor experimentMonitor = airavataAPI.getExecutionManager().getExperimentMonitor(experimentId,
                monitorListener);
        log.info("Started the Workflow monitor");
        experimentMonitor.startMonitoring();
    }

    /**
     * Set workflow monitoring configurations and start monitoring
     * @param experimentId experiment id of executed workflow
     * @param airavataAPI  airavata API instance
     * @param monitorListener
     * @throws AiravataAPIInvocationException
     * @throws URISyntaxException
     */
    public static void monitorWorkflow(final String experimentId,AiravataAPI airavataAPI,
                                       MonitorListener monitorListener) throws AiravataAPIInvocationException, URISyntaxException, IOException {
        Monitor experimentMonitor = airavataAPI.getExecutionManager().getExperimentMonitor(experimentId,
                monitorListener);

        experimentMonitor.startMonitoring();
        airavataAPI.getExecutionManager().waitForExperimentTermination(experimentId);
        experimentMonitor.stopMonitoring();

    }



    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);

    }


}
