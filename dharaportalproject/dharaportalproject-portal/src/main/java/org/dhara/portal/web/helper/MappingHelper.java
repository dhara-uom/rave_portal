package org.dhara.portal.web.helper;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/16/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class MappingHelper {
    private String nodeName;
    private String existingMapping;

    public String getExistingMapping() {
        return existingMapping;
    }

    public void setExistingMapping(String existingMapping) {
        this.existingMapping = existingMapping;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
