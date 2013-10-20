package org.dhara.portal.web.helperService.entity;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/9/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Customer {
    private long id;
    private String name;
    private String address;
    private String item;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}