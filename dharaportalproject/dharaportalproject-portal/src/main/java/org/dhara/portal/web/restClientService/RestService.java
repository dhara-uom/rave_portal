package org.dhara.portal.web.restClientService;

import com.fasterxml.jackson.core.JsonParseException;
import org.apache.airavata.registry.api.workflow.ExperimentData;
import org.dhara.portal.web.helper.ExperimentHelper;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/26/13
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RestService {

    public List<ExperimentHelper> getExperiments() throws IOException;

    public  Object monitorExperiment();
}
