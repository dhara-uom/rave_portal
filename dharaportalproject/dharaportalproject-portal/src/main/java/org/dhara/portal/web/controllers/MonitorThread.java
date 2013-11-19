package org.dhara.portal.web.controllers;

import org.dhara.portal.web.airavataService.AiravataClientAPIService;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 11/19/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonitorThread implements Runnable {

    private String name;

    private AiravataClientAPIService airavataClientAPIService;
    private int[] inputs;

    public MonitorThread(String threadName, AiravataClientAPIService airavataClientAPIService, int[] inputs){
        this.name = threadName;
        this.airavataClientAPIService = airavataClientAPIService;
        this.inputs = inputs;
    }

    public void run(){
        try {
            RestWorkflowMonitorAPI restWorkflowMonitorAPI = new RestWorkflowMonitorAPI();
            restWorkflowMonitorAPI.getEvents(airavataClientAPIService,inputs,name);
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

    public AiravataClientAPIService getAiravataClientAPIService() {
        return airavataClientAPIService;
    }

    public void setAiravataClientAPIService(AiravataClientAPIService airavataClientAPIService) {
        this.airavataClientAPIService = airavataClientAPIService;
    }

    public int[] getInputs() {
        return inputs;
    }

    public void setInputs(int[] inputs) {
        this.inputs = inputs;
    }
}
