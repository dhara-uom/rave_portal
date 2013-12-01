package org.dhara.portal.web.helper;

/**
 * Input data helper class for workflow execution to hold workflow inputs
 */
public class InputHelper {

    private String name;
    private String rawName;
    private String type;
    private String[] values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }
}
