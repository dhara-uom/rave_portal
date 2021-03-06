/***********************************************************************************************************************
 *
 * Dhara- A Geoscience Gateway
 * ==========================================
 *
 * Copyright (C) 2013 by Dhara
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/
package org.dhara.portal.web.dataAccessLayer.entity;

import java.util.Date;

/**
 * POJO class for store deployed workflow in the portal
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
