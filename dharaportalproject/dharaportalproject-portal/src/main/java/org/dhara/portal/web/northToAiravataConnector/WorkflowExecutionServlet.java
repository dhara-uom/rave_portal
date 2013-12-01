/***********************************************************************************************************************
 *
 * Dhara- A Geoscience Gateway
 * ==========================================
 *
 * Copyright (C) 2013 by Dhara
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/
package org.dhara.portal.web.northToAiravataConnector;

import org.apache.airavata.workflow.model.wf.Workflow;
import org.apache.airavata.workflow.model.wf.WorkflowInput;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Workflow execution servele which is currently not in use
 */
public class WorkflowExecutionServlet extends javax.servlet.http.HttpServlet {

    private static Map<String, Object> outputs;


    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    @RequestMapping(value = {"/connect/ExecutionServlet", "/connect/ExecutionServlet/"}, method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
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
