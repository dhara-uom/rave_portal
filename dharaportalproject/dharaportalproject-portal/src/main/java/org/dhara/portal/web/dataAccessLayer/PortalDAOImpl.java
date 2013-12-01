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
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Portal data access service implementation
 */
@Transactional
public class PortalDAOImpl implements PortalDAO{
    private SessionFactory sessionFactory;

    /**
     * @see PortalDAO#saveWorkfloeDeployment(org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowDeployment)
     */
    public void saveWorkfloeDeployment(PersistWorkflowDeployment persistWorkflowDeployment) {
        getSessionFactory().getCurrentSession().save(persistWorkflowDeployment);
    }

    /**
     * @see PortalDAO#saveWorkfloeInvocation(org.dhara.portal.web.dataAccessLayer.entity.PersistWorkflowInvocation)
     */
    public void saveWorkfloeInvocation(PersistWorkflowInvocation persistWorkflowInvocations) {
        getSessionFactory().getCurrentSession().save(persistWorkflowInvocations);
    }

    /**
     * @see org.dhara.portal.web.dataAccessLayer.PortalDAO#getAllWorkfloeInvocations()
     */
    public List<PersistWorkflowInvocation> getAllWorkfloeInvocations() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PersistWorkflowInvocation.class);
        return (List<PersistWorkflowInvocation>) criteria.list();
    }

    /**
     * @see org.dhara.portal.web.dataAccessLayer.PortalDAO#getAllWorkfloeDeployments()
     */
    public List<PersistWorkflowDeployment> getAllWorkfloeDeployments() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PersistWorkflowDeployment.class);
        return (List<PersistWorkflowDeployment>) criteria.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
