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
import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 8/9/13
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkflowCustomDeploymentController extends SimpleFormController {
    protected final Log log = LogFactory.getLog(getClass());

    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String text = "Not used";
        log.debug("Returning hello world text: " + text);
        return text;
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
                                    BindException errors) throws Exception {
        String workflowId=request.getParameter("workflowId");
        String extendingAlgorithm=request.getParameter("extendingAlgo");
        ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        AiravataClientAPIService airavataClientAPIService= (AiravataClientAPIService) context.getBean("airavataAPIService");
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        Map<String,String> inputMapping=new HashMap<String, String>();
        Map<String,String> outputMapping=new HashMap<String, String>();
        for(WorkflowInput input:workflow.getWorkflowInputs()) {
            inputMapping.put(input.getName(),request.getParameter(input.getName()));
        }

        for(WSComponentPort workflowOutput : workflow.getOutputs()) {
            outputMapping.put(workflowOutput.getName(),request.getParameter(workflowOutput.getName()));
        }

        CodeGenService codeGenService= (CodeGenService) context.getBean("codeGenService");
        codeGenService.getGeneratedClassForCustomDeployment(workflowId,inputMapping,outputMapping,extendingAlgorithm);
        WPS52NorthService wpsConnect52Servie= (WPS52NorthService) context.getBean("wps52NorthService");
        String generatedCode=codeGenService.getGeneratedClass(workflowId);
        wpsConnect52Servie.uploadClass(generatedCode,workflowId);
        ModelAndView model = new ModelAndView("redirect:/workflowList.htm");
        return model;
    }

    /*@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String workflowId=request.getParameter("workflowId");
        ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        AiravataClientAPIService airavataClientAPIService= (AiravataClientAPIService) context.getBean("airavataAPIService");
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        ModelAndView model = new ModelAndView("workflows");
        model.addObject("workflow", workflow);
        return model;
    }*/

    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        String workflowId=request.getParameter("workflowId");
        ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        AiravataClientAPIService airavataClientAPIService= (AiravataClientAPIService) context.getBean("airavataAPIService");
        List<Workflow> workflowList=airavataClientAPIService.getAllWorkflows();
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

        modelMap.put("workflowList",workflowHelpers);
        modelMap.put("workflowId",workflowId);
        modelMap.put("inputNodes",inputNodes);
        modelMap.put("outputNodes",outputNodes);

        return modelMap;
    }
}
