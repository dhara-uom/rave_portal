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
package org.dhara.portal.web.dataAccessLayer;

import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowDeployment;
import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowInvocation;

import java.util.List;

/**
 * Portal data access service interface
 */
public interface PortalDAO {
    /**
     * Save workflow deployment
     * @param persistWorkflowDeployment
     */
    public void saveWorkfloeDeployment(PersistWorkflowDeployment persistWorkflowDeployment);

    /**
     * Save workflow invocation
     * @param persistWorkflowInvocations
     */
    public void saveWorkfloeInvocation(PersistWorkflowInvocation persistWorkflowInvocations);

    /**
     * Get all workflow invocations
     * @return
     */
    public List<PersistWorkflowInvocation> getAllWorkfloeInvocations();

    /**
     * Get all workflow deployments
     * @return
     */
    public List<PersistWorkflowDeployment> getAllWorkfloeDeployments();
}
