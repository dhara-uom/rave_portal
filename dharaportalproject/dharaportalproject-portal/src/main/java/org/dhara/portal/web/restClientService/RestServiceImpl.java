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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dhara.portal.web.helper.ExperimentHelper;
import org.dhara.portal.web.configuration.PortalConfiguration;
import org.dhara.portal.web.configuration.RestServiceConfig;
import org.dhara.portal.web.helper.WorkflowHelper;

import java.io.IOException;
import java.util.List;

/**
 * REST service implementation
 */
public class RestServiceImpl implements RestService {

    private RestServiceConfig restServiceConfig;
    private PortalConfiguration portalConfiguration;
    private RestClient restClient;

    public RestServiceImpl() {
        setRestClient(new RestClient());
    }

    /**
     * @see org.dhara.portal.web.restClientService.RestService#getExperiments()
     */
    @Override
    public List<ExperimentHelper> getExperiments() throws IOException {
        String response= getRestClient().getResponse(getRestServiceConfig().getServerUrl() + RestResourceUtils.EXPERIMENTDATA_RESOURCE);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<List<ExperimentHelper>>(){});
    }

    /**
     * @see org.dhara.portal.web.restClientService.RestService#getWorkflows()
     */
    public List<WorkflowHelper> getWorkflows() throws IOException {
        String response= getRestClient().getResponse(getRestServiceConfig().getServerUrl() + RestResourceUtils.WORKFLOWDATA_RESOURCE);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<List<WorkflowHelper>>(){});
    }


    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public void setRestServiceConfig(RestServiceConfig restServiceConfig) {
        this.restServiceConfig = restServiceConfig;
    }

    public RestServiceConfig getRestServiceConfig() {
        return restServiceConfig;
    }

    public PortalConfiguration getPortalConfiguration() {
        return portalConfiguration;
    }

    public void setPortalConfiguration(PortalConfiguration portalConfiguration) {
        this.portalConfiguration = portalConfiguration;
        restServiceConfig=portalConfiguration.getRestServiceConfig();
    }
}
