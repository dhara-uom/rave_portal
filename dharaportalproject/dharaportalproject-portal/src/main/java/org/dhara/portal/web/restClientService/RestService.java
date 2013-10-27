package org.dhara.portal.web.restClientService;

import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/26/13
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RestService {

    public  Object getExperiments() throws IOException;

    public  Object monitorExperiment();
}
