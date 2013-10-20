package org.dhara.portal.web.helperService.dao;

import org.dhara.portal.web.helperService.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/9/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class HibernateUserDao implements UserDao {

    private SessionFactory sessionFactory;

    public void saveOrUpdateCustomer(Customer customer) {
        getSessionFactory().getCurrentSession().save(customer);
    }

    public List<Customer> fetchALLCustomers() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Customer.class);
        return (List<Customer>) criteria.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}


