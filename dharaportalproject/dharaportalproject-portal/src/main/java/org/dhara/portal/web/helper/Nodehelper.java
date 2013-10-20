package org.dhara.portal.web.helper;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 10/15/13
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Nodehelper {

      private String type;
      private String workflowInstanceNodeId;
      private String input;
      private String output;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkflowInstanceNodeId() {
        return workflowInstanceNodeId;
    }

    public void setWorkflowInstanceNodeId(String workflowInstanceNodeId) {
        this.workflowInstanceNodeId = workflowInstanceNodeId;
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
}
