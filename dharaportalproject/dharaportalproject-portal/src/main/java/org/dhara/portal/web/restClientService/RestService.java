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
package org.dhara.portal.web.restClientService;

import org.dhara.portal.web.helper.ExperimentHelper;
import org.dhara.portal.web.helper.WorkflowHelper;

import java.io.IOException;
import java.util.List;

/**
 * REST service interface
 */
public interface RestService {

    /**
     * Get experiments through backend REST service
     * @return
     * @throws IOException
     */
    public List<ExperimentHelper> getExperiments() throws IOException;

    /**
     * Get all workflows through backend REST service
     * @return
     * @throws IOException
     */
    public List<WorkflowHelper> getWorkflows() throws IOException;

}
