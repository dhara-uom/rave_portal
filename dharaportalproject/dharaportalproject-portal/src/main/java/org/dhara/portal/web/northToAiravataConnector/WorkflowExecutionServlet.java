package org.dhara.portal.web.northToAiravataConnector;

import org.apache.airavata.workflow.model.wf.Workflow;
import org.apache.airavata.workflow.model.wf.WorkflowInput;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class WorkflowExecutionServlet extends javax.servlet.http.HttpServlet {

    private static Map<String, Object> outputs;


    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String workflowId=request.getParameter("workflowId");
        try {
            ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            AiravataClientAPIService airavataClientAPIService = (AiravataClientAPIService) context.getBean("airavataAPIService");
            Map<String, Object> inputs = new HashMap<String, Object>();
            Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
            for(WorkflowInput workflowInput:workflow.getWorkflowInputs()) {
                inputs.put(workflowInput.getName(),request.getParameter(workflowInput.getName()));
            }
            outputs = airavataClientAPIService.executeWorkflow(inputs, workflowId);      //     _52NorthExecuteWorkFlowTest
            JSONObject jsonObject = new JSONObject(outputs);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(jsonObject.toJSONString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
