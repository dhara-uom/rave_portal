/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.dhara.portal.web.controllers;

import org.apache.airavata.workflow.model.wf.Workflow;
import org.apache.rave.model.PortalPreference;
import org.apache.rave.portal.service.PortalPreferenceService;
import org.apache.rave.portal.web.util.ModelKeys;
import org.apache.rave.portal.web.util.PortalPreferenceKeys;
import org.apache.rave.rest.model.SearchResult;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.helper.ExperimentHelper;
import org.dhara.portal.web.helper.WorkflowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.dhara.portal.web.controllers.GatewayControllerUtil.addNavigationMenusToModel;

/**
 * Controller for the admin pages
 */
@Controller
public class WorkflowController {

    private static final String SELECTED_ITEM = "workflows";

    int DEFAULT_PAGE_SIZE=10;


    @Autowired
    private PortalPreferenceService preferenceService;

    @Autowired
    private AiravataClientAPIService airavataClientAPIService;

    @RequestMapping(value = {"/admin/workflows", "/admin/workflows/"}, method = RequestMethod.GET)
    public String handleRequestInternal(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false) final String action,
                                @RequestParam(required = false) String referringPageId, Model model,
                                HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);
        List<Workflow> workflowList = airavataClientAPIService.getAllWorkflows();

        List<WorkflowHelper> workflowHelpers = new ArrayList<WorkflowHelper>();
        for (int index = 0; index < workflowList.size(); index++) {
            WorkflowHelper helper = new WorkflowHelper();
            Workflow workflow = workflowList.get(index);
            helper.setName(workflow.getName());
            //TODO get author and created data (need to implement methods)
            workflowHelpers.add(helper);
        }

        Map paramMap = WebUtils.getParametersStartingWith(httpServletRequest, "d-");
        if (paramMap.size() == 0) {
            WebUtils.setSessionAttribute(httpServletRequest, "workflows", workflowHelpers);
        }

        int count =workflowHelpers.size();
        workflowHelpers=getLimitedList(offset,count,workflowHelpers);
        final SearchResult<WorkflowHelper> workflows =new SearchResult<WorkflowHelper>(workflowHelpers,count);
        workflows.setOffset(offset);
        workflows.setPageSize(getPageSize());
        model.addAttribute(ModelKeys.SEARCHRESULT, workflows);


//        model.addAttribute("message", workflowHelpers);


        return "templates.admin.workflows";
    }

    private List<WorkflowHelper> getLimitedList(int offset, int count, List<WorkflowHelper> exp){
        List<WorkflowHelper> limited=new ArrayList<WorkflowHelper>();

        int max=count;
        if(count>DEFAULT_PAGE_SIZE+offset)
            max=DEFAULT_PAGE_SIZE+offset;
        int j=0;
        for(int i=offset;i<max;i++){
            limited.add(j,exp.get(i));
            j++;
        }
        return limited;

    }

    public int getPageSize() {
        final PortalPreference pageSizePref = preferenceService.getPreference(PortalPreferenceKeys.PAGE_SIZE);
        if (pageSizePref == null) {
            return DEFAULT_PAGE_SIZE;
        }
        try {
            return Integer.parseInt(pageSizePref.getValue());
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_SIZE;
        }
    }
}
