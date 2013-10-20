package org.dhara.portal.web.dataAccessLayer.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 8/10/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersistWorkflowInvocation {
    private int persistInvocationId;
    private Date executedDate;
    private String workflowId;
    private String invocationId;

    public int getPersistInvocationId() {
        return persistInvocationId;
    }

    public void setPersistInvocationId(int persistInvocationId) {
        this.persistInvocationId = persistInvocationId;
    }

    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getInvocationId() {
        return invocationId;
    }

    public void setInvocationId(String invocationId) {
        this.invocationId = invocationId;
    }
}
