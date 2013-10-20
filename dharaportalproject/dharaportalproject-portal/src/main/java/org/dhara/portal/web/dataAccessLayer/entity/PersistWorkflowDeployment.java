package org.dhara.portal.web.dataAccessLayer.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 8/10/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersistWorkflowDeployment {
    private int persistDeploymentId;
    private Date deployedDate;
    private String workflowId;

    public int getPersistDeploymentId() {
        return persistDeploymentId;
    }

    public void setPersistDeploymentId(int persistDeploymentId) {
        this.persistDeploymentId = persistDeploymentId;
    }

    public Date getDeployedDate() {
        return deployedDate;
    }

    public void setDeployedDate(Date deployedDate) {
        this.deployedDate = deployedDate;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}
