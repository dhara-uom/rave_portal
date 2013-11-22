package org.dhara.portal.web.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.airavataService.MonitorMessage;
import org.dhara.portal.web.configuration.PortalConfiguration;
import org.dhara.portal.web.helper.InputHelper;
import org.dhara.portal.web.helper.MonitorThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static org.dhara.portal.web.controllers.GatewayControllerUtil.addNavigationMenusToModel;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 11/17/13
 * Time: 9:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class WorkflowMonitorController {
    protected final Log log = LogFactory.getLog(getClass());
    private List<MonitorMessage> events = new ArrayList<MonitorMessage>();
    private static final String SELECTED_ITEM = "workflows";

    @Autowired
    private AiravataClientAPIService airavataClientAPIService;

    @RequestMapping(value = {"/admin/monitoring", "/admin/monitoring/"}, method = RequestMethod.GET)
    protected String handleRequestInternal(@RequestParam(required = false) final String action,
                                           @RequestParam(required = false) String referringPageId, Model model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);
        String workflowName=request.getParameter("workflowId");
        List<InputHelper> inputHelperList = new ArrayList<InputHelper>();
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()){
            String temp = (String) enumeration.nextElement();
            if(temp.equalsIgnoreCase("workflowId"));
            else {
                InputHelper inputHelper = new InputHelper();
                inputHelper.setRawName(temp);
                inputHelper.setName(temp.split("/")[0]);
                inputHelper.setType(temp.split("/")[1]);
                inputHelper.setValues(request.getParameterValues(temp));
                inputHelperList.add(inputHelper);
            }
        }

        List<Object> inputs = new ArrayList<Object>();

        for (InputHelper in:inputHelperList){
            for (int i=0;i<in.getValues().length;i++){
                    inputs.add(in.getValues()[i]);
                }
        }

        Object[] ints = inputs.toArray();

        Runnable monitorThread = new MonitorThread(airavataClientAPIService,workflowName,ints);
        String id = ((MonitorThread)monitorThread).getExperimentId();
        PortalConfiguration.executor.execute(monitorThread);

        model.addAttribute("id",id);
        return "templates.admin.monitoring";
    }
}
