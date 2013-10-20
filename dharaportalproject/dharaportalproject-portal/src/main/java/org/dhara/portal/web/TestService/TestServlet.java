package org.dhara.portal.web.TestService;

import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.codegenService.CodeGenService;
import org.dhara.portal.web.exception.PortalException;
import org.dhara.portal.web.wps52NorthService.WPS52NorthService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: newair
 * Date: 8/12/13
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "TestServlet")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            String workflowId=request.getParameter("workflowId");
        ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        AiravataClientAPIService airavataClientAPIService= (AiravataClientAPIService) context.getBean("airavataAPIService");
        CodeGenService codeGenService= (CodeGenService) context.getBean("codeGenService");
        WPS52NorthService wpsConnect52Servie= (WPS52NorthService) context.getBean("wps52NorthService");
        String generatedCode= null;
        try {
            generatedCode = codeGenService.getGeneratedClass(workflowId);
        } catch (PortalException e) {
            e.printStackTrace();
        }

        wpsConnect52Servie.uploadClass(generatedCode,workflowId);


    }
}
