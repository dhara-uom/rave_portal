package org.dhara.portal.web.helperService.service;

import org.dhara.portal.web.helperService.dao.UserDao;
import org.dhara.portal.web.helperService.entity.Customer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/9/13
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void saveOrUpdateCustomer(Customer customer) {
        userDao.saveOrUpdateCustomer(customer);
    }

    public List<Customer> fetchALLCustomers() {
        return userDao.fetchALLCustomers();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
