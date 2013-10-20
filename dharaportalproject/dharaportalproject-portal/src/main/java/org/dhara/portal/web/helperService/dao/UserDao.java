package org.dhara.portal.web.helperService.dao;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/9/13
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */

import org.dhara.portal.web.helperService.entity.Customer;

import java.util.List;

public interface UserDao {

    public void saveOrUpdateCustomer(Customer customer);
    public List<Customer> fetchALLCustomers();

}