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
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/26/13
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestServiceImpl implements RestService {

    private RestServiceConfig restServiceConfig;
    private PortalConfiguration portalConfiguration;
    private RestClient restClient;

    public RestServiceImpl() {
        setRestClient(new RestClient());
    }

    @Override
    public List<ExperimentHelper> getExperiments() throws IOException {
        String response= getRestClient().getResponse(getRestServiceConfig().getServerUrl() + RestResourceUtils.EXPERIMENTDATA_RESOURCE);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<List<ExperimentHelper>>(){});
    }

    public List<WorkflowHelper> getWorkflows() throws IOException {
        String response= getRestClient().getResponse(getRestServiceConfig().getServerUrl() + RestResourceUtils.WORKFLOWDATA_RESOURCE);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<List<WorkflowHelper>>(){});
    }


    @Override
    public Object monitorExperiment() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
