package org.dhara.portal.web.controllers;

import org.dhara.portal.web.airavataService.AiravataClientAPIServiceImpl;
import org.dhara.portal.web.airavataService.MonitorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 11/19/13
 * Time: 8:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class RestWorkflowMonitorAPI implements Observer {

    private static List<MonitorMessage> events = new ArrayList<MonitorMessage>();
    private static String html ="";


    @RequestMapping(value = {"/admin/monitorData/{workflowId}", "/admin/monitorData/{workflowId}/"}, method = RequestMethod.GET)
    @ResponseBody
    public String handleRequestInternal(@PathParam("name") String name, Model model, HttpServletRequest httpServletRequest) throws Exception {

        String temp=html;
        html="";
        events = new ArrayList<MonitorMessage>();

        return temp;
    }

    public void registerObserver(AiravataClientAPIServiceImpl airavataClientAPIService, Object[] ints, String workflowName) throws Exception {
        events = new ArrayList<MonitorMessage>();
        AiravataClientAPIServiceImpl apiService = new AiravataClientAPIServiceImpl();
        apiService.setPortalConfiguration(airavataClientAPIService.getPortalConfiguration());
        apiService.setAiravataConfig(airavataClientAPIService.getAiravataConfig());
        apiService.addObserver(this);
        String experimentId = apiService.executeExperiment(ints, workflowName);
        apiService.monitorWorkflow(experimentId);
    }


    public void update(Observable o, Object arg) {
        events.add((MonitorMessage)arg);
        html = html+"<tr>" +
                "<td>"+((MonitorMessage) arg).getTimestamp().toString()+"</td>" +
                "<td>"+((MonitorMessage) arg).getStatusText()+"</td>" +
                "<td>"+((MonitorMessage) arg).getMesssage()+"</td>" +
                "</tr>";
    }
}
