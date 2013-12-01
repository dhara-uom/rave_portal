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
package org.dhara.portal.web.controllers;

import org.apache.airavata.client.api.AiravataAPIInvocationException;
import org.dhara.portal.web.airavataService.AiravataClientAPIServiceImpl;
import org.dhara.portal.web.airavataService.MonitorMessage;
import org.dhara.portal.web.exception.PortalException;
import org.dhara.portal.web.helper.ExperimentDataHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Controller class for REST monitoring handling
 */
@Controller
public class RestMonitorAPIController implements Observer {

    private static List<MonitorMessage> events = new ArrayList<MonitorMessage>();
    private static String html ="";
    private AiravataClientAPIServiceImpl apiService = new AiravataClientAPIServiceImpl();
    private static String expId ="";

    @RequestMapping(value = {"/admin/monitorData/{experimentId}", "/admin/monitorData/{experimentId}/"}, method = RequestMethod.GET)
    @ResponseBody
    public String handleRequestInternal(@PathVariable String experimentId, Model model, HttpServletRequest httpServletRequest) throws Exception {

        String temp ="";
        if(expId.equalsIgnoreCase(experimentId)) {

            temp=html;
            html="";
            events = new ArrayList<MonitorMessage>();

        }
        return temp;
    }

    //Register this class as the observer for workflow monitoring
    public String registerObserver(AiravataClientAPIServiceImpl airavataClientAPIService, Object[] ints, String workflowName) throws Exception {
        events = new ArrayList<MonitorMessage>();
        expId ="";
        apiService.setPortalConfiguration(airavataClientAPIService.getPortalConfiguration());
        apiService.setAiravataConfig(airavataClientAPIService.getAiravataConfig());
        apiService.addObserver(this);
        expId = apiService.executeExperiment(ints, workflowName);
        return expId;
    }

    //Start monitoring
    public void monitor() throws URISyntaxException, IOException, AiravataAPIInvocationException, PortalException {
        apiService.monitorWorkflow(expId);
    }

    //Update the data on the time of event receiving
    public void update(Observable o, Object arg) {
        MonitorMessage monitorMessage = ((ExperimentDataHelper)arg).getMonitorMessage();
        this.expId = ((ExperimentDataHelper)arg).getExperimentId();
        html = html+"<tr>" +
                "<td>"+monitorMessage.getTimestamp().toString()+"</td>" +
                "<td>"+monitorMessage.getComonent()+"</td>" +
                "<td>"+monitorMessage.getStatusText()+"</td>" +
                "<td>"+monitorMessage.getMesssage()+"</td>" +
                "</tr>";
    }
}
