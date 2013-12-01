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
package org.dhara.portal.web.portalServices;

import org.dhara.portal.web.dataAccessLayer.PortalDAO;
import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowDeployment;
import org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowInvocation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Portal service implementation
 */
@Transactional
public class PortalServiceImpl implements PortalService{
    private PortalDAO portalDAO;

    public PortalDAO getPortalDAO() {
        return portalDAO;
    }

    public void setPortalDAO(PortalDAO portalDAO) {
        this.portalDAO = portalDAO;
    }

    /**
     * @see PortalService#saveWorkfloeDeployment(org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowDeployment)
     */
    public void saveWorkfloeDeployment(PersistWorkflowDeployment persistWorkflowDeployment) {
        portalDAO.saveWorkfloeDeployment(persistWorkflowDeployment);
    }

    /**
     * @see PortalService#saveWorkfloeInvocation(org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowInvocation)
     */
    public void saveWorkfloeInvocation(PersistWorkflowInvocation persistWorkflowInvocations) {
        portalDAO.saveWorkfloeInvocation(persistWorkflowInvocations);
    }

    /**
     * @see org.dhara.portal.web.portalServices.PortalService#getAllWorkfloeInvocations()
     */
    public List<PersistWorkflowInvocation> getAllWorkfloeInvocations() {
        return portalDAO.getAllWorkfloeInvocations();
    }

    /**
     * @see org.dhara.portal.web.portalServices.PortalService#getAllWorkfloeDeployments()
     */
    public List<PersistWorkflowDeployment> getAllWorkfloeDeployments() {
        return portalDAO.getAllWorkfloeDeployments();
    }
}
