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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.airavataService.MonitorMessage;
import org.dhara.portal.web.configuration.PortalConfiguration;
import org.dhara.portal.web.helper.InputHelper;
import org.dhara.portal.web.helper.MonitorThread;
import org.dhara.portal.web.helper.UserInputHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static org.dhara.portal.web.controllers.GatewayControllerUtil.addNavigationMenusToModel;

/**
 * Workflow monitor handling controller class
 */
@Controller
public class WorkflowMonitorController {
    protected final Log log = LogFactory.getLog(getClass());
    private List<MonitorMessage> events = new ArrayList<MonitorMessage>();
    private static final String SELECTED_ITEM = "workflows";

    @Autowired
    private AiravataClientAPIService airavataClientAPIService;

    @RequestMapping(value = {"/admin/monitoring", "/admin/monitoring/"}, method = RequestMethod.GET)
    protected String handleRequestInternal(@RequestParam(required = false) final String action,
                                           @RequestParam(required = false) String referringPageId, Model model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);
        String workflowName=request.getParameter("workflowId");
        List<InputHelper> inputHelperList = new ArrayList<InputHelper>();
        //Get user specified inputs from the form
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()){
            String temp = (String) enumeration.nextElement();
            if(temp.equalsIgnoreCase("workflowId"));
            else {
                InputHelper inputHelper = new InputHelper();
                inputHelper.setRawName(temp);
                inputHelper.setName(temp.split("/")[0]);
                inputHelper.setType(temp.split("/")[1]);
                inputHelper.setValues(request.getParameterValues(temp));
                inputHelperList.add(inputHelper);
            }
        }

        List<UserInputHelper> inputs = new ArrayList<UserInputHelper>();

        for (InputHelper in:inputHelperList){
            UserInputHelper userInputHelper = new UserInputHelper();
            userInputHelper.setName(in.getName());
            for (int i=0;i<in.getValues().length;i++){
                userInputHelper.setValue(in.getValues()[i]);
            }
            inputs.add(userInputHelper);
        }

        Object[] ints = inputs.toArray();

        //Start workflow monitoring
        Runnable monitorThread = new MonitorThread(airavataClientAPIService,workflowName,ints);
        String id = ((MonitorThread)monitorThread).getExperimentId();
        PortalConfiguration.executor.execute(monitorThread);

        model.addAttribute("id",id);
        model.addAttribute("workflowId", workflowName);
        return "templates.admin.monitoring";
    }
}
