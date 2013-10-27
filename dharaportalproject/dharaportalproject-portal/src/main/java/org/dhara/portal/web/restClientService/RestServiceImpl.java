package org.dhara.portal.web.restClientService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dhara.portal.web.helper.ExperimentHelper;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private RestServiceConfig restServiceConfig;

    private RestClient restClient;

    public RestServiceImpl() {
        restClient=new RestClient();
    }

    @Override
    public List<ExperimentHelper> getExperiments() throws IOException {
        String response=restClient.getResponse(restServiceConfig.getServerUrl()+RestResourceUtils.EXPERIMENTDATA_RESOURCE);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<List<ExperimentHelper>>(){});
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
}
