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

import org.apache.rave.model.PortalPreference;
import org.apache.airavata.workflow.model.component.ws.WSComponentPort;
import org.apache.airavata.workflow.model.wf.Workflow;
import org.apache.airavata.workflow.model.wf.WorkflowInput;
import org.apache.rave.portal.service.PortalPreferenceService;
import org.apache.rave.portal.web.util.ModelKeys;
import org.apache.rave.portal.web.util.PortalPreferenceKeys;
import org.apache.rave.rest.model.SearchResult;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.helper.MappingHelper;
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
 * Controller for the admin pages for show workflow details for execution
 */
@Controller
public class WorkflowDetailController {

    private static final String SELECTED_ITEM = "workflows";

    int DEFAULT_PAGE_SIZE=10;

    @Autowired
    private RestServiceImpl restService;

    @Autowired
    private PortalPreferenceService preferenceService;

    @Autowired
    private AiravataClientAPIService airavataClientAPIService;


    @RequestMapping(value = {"/admin/workflow/details", "/admin/workflow/details/"}, method = RequestMethod.GET)
    public String handleRequestInternal(@RequestParam(required = false) String workflowDesc,
                                @RequestParam(required = false) String referringPageId, @RequestParam String workflowId,Model model,
                                HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        model.addAttribute(ModelKeys.REFERRING_PAGE_ID, referringPageId);
        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);

        List<WorkflowHelper> workflowHelpers=new ArrayList<WorkflowHelper>();
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        List<MappingHelper> inputNodes=new ArrayList<MappingHelper>();
        //Set workflow details
        for(WSComponentPort workflowInput:workflow.getInputs()) {
            MappingHelper mappingHelper=new MappingHelper();
            mappingHelper.setNodeName(workflowInput.getName());
            mappingHelper.setExistingMapping(workflowInput.getType().getLocalPart());
            inputNodes.add(mappingHelper);
        }

        model.addAttribute("workflowList",workflowHelpers);
        model.addAttribute("workflowId",workflowId);
        model.addAttribute("inputNodes",inputNodes);
        model.addAttribute("workflowDesc",workflowDesc);

        return "templates.admin.workflowDetails";

    }


}
