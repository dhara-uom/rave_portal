package org.dhara.portal.web.configuration;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.dhara.portal.web.codegenService.CodegenUtils;
import org.dhara.portal.web.exception.PortalException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 11/14/13
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortalConfiguration {
    private AiravataConfig airavataConfig;
    private RestServiceConfig restServiceConfig;
    private WPS52NorthConfig wps52NorthConfig;

    public PortalConfiguration() throws PortalException {
        setAiravataConfig(new AiravataConfig());
        setRestServiceConfig(new RestServiceConfig());
        setWps52NorthConfig(new WPS52NorthConfig());
        if(isPortalConfigurationExists()) {
            setPortalConfiguration();
        } else {
            setDefaultConfig();
        }
    }

    private boolean isPortalConfigurationExists() {
        File file=new File(System.getProperty("catalina.base")+File.separator+"webapps"+File.separator+"portal"+File.separator+"WEB-INF"+File.separator+"conf"+File.separator+"portal_configuration.xml");
        return file.exists();
    }

    /**
     * Set defualt config
     * @throws PortalException
     */
    private void setDefaultConfig() throws PortalException {
        //airavata default configs
        this.getAiravataConfig().setPassword("admin");
        this.getAiravataConfig().setUserName("admin");
        this.getAiravataConfig().setGatewayName("default");
        this.getAiravataConfig().setPort(8081);
        this.getAiravataConfig().setServerContextName("airavata-registry");
        this.getAiravataConfig().setServerUrl("localhost");

        //rest default service configs
        this.getRestServiceConfig().setPassword("admin");
        this.getRestServiceConfig().setUserName("admin");
        this.getRestServiceConfig().setServerUrl("http://localhost:8090/portal");

        //north default configurations
        this.getWps52NorthConfig().setPassword("admin");
        this.getWps52NorthConfig().setUserName("admin");
        this.getWps52NorthConfig().setServerUrl("http://localhost:8090/52n-wps-webapp-3.3.0-SNAPSHOT/webAdmin/DynamicDeployProcesstest.jsp");
        this.getWps52NorthConfig().setDefaultPackage(CodegenUtils.defaultPackage);

    }

    /**
     * If config exists create airavata configuration from xml
     * @throws PortalException
     */
    private void setPortalConfiguration() throws PortalException {
        File file= new File("portal_configuration.xml");
        FileInputStream fis;
        XMLInputFactory xif;
        XMLStreamReader reader;
        StAXOMBuilder builder;
        try {
            fis= new FileInputStream(file);
            xif= XMLInputFactory.newInstance();
            reader= xif.createXMLStreamReader(fis);
            builder= new StAXOMBuilder(reader);
        } catch (FileNotFoundException e) {
            throw new PortalException(e.getMessage(),e);
        } catch (XMLStreamException e) {
            throw new PortalException(e.getMessage(),e);
        }

        OMElement documentElement= builder.getDocumentElement();
        OMElement airavataConfig=documentElement.getFirstChildWithName(new QName("airavata-configuration"));
        OMElement aserver=airavataConfig.getFirstElement();
        this.getAiravataConfig().setPassword(aserver.getFirstChildWithName(new QName("username")).getText().toString());
        this.getAiravataConfig().setUserName(aserver.getFirstChildWithName(new QName("password")).getText().toString());
        this.getAiravataConfig().setGatewayName(aserver.getFirstChildWithName(new QName("gateway-name")).getText().toString());
        this.getAiravataConfig().setPort(Integer.parseInt(aserver.getFirstChildWithName(new QName("port")).getText().toString()));
        this.getAiravataConfig().setServerContextName(aserver.getFirstChildWithName(new QName("server-context")).getText().toString());
        this.getAiravataConfig().setServerUrl(aserver.getFirstChildWithName(new QName("server-url")).getText().toString());
        this.getAiravataConfig().setBroker(aserver.getFirstChildWithName(new QName("broker")).getText().toString());
        this.getAiravataConfig().setGfac(aserver.getFirstChildWithName(new QName("gfac")).getText().toString());
        this.getAiravataConfig().setMessageBox(aserver.getFirstChildWithName(new QName("message-box")).getText().toString());
        this.getAiravataConfig().setJcr(aserver.getFirstChildWithName(new QName("jcr")).getText().toString());

        OMElement backendConfiguration=documentElement.getFirstChildWithName(new QName("backend-configuration"));
        OMElement bserver=backendConfiguration.getFirstElement();
        this.getRestServiceConfig().setPassword(bserver.getFirstChildWithName(new QName("username")).getText().toString());
        this.getRestServiceConfig().setUserName(bserver.getFirstChildWithName(new QName("password")).getText().toString());
        this.getRestServiceConfig().setServerUrl(bserver.getFirstChildWithName(new QName("server-url")).getText().toString());

        OMElement northConfiguration=documentElement.getFirstChildWithName(new QName("wps-52north-configuration"));
        OMElement nserver=northConfiguration.getFirstElement();
        this.getWps52NorthConfig().setPassword(nserver.getFirstChildWithName(new QName("username")).getText().toString());
        this.getWps52NorthConfig().setUserName(nserver.getFirstChildWithName(new QName("password")).getText().toString());
        this.getWps52NorthConfig().setServerUrl(nserver.getFirstChildWithName(new QName("server-url")).getText().toString());
    }

    public AiravataConfig getAiravataConfig() {
        return airavataConfig;
    }

    public void setAiravataConfig(AiravataConfig airavataConfig) {
        this.airavataConfig = airavataConfig;
    }

    public RestServiceConfig getRestServiceConfig() {
        return restServiceConfig;
    }

    public void setRestServiceConfig(RestServiceConfig restServiceConfig) {
        this.restServiceConfig = restServiceConfig;
    }

    public WPS52NorthConfig getWps52NorthConfig() {
        return wps52NorthConfig;
    }

    public void setWps52NorthConfig(WPS52NorthConfig wps52NorthConfig) {
        this.wps52NorthConfig = wps52NorthConfig;
    }
}
