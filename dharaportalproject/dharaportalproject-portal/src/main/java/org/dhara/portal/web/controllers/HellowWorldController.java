package org.dhara.portal.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/8/13
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dhara.portal.web.helperService.entity.Customer;
import org.dhara.portal.web.helperService.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import java.util.Date;
import java.util.List;

public class HellowWorldController extends AbstractController{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        String now = (new Date()).toString();
        logger.info("Returning hello view with " + now);

        ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        UserService service=(UserService)context.getBean("userService");
        Customer customer=new Customer();
        customer.setName("Abc");
        customer.setAddress("DDC");
        customer.setItem("aoaoaaoa");

        service.saveOrUpdateCustomer(customer);

        List<Customer> customerList = service.fetchALLCustomers();

        request.setAttribute("customerData", customerList);
        return new ModelAndView("helloworld", "now", "S");
    }
}
