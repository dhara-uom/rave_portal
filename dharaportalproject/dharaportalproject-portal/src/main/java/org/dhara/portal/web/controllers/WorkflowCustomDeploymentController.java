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

import org.apache.airavata.workflow.model.component.ws.WSComponentPort;
import org.apache.airavata.workflow.model.wf.Workflow;
import org.apache.airavata.workflow.model.wf.WorkflowInput;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.codegenService.CodeGenService;
import org.dhara.portal.web.helper.MappingHelper;
import org.dhara.portal.web.helper.WorkflowHelper;
import org.dhara.portal.web.wps52NorthService.WPS52NorthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dhara.portal.web.controllers.GatewayControllerUtil.addNavigationMenusToModel;

/**
 * Workflow custom deployment controller class
 */
@Controller
public class WorkflowCustomDeploymentController{

    private static final String SELECTED_ITEM = "workflows";

    @Autowired
    private AiravataClientAPIService airavataClientAPIService;

    @Autowired
    private WPS52NorthService wpsConnect52Service;

    @Autowired
    private CodeGenService codeGenService;

    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = {"/admin/workflow/deploy"}, method = RequestMethod.POST)
    public String customDeployment(@RequestParam(required = false) final String action,
                              @RequestParam(required = false) String referringPageId, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception {

        String workflowId=request.getParameter("workflowId");
        String extendingAlgorithm=request.getParameter("extendingAlgo");
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        Map<String,String> inputMapping=new HashMap<String, String>();
        Map<String,String> outputMapping=new HashMap<String, String>();
        //Map inputs with user specified types
        for(WSComponentPort input:workflow.getInputs()) {
            inputMapping.put(input.getName(),request.getParameter(input.getName()));
        }

        //Map outputs with user specified types
        for(WSComponentPort workflowOutput : workflow.getOutputs()) {
            outputMapping.put(workflowOutput.getName(),request.getParameter(workflowOutput.getName()));
        }

        //Generate code
        codeGenService.getGeneratedClassForCustomDeployment(workflowId,inputMapping,outputMapping,extendingAlgorithm);
        String generatedCode=codeGenService.getGeneratedClass(workflowId);
        //Deploy in the WPS instance
        wpsConnect52Service.uploadClass(generatedCode,workflowId);

        return "redirect:/app/admin/workflows";
    }

    @RequestMapping(value = {"/admin/workflow/customdeploy", "/admin/workflow/custom/"}, method = RequestMethod.GET)
    public String showDeployForm(@RequestParam(required = false) final String action,
                                 @RequestParam(required = false) String referringPageId, Model model,HttpServletRequest request) throws Exception {
        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);
        String workflowId=request.getParameter("workflowId");
        List<WorkflowHelper> workflowHelpers=new ArrayList<WorkflowHelper>();
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        List<MappingHelper> inputNodes=new ArrayList<MappingHelper>();
        List<MappingHelper> outputNodes=new ArrayList<MappingHelper>();

        //Set input data type mappings
        for(WSComponentPort workflowInput:workflow.getInputs()) {
            MappingHelper mappingHelper=new MappingHelper();
            mappingHelper.setNodeName(workflowInput.getName());
            mappingHelper.setExistingMapping(workflowInput.getType().getLocalPart());
            inputNodes.add(mappingHelper);
        }

        //Set output data type mappings
        for(WSComponentPort workflowOutput : workflow.getOutputs()) {
            MappingHelper mappingHelper=new MappingHelper();
            mappingHelper.setNodeName(workflowOutput.getName());
            mappingHelper.setExistingMapping(workflowOutput.getType().getLocalPart());
            outputNodes.add(mappingHelper);
        }

        model.addAttribute("workflowList",workflowHelpers);
        model.addAttribute("workflowId",workflowId);
        model.addAttribute("inputNodes",inputNodes);
        model.addAttribute("outputNodes",outputNodes);

        return "templates.admin.customdeploy";
    }
}
