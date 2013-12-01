/***********************************************************************************************************************
 *
 * Dhara- A Geoscience Gateway
 * ==========================================
 *
 * Copyright (C) 2013 by Dhara
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/
package org.dhara.portal.web.codegenService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.airavata.workflow.model.component.ws.WSComponentPort;
import org.apache.airavata.workflow.model.wf.Workflow;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.configuration.PortalConfiguration;
import org.dhara.portal.web.exception.PortalException;

import java.io.*;
import java.util.*;

/**
 * Code generation service implementation
 */
public class CodeGenServiceImpl implements CodeGenService{
    private AiravataClientAPIService airavataClientAPIService;
    private PortalConfiguration portalConfiguration;
    private final String servlet="/connect/ExecutionServlet";

    /**
     * @see org.dhara.portal.web.codegenService.CodeGenService#getGeneratedClass(String) ()
     */
    public String getGeneratedClass(String workflowId) throws PortalException {
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        List<WSComponentPort> workflowInputs = null;
        List<WSComponentPort> workflowOutputs = null;

        try {
            workflowInputs = workflow.getInputs();
            workflowOutputs = workflow.getOutputs();
        } catch (Exception e) {
            throw new PortalException("Error getting workflow with id="+workflowId,e);
        }

        List<String> inputIds=new ArrayList<String>();
        List<String> outputIds=new ArrayList<String>();
        Map<String,String> inputBindings=new HashMap<String, String>();
        Map<String,String> outputBindings=new HashMap<String, String>();
        ArrayList<CodeGenInOutAssociations> inputs=new ArrayList<CodeGenInOutAssociations>();
        ArrayList<CodeGenInOutAssociations> outputs=new ArrayList<CodeGenInOutAssociations>();

        //Set bindings for workflow inputs by getting its' mapping data types
        for (WSComponentPort workflowInput : workflowInputs) {
            inputIds.add(workflowInput.getName());
            CodeGenInOutAssociations codeGenInOutAssociations=new CodeGenInOutAssociations();
            if("int".equalsIgnoreCase(workflowInput.getType().getLocalPart()) || "integer".equalsIgnoreCase(workflowInput.getType().getLocalPart()) || "IntegerParameterType".equalsIgnoreCase(workflowInput.getType().getLocalPart())) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_INT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_INT_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("string".equalsIgnoreCase(workflowInput.getType().getLocalPart()) || "StringParameterType".equalsIgnoreCase(workflowInput.getType().getLocalPart())) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_STRING_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_STRING_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("short".equalsIgnoreCase(workflowInput.getType().getLocalPart()) || "ShortParameterType".equalsIgnoreCase(workflowInput.getType().getLocalPart())) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_SHORT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_SHORT_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("double".equalsIgnoreCase(workflowInput.getType().getLocalPart()) || "DoubleParameterType".equalsIgnoreCase(workflowInput.getType().getLocalPart())) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_DOUBLE_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_DOUBLE_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("float".equalsIgnoreCase(workflowInput.getType().getLocalPart()) || "FloatParameterType".equalsIgnoreCase(workflowInput.getType().getLocalPart())) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_FLOAT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_FLOAT_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("boolean".equalsIgnoreCase(workflowInput.getType().getLocalPart()) || "BooleanParameterType".equalsIgnoreCase(workflowInput.getType().getLocalPart())) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_BOOLEAN_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_BOOLEAN_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_STRING_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_STRING_BINDING);
                inputs.add(codeGenInOutAssociations);
            }
        }

        //Set bindings for workflow outputs by getting its' mapping data types
        for (WSComponentPort workflowOutput : workflowOutputs) {
            CodeGenInOutAssociations codeGenInOutAssociations=new CodeGenInOutAssociations();
            outputIds.add(workflowOutput.getName());
            if("int".equalsIgnoreCase(workflowOutput.getType().getLocalPart()) || "integer".equalsIgnoreCase(workflowOutput.getType().getLocalPart()) || "IntegerParameterType".equalsIgnoreCase(workflowOutput.getType().getLocalPart())) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_INT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_INT_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("string".equalsIgnoreCase(workflowOutput.getType().getLocalPart()) || "StringParameterType".equalsIgnoreCase(workflowOutput.getType().getLocalPart())) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_STRING_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_STRING_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("short".equalsIgnoreCase(workflowOutput.getType().getLocalPart()) || "ShortParameterType".equalsIgnoreCase(workflowOutput.getType().getLocalPart())) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_SHORT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_SHORT_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("double".equalsIgnoreCase(workflowOutput.getType().getLocalPart()) || "DoubleParameterType".equalsIgnoreCase(workflowOutput.getType().getLocalPart())) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_DOUBLE_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_DOUBLE_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("float".equalsIgnoreCase(workflowOutput.getType().getLocalPart()) || "FloatParameterType".equalsIgnoreCase(workflowOutput.getType().getLocalPart())) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_FLOAT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_FLOAT_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("boolean".equalsIgnoreCase(workflowOutput.getType().getLocalPart()) || "BooleanParameterType".equalsIgnoreCase(workflowOutput.getType().getLocalPart())) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_BOOLEAN_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_BOOLEAN_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_STRING_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_STRING_BINDING);
                outputs.add(codeGenInOutAssociations);
            }
        }
        String classContents=generateClassFromTemplate(inputIds,outputIds,workflow.getName(),CodegenUtils.defaultExtendingClass,inputBindings,outputBindings,inputs,outputs);
        return classContents;
    }

    /**
     * @see org.dhara.portal.web.codegenService.CodeGenService#getGeneratedClassForCustomDeployment(String, java.util.Map, java.util.Map, String) ()
     */
    public String getGeneratedClassForCustomDeployment(String workflowId, Map<String, String> inputsMapping, Map<String, String> outputsMapping, String extendingAlgorithm) throws PortalException {
        Workflow workflow=airavataClientAPIService.getWorkflow(workflowId);
        List<WSComponentPort> workflowInputs = null;
        List<WSComponentPort> workflowOutputs = null;

        try {
            workflowInputs = workflow.getInputs();
            workflowOutputs = workflow.getOutputs();
        } catch (Exception e) {
            throw new PortalException("Error getting workflow with id="+workflowId,e);
        }

        List<String> inputIds=new ArrayList<String>();
        List<String> outputIds=new ArrayList<String>();
        Map<String,String> inputBindings=new HashMap<String, String>();
        Map<String,String> outputBindings=new HashMap<String, String>();
        ArrayList<CodeGenInOutAssociations> inputs=new ArrayList<CodeGenInOutAssociations>();
        ArrayList<CodeGenInOutAssociations> outputs=new ArrayList<CodeGenInOutAssociations>();

        //Set bindings for workflow inputs by getting its' user specified mapping data types
        for (WSComponentPort workflowInput : workflowInputs) {
            inputIds.add(workflowInput.getName());
            CodeGenInOutAssociations codeGenInOutAssociations=new CodeGenInOutAssociations();
            if("int".equalsIgnoreCase(inputsMapping.get(workflowInput.getName())) || "integer".equalsIgnoreCase(inputsMapping.get(workflowInput.getName())) || "IntegerParameterType".equalsIgnoreCase(inputsMapping.get(workflowInput.getName()))) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_INT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_INT_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("string".equalsIgnoreCase(inputsMapping.get(workflowInput.getName())) || "StringParameterType".equalsIgnoreCase(inputsMapping.get(workflowInput.getName()))) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_STRING_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_STRING_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("short".equalsIgnoreCase(inputsMapping.get(workflowInput.getName())) || "ShortParameterType".equalsIgnoreCase(inputsMapping.get(workflowInput.getName()))) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_SHORT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_SHORT_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("double".equalsIgnoreCase(inputsMapping.get(workflowInput.getName())) || "DoubleParameterType".equalsIgnoreCase(inputsMapping.get(workflowInput.getName()))) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_DOUBLE_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_DOUBLE_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("float".equalsIgnoreCase(inputsMapping.get(workflowInput.getName())) || "FloatParameterType".equalsIgnoreCase(inputsMapping.get(workflowInput.getName()))) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_FLOAT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_FLOAT_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else if ("boolean".equalsIgnoreCase(inputsMapping.get(workflowInput.getName())) || "BooleanParameterType".equalsIgnoreCase(inputsMapping.get(workflowInput.getName()))) {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_BOOLEAN_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_BOOLEAN_BINDING);
                inputs.add(codeGenInOutAssociations);
            } else {
                inputBindings.put(workflowInput.getName()+"_TypeClass", CodegenUtils.LITERAL_STRING_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowInput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_STRING_BINDING);
                inputs.add(codeGenInOutAssociations);
            }
        }

        //Set bindings for outputs workflow by getting its' user specified mapping data types
        for (WSComponentPort workflowOutput : workflowOutputs) {
            CodeGenInOutAssociations codeGenInOutAssociations=new CodeGenInOutAssociations();
            outputIds.add(workflowOutput.getName());
            if("int".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName())) || "integer".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName())) || "IntegerParameterType".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName()))) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_INT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_INT_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("string".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName())) || "StringParameterType".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName()))) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_STRING_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_STRING_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("short".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName())) || "ShortParameterType".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName()))) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_SHORT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_SHORT_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("double".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName())) || "DoubleParameterType".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName()))) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_DOUBLE_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_DOUBLE_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("float".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName())) || "FloatParameterType".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName()))) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_FLOAT_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_FLOAT_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else if ("boolean".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName())) || "BooleanParameterType".equalsIgnoreCase(inputsMapping.get(workflowOutput.getName()))) {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_BOOLEAN_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_BOOLEAN_BINDING);
                outputs.add(codeGenInOutAssociations);
            } else {
                outputBindings.put(workflowOutput.getName()+"_TypeClass", CodegenUtils.LITERAL_STRING_BINDING);
                codeGenInOutAssociations.setIdentifier(workflowOutput.getName());
                codeGenInOutAssociations.setMappingClass(CodegenUtils.LITERAL_STRING_BINDING);
                outputs.add(codeGenInOutAssociations);
            }
        }

        String classContents=generateClassFromTemplate(inputIds,outputIds,workflow.getName(),extendingAlgorithm,inputBindings,outputBindings,inputs,outputs);
        return classContents;
    }

    /**
     * Generate class from the wps template
     * @param inputIdentifiersList input identifier list
     * @param outputIdentifiersList output identifier list
     * @param className  class  name of generated class
     * @param extendingClass extending algorithm class
     * @param inputBindingsList input binding list
     * @param outputBindingsList output binding list
     * @param inputMappings  input mappings
     * @param outputMappings  output mappings
     * @return generated class
     * @throws PortalException
     */
    private String generateClassFromTemplate(List<String> inputIdentifiersList,List<String> outputIdentifiersList, String className,String extendingClass, Map<String,String> inputBindingsList,
                                             Map<String,String> outputBindingsList, ArrayList<CodeGenInOutAssociations> inputMappings,ArrayList<CodeGenInOutAssociations> outputMappings) throws PortalException {
        Configuration cfg = new Configuration();
        try {
            //Load template from source folder
            //String current = new java.io.File( "." ).getCanonicalPath();
            //fg.setDirectoryForTemplateLoading(new File(
            //current));
            File file1=new File(System.getProperty("catalina.base")+File.separator+"webapps"+File.separator+"portal"+File.separator+"WEB-INF"+File.separator+"templates");
            cfg.setDirectoryForTemplateLoading(file1);
            Template template = cfg.getTemplate("wpstemplate.ftl");

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("package", CodegenUtils.defaultPackage);
            data.put("processName", className);
            data.put("extendingClass", extendingClass);

            //List parsing
            List<String> imports = CodegenUtils.getImports();
            List<String> inputIdentifiers = inputIdentifiersList;
            List<String> outputIdentifiers = outputIdentifiersList;

            data.put("imports", imports);
            data.put("inputIdentifiers", inputIdentifiers);
            data.put("outputIdentifiers", outputIdentifiers);
            data.put("inputMappings",inputMappings);
            data.put("outputMappings",outputMappings);
            data.put("executionServlet",portalConfiguration.getRestServiceConfig().getServerUrl()+servlet);

            Map<String,String> inputBindings =inputBindingsList;
            Set<Map.Entry<String, String>> entriesIn = inputBindings.entrySet();
            for ( Map.Entry<String, String> entry : entriesIn ) {
                if (!data.containsKey(entry.getKey())) {
                    data.put(entry.getKey(), entry.getValue());
                }
            }

            Map<String,String> outputBindings =outputBindingsList;
            Set<Map.Entry<String, String>> entriesOut = inputBindings.entrySet();
            for ( Map.Entry<String, String> entry : entriesOut ) {
                if (!data.containsKey(entry.getKey())) {
                    data.put( entry.getKey(), entry.getValue() );
                }
            }

            //Generate the class using wps template
            Writer file = new FileWriter(new File(className+".java"));
            template.process(data, file);
            file.flush();
            file.close();

            StringBuilder builder=new StringBuilder();
            String currentLine;
            BufferedReader br;
            br = new BufferedReader(new FileReader(className+".java"));
            while ((currentLine = br.readLine()) != null) {
                builder.append(currentLine);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            throw new PortalException("Error while file operations",e);
        } catch (TemplateException e) {
            throw new PortalException("Template exception when generating the template");
        }
    }

    public AiravataClientAPIService getAiravataClientAPIService() {
        return airavataClientAPIService;
    }

    public void setAiravataClientAPIService(AiravataClientAPIService airavataClientAPIService) {
        this.airavataClientAPIService = airavataClientAPIService;
    }

    public PortalConfiguration getPortalConfiguration() {
        return portalConfiguration;
    }

    public void setPortalConfiguration(PortalConfiguration portalConfiguration) {
        this.portalConfiguration = portalConfiguration;
    }
}
