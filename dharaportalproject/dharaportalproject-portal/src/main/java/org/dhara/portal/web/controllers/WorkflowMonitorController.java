package org.dhara.portal.web.controllers;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dhara.portal.web.airavataService.MonitorMessage;
import org.dhara.portal.web.helper.InputHelper;
import org.dhara.portal.web.restAPI.RestInputAPI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 11/17/13
 * Time: 9:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class WorkflowMonitorController extends Observable{
    protected final Log log = LogFactory.getLog(getClass());
    private List<MonitorMessage> events = new ArrayList<MonitorMessage>();

    @RequestMapping(value = {"/admin/monitoring", "/admin/monitoring/"}, method = RequestMethod.GET)
    protected String handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

        List<Integer> inputs = new ArrayList<Integer>();
        for (InputHelper in:inputHelperList){
            if(in.getType().equalsIgnoreCase("(int)")){
                for (int i=0;i<in.getValues().length;i++){
                    inputs.add(Integer.parseInt(in.getValues()[i]));
                }
            }
        }

        int[] ints = ArrayUtils.toPrimitive(inputs.toArray(new Integer[inputs.size()]));
        RestInputAPI restInputAPI = new RestInputAPI();
        this.addObserver(restInputAPI);
        setChanged();
        notifyObservers(ints);
        //TODO execute workflow through API  (build rest API here ane Rest client in admin portal side)
//        ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
//        AiravataClientAPIService airavataClientAPIService= (AiravataClientAPIService) context.getBean("airavataAPIService");
//
//        RestWorkflowMonitorAPI restWorkflowMonitorAPI = new RestWorkflowMonitorAPI();
//        restWorkflowMonitorAPI.getEvents(airavataClientAPIService, ints, workflowName);

        return "templates.admin.monitoring";
    }
}
