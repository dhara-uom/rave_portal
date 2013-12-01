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
import org.dhara.portal.web.codegenService.CodeGenService;
import org.dhara.portal.web.wps52NorthService.WPS52NorthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Workflow default deployment controller
 */
@Controller
public class WorkflowDeploymentController {

    @Autowired
    private AiravataClientAPIService airavataClientAPIService;

    @Autowired
    private CodeGenService codeGenService;

    @Autowired
    private WPS52NorthService wpsConnect52Service;

    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = {"/admin/workflow/deploy", "/admin/workflow/deploy/"}, method = RequestMethod.GET)
    public String handleRequestInternal(@RequestParam(required = false) final String action,
                                           @RequestParam(required = false) String referringPageId,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String workflowId=request.getParameter("workflowId");
        //Generate code
        String generatedCode=codeGenService.getGeneratedClass(workflowId);
        //Deploy in the backend wps configuration
        wpsConnect52Service.uploadClass(generatedCode,workflowId);
//        request.getSession().setAttribute("successMessage","Workflow is successfully deployed in wps instance");
        return "redirect:/app/admin/workflows?action=deployed";
    }

}
