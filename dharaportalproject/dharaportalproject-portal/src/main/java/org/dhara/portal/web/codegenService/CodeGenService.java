package org.dhara.portal.web.codegenService;

import org.dhara.portal.web.exception.PortalException;

import java.util.Map;

/**
 * Code generation service interface
 */
public interface CodeGenService {

    /**
     * Generate the class for the given workflow
     * @param workflowId workflow id of the workflow which class need to be generated
     * @return generated class
     * @throws PortalException
     */
    public String getGeneratedClass(String workflowId) throws PortalException;

    /**
     * Custom class generation
     * @param worklfowId workflow id of the workflow which class need to be generated
     * @param inputsMapping mapping between workflow inputs to user specified data types
     * @param outputsMapping mapping between workflow outputs to user specified data types
     * @param extendingAlgorithm user specified extending algorithm
     * @return
     */
    public String getGeneratedClassForCustomDeployment(String worklfowId,Map<String,String> inputsMapping, Map<String,String> outputsMapping,String extendingAlgorithm) throws PortalException;
}
