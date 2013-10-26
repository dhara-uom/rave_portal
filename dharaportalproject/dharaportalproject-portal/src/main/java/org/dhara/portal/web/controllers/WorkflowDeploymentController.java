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
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/29/13
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
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
        String generatedCode=codeGenService.getGeneratedClass(workflowId);
        wpsConnect52Service.uploadClass(generatedCode,workflowId);
        return "redirect:/";
    }

}
