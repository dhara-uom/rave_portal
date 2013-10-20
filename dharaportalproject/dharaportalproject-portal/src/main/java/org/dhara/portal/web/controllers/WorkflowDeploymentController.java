package org.dhara.portal.web.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.codegenService.CodeGenService;
import org.dhara.portal.web.wps52NorthService.WPS52NorthService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/29/13
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkflowDeploymentController extends AbstractController {

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String workflowId=request.getParameter("workflowId");
        ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        AiravataClientAPIService airavataClientAPIService= (AiravataClientAPIService) context.getBean("airavataAPIService");
        CodeGenService codeGenService= (CodeGenService) context.getBean("codeGenService");
        WPS52NorthService wpsConnect52Servie= (WPS52NorthService) context.getBean("wps52NorthService");
        String generatedCode=codeGenService.getGeneratedClass(workflowId);
        wpsConnect52Servie.uploadClass(generatedCode,workflowId);
        ModelAndView model = new ModelAndView("redirect:/workflowList.htm");
        return model;
    }

    /*protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String text = "Not used";
        log.debug("Returning hello world text: " + text);
        return text;
    }

    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
        Map<String,Object> modelMap=new HashMap<String, Object>();
        ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        AiravataClientAPIService airavataClientAPIService= (AiravataClientAPIService) context.getBean("airavataAPIService");
        List<Workflow> workflowList=airavataClientAPIService.getAllWorkflows();
        List<WorkflowHelper> workflowHelpers=new ArrayList<WorkflowHelper>();
        for(Workflow workflow:workflowList)
        {
            WorkflowHelper workflowHelper=new WorkflowHelper();
            workflowHelper.setName(workflow.getName());
            workflowHelpers.add(workflowHelper);
        }
        modelMap.put("workflowList",workflowHelpers);
        return modelMap;
    }*/
}
