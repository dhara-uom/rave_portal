package org.dhara.portal.web.restClientService;

import org.dhara.portal.web.helper.ExperimentHelper;
import org.dhara.portal.web.helper.WorkflowHelper;

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

    public List<WorkflowHelper> getWorkflows() throws IOException;

    public  Object monitorExperiment();
}
