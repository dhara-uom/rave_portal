package org.dhara.portal.web.airavataService;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 10/24/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class MonitorMessage {

    private String workflowName;
    private Date timestamp;
    private String messsage;
    private String statusText;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
}
