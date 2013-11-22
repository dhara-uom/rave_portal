package org.dhara.portal.web.controllers;

import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.airavataService.AiravataClientAPIServiceImpl;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 11/19/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonitorThread implements Runnable {

    private String name;
    private Object[] inputs;

    private AiravataClientAPIServiceImpl airavataClientAPIService;

    public MonitorThread(AiravataClientAPIService airavataClientAPIService,String threadName, Object[] inputs){
        this.name = threadName;
        this.inputs = inputs;
        AiravataClientAPIServiceImpl temp = new AiravataClientAPIServiceImpl();
        temp.setPortalConfiguration(((AiravataClientAPIServiceImpl)airavataClientAPIService).getPortalConfiguration());
        temp.setAiravataConfig(((AiravataClientAPIServiceImpl)airavataClientAPIService).getAiravataConfig());
        this.airavataClientAPIService = temp;
    }

    public void run(){
        try {

            RestWorkflowMonitorAPI restWorkflowMonitorAPI = new RestWorkflowMonitorAPI();
            restWorkflowMonitorAPI.registerObserver(airavataClientAPIService,inputs, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Object[] getInputs() {
        return inputs;
    }

    public void setInputs(Object[] inputs) {
        this.inputs = inputs;
    }
}
