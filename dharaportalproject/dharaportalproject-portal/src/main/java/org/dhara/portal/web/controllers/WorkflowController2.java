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

package org.apache.rave.portal.web.controller.admin;

import org.apache.rave.model.Category;
import org.apache.rave.model.User;
import org.apache.rave.portal.model.impl.CategoryImpl;
import org.apache.rave.portal.service.CategoryService;
import org.apache.rave.portal.service.UserService;
import org.apache.rave.portal.web.util.ModelKeys;
import org.apache.rave.portal.web.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;
import org.apache.airavata.workflow.model.wf.Workflow;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.helper.WorkflowHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static org.apache.rave.portal.web.controller.admin.AdminControllerUtil.*;

/**
 * Controller for the admin pages
 */
@Controller

public class WorkflowController2 {

    private static final String SELECTED_ITEM = "gateway";

    @Autowired
    private UserService userService;

    @Autowired
    private AiravataClientAPIService airavataClientAPIService;

    @RequestMapping(value = {"/admin/workflow", "/admin/workflow/"}, method = RequestMethod.GET)
    public String getCategories(@RequestParam(required = false) final String action,
                                @RequestParam(required = false) String referringPageId, Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

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
        model.addAttribute("message", workflowHelpers);


        return "a";
    }

}
