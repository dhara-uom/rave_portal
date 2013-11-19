package org.dhara.portal.web.helper;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 11/17/13
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkflowInputHelper {

    private int[] ints;
    private String workflowName;

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }
}
