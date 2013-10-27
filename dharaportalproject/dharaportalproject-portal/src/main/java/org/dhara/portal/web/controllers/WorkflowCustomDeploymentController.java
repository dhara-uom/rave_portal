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

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 8/9/13
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller

@RequestMapping(value = {"/admin/workflow/customdeploy", "/admin/workflow/custom/"})
public class WorkflowCustomDeploymentController{

    @Autowired
    private AiravataClientAPIService airavataClientAPIService;

    @Autowired
    private WPS52NorthService wpsConnect52Service;

    @Autowired
    private CodeGenService codeGenService;

    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@RequestParam(required = false) final String action,
                              @RequestParam(required = false) String referringPageId, Model model,HttpServletRequest request, HttpServletResponse response, Object command,
                                    BindException errors) throws Exception {

        String workflowId=request.getParameter("workflowId");
        String extendingAlgorithm=request.getParameter("extendingAlgo");
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        Map<String,String> inputMapping=new HashMap<String, String>();
        Map<String,String> outputMapping=new HashMap<String, String>();
        for(WorkflowInput input:workflow.getWorkflowInputs()) {
            inputMapping.put(input.getName(),request.getParameter(input.getName()));
        }

        for(WSComponentPort workflowOutput : workflow.getOutputs()) {
            outputMapping.put(workflowOutput.getName(),request.getParameter(workflowOutput.getName()));
        }

        codeGenService.getGeneratedClassForCustomDeployment(workflowId,inputMapping,outputMapping,extendingAlgorithm);
        String generatedCode=codeGenService.getGeneratedClass(workflowId);
        wpsConnect52Service.uploadClass(generatedCode,workflowId);

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showDeployForm(HttpServletRequest request, ModelMap modelMap) throws Exception {

        String workflowId=request.getParameter("workflowId");
        List<WorkflowHelper> workflowHelpers=new ArrayList<WorkflowHelper>();
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        List<MappingHelper> inputNodes=new ArrayList<MappingHelper>();
        List<MappingHelper> outputNodes=new ArrayList<MappingHelper>();


        for(WorkflowInput workflowInput:workflow.getWorkflowInputs()) {
            MappingHelper mappingHelper=new MappingHelper();
            mappingHelper.setNodeName(workflowInput.getName());
            mappingHelper.setExistingMapping(workflowInput.getType());
            inputNodes.add(mappingHelper);
        }

        for(WSComponentPort workflowOutput : workflow.getOutputs()) {
            MappingHelper mappingHelper=new MappingHelper();
            mappingHelper.setNodeName(workflowOutput.getName());
            mappingHelper.setExistingMapping(workflowOutput.getType().getLocalPart());
            outputNodes.add(mappingHelper);
        }

        modelMap.addAttribute("workflowList",workflowHelpers);
        modelMap.addAttribute("workflowId",workflowId);
        modelMap.addAttribute("inputNodes",inputNodes);
        modelMap.addAttribute("outputNodes",outputNodes);

        return "templates.admin.customdeploy";
    }
}
