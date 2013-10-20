package org.dhara.portal.web.codegenService;

import org.dhara.portal.web.exception.PortalException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/26/13
 * Time: 8:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CodeGenService {

    /**
     * Generate the class for the given workflow
     * @param workflowId workflow id of the workflow which class need to be generated
     * @return
     * @throws PortalException
     */
    public String getGeneratedClass(String workflowId) throws PortalException;

    /**
     * Custom class generation
     * @param worklfowId
     * @param inputsMapping
     * @param outputsMapping
     * @param extendingAlgorithm
     * @return
     */
    public String getGeneratedClassForCustomDeployment(String worklfowId,Map<String,String> inputsMapping, Map<String,String> outputsMapping,String extendingAlgorithm) throws PortalException;
}
