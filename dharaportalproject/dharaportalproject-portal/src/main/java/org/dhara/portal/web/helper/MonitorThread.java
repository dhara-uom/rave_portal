package org.dhara.portal.web.helper;

import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.airavataService.AiravataClientAPIServiceImpl;
import org.dhara.portal.web.controllers.RestMonitorAPIController;

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
    private RestMonitorAPIController restWorkflowMonitorAP;

    public MonitorThread(AiravataClientAPIService airavataClientAPIService,String threadName, Object[] inputs){
        this.name = threadName;
        this.inputs = inputs;
        AiravataClientAPIServiceImpl temp = new AiravataClientAPIServiceImpl();
        temp.setPortalConfiguration(((AiravataClientAPIServiceImpl)airavataClientAPIService).getPortalConfiguration());
        temp.setAiravataConfig(((AiravataClientAPIServiceImpl)airavataClientAPIService).getAiravataConfig());
        this.airavataClientAPIService = temp;
        this.restWorkflowMonitorAP = new RestMonitorAPIController();
    }

    public String getExperimentId() throws Exception {
        String id = this.restWorkflowMonitorAP.registerObserver(airavataClientAPIService,inputs, name);
        return id;
    }

    public void run(){
        try {
            this.restWorkflowMonitorAP.monitor();

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
