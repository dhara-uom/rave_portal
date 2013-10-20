/*
package org.dhara.portal.web.Test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.n52.wps.io.data.*;
import org.n52.wps.io.data.binding.complex.*;
import org.n52.wps.io.data.binding.literal.*;
import org.n52.wps.server.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class NorthTest extends AbstractSelfDescribingAlgorithm {

//private static Logger LOGGER = Logger.getLogger(52NorthTest.class);

private final String inputIdentifier_input = "input";

private final String outputIdentifier_output = "output";
private final String USER_AGENT = "Mozilla/5.0";
private String url = "http://localhost:8090/portal/connect/ExecutionServlet";

@Override
public List<String> getInputIdentifiers() {
    List<String> identifierList = new ArrayList<String>();
        identifierList.add("input");
        return identifierList;
        }

        @Override
        public List<String> getOutputIdentifiers() {
            List<String> identifierList = new ArrayList<String>();
                identifierList.add("output");
                return identifierList;
                }

                @Override
                public Class<?> getInputDataType(String identifier) {
                if (identifier.equalsIgnoreCase(inputIdentifier_input)) {
                        return LiteralIntBinding.class;
                }
                return null;
                }

                @Override
                public Class<?> getOutputDataType(String identifier) {
                if (identifier.equalsIgnoreCase(outputIdentifier_output)) {
                        return LiteralStringBinding.class;
                }
                return null;
                }

                @SuppressWarnings({ "unchecked" })
                @Override
                public Map<String, IData> run(Map<String, List<IData>> inputData) {

                    List<IData> inputList = inputData.get("input");

                    if (inputList.size() == 0) {
                    throw new RuntimeException("Invalid Input Parameters");
                    }

                    //Other elements are attributes
                    IData inputWrapped = inputList.get(0);

                    String inputData1 = (String) inputWrapped.getPayload();
                    String end="end";
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject= null;
                    try {
                    jsonObject = (JSONObject) jsonParser.parse(sendGet(
                inputData1,
                end));    // send the HTTP request and parse
                    } catch (ParseException e) {
                    e.printStackTrace();
                    } catch (IOException e) {
                    e.printStackTrace();
                    }

                    Set keySet = jsonObject.keySet();
                    Iterator i = keySet.iterator();
                    Map<String, IData> resultMap = new HashMap<String, IData>();
                    while(i.hasNext()){
                    String key= (String)i.next();
                    String value= (String)jsonObject.get(key);
                    IData wrappedValue=null;
                            if("output".equalsIgnoreCase(key)) {
                            wrappedValue=new LiteralStringBinding(value);
                        }
                    resultMap.put(key,wrappedValue);
                    }
                    return resultMap;
                    }

                    private String sendGet(
                    String inputData,
                String end) throws IOException {

                   StringBuilder urlBuilder = new StringBuilder();
                    url= urlBuilder.append(url).append("?end=").append(end).append("&input=").append(inputData).append("&workflowId=52NorthTest").toString();
                   */
/* url = url+"?end=" + end +
                    +"&input="+inputData
                    +"&workflowId=52NorthTest";*//*

                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    // optional default is GET
                    con.setRequestMethod("GET");

                    //add request header
                    con.setRequestProperty("User-Agent", USER_AGENT);

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
            }
    }
*/
