package org.dhara.portal.web.helper;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 8/15/13
 * Time: 5:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkflowDataHelper {

    private Date date;
    private String input;
    private String output;
    private String type;
    private String experimentId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }
}
