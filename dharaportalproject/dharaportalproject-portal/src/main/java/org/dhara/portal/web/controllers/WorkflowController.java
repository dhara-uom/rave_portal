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

import org.apache.rave.model.PortalPreference;
import org.apache.rave.portal.service.PortalPreferenceService;
import org.apache.rave.portal.web.util.ModelKeys;
import org.apache.rave.portal.web.util.PortalPreferenceKeys;
import org.apache.rave.rest.model.SearchResult;
import org.dhara.portal.web.helper.WorkflowHelper;
import org.dhara.portal.web.restClientService.RestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.dhara.portal.web.controllers.GatewayControllerUtil.addNavigationMenusToModel;

/**
 * Controller for the admin pages
 */
@Controller
public class WorkflowController {

    private static final String SELECTED_ITEM = "workflows";

    int DEFAULT_PAGE_SIZE=10;

    @Autowired
    private RestServiceImpl restService;

    @Autowired
    private PortalPreferenceService preferenceService;


    @RequestMapping(value = {"/admin/workflows", "/admin/workflows/"}, method = RequestMethod.GET)
    public String handleRequestInternal(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false) final String action,
                                @RequestParam(required = false) String referringPageId, Model model,
                                HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        model.addAttribute(ModelKeys.REFERRING_PAGE_ID, referringPageId);
        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);

        List<WorkflowHelper> workflowHelperList = restService.getWorkflows();
        int count=workflowHelperList.size();

        workflowHelperList=getLimitedList(offset,count,workflowHelperList);
        final SearchResult<WorkflowHelper> workflows =new SearchResult<WorkflowHelper>(workflowHelperList,count);
        workflows.setOffset(offset);
        workflows.setPageSize(getPageSize());
        model.addAttribute(ModelKeys.SEARCHRESULT, workflows);
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
