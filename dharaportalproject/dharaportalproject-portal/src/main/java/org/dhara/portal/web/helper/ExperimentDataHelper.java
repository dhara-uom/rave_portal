package org.dhara.portal.web.helper;

import org.dhara.portal.web.airavataService.MonitorMessage;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 11/22/13
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExperimentDataHelper {

    private MonitorMessage monitorMessage;
    private String experimentId;

    public MonitorMessage getMonitorMessage() {
        return monitorMessage;
    }

    public void setMonitorMessage(MonitorMessage monitorMessage) {
        this.monitorMessage = monitorMessage;
    }

    public String getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }
}
