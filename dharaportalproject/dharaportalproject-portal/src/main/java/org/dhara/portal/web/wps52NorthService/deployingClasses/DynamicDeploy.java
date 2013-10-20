/*


package org.dhara.portal.web.wpsService52.deployingClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;





import java.util.*;



import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.n52.wps.io.data.IData;
import org.n52.wps.io.data.binding.literal.LiteralIntBinding;
import org.n52.wps.io.data.binding.literal.LiteralStringBinding;
import org.n52.wps.server.AbstractSelfDescribingAlgorithm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

*/
/*
 * This is a sample 52North WPS Process Class.
 * It inherits from AbstractSelfDescribingAlgorithm to automcatically create the ProcessDescription document.  *//*

public class DynamicDeploy extends AbstractSelfDescribingAlgorithm {

      */
/*
     * Method to retrieve the identifiers for the input data elements.
     * The ProcessDescription is automatically created with this data.
     *
     * @return List of input identifiers
     *//*


    private static Map<String, Object> outputs;
    private final String USER_AGENT = "Mozilla/5.0";
    private String url = "http://localhost:8093/J2EETEST/MyServlet"; //"http://localhost:8093/jsontest/json";

    @Override
    public List<String> getInputIdentifiers() {
        List<String> identifiers = new ArrayList<String>();
        identifiers.add("input1");       // inputs given by url
        identifiers.add("input2");
        return identifiers;
    }

    */
/*
     * Method to retrieve the identifiers for the output data elements.
     * The ProcessDescription is automatically created with this data.
     *
     * @return List of output identifiers
     *//*

    @Override
    public List<String> getOutputIdentifiers() {
        List<String> identifiers = new ArrayList<String>();

        identifiers.add("output1");    // keys sent from web app
        identifiers.add("output2");

        return identifiers;
    }


    */
/*
     * This method returns the Class of the accepted Inputdata format.
     * It is used to determine a suitable parser and automatically announce accepted datatypes for input data in the DescribeProcess response.
     *
     * @param identifier Identifier of the Inputdata element (see ProcessDescription)
     * @return Class of the accepted Inputdata format based on the given identifier.
     *//*

    @Override
    public Class getInputDataType(String identifier) {
        if (identifier.equals("input1")) {
            return LiteralIntBinding.class;
        }
        else if (identifier.equals("input2")){
            return LiteralIntBinding.class;
        }

        throw new RuntimeException("Error: Wrong identifier");
    }


    */
/*
     * This method returns the Class of the accepted Outputdata format.
     * It is used to determine a suitable generator and automatically announce provided datatypes for output data in the DescribeProcess response.
     *
     * @param identifier Identifier of the Outputdata element (see ProcessDescription)
     * @return Class of the accepted Oututdata format based on the given identifier.
     *//*

    @Override
    public Class getOutputDataType(String identifier) {

        if(identifier.equals("output1")){
            return LiteralStringBinding.class;
        }
        if(identifier.equals("output2")){
            return LiteralStringBinding.class;
        }


        throw new RuntimeException("Error: Wrong identifier");
    }

    */
/*
     * Main method to perform the processing.
     * All business logic shall go in this method.
     *
     *
     * @param inputMap Parsed inputdata identified by the identifiers provided in the getInputIdentifiers()  method and automatically announced in the DescribeProcess response
     * @return Map containing the output data as IData identified by the identifiers given in getOutputIdentifiers().
     *//*

    @Override
    public Map<String, IData> run(Map<String, List<IData>> inputMap) {

        System.out.println("");
        System.out.println("test10");
        System.out.println("");

        List<IData> input1List = inputMap.get("input1");

        List<IData> input2List = inputMap.get("input2");

        if (input1List.size() == 0) {
            throw new RuntimeException("Invalid Input Parameters");
        }
        if (input2List.size() == 0) {
            throw new RuntimeException("Invalid Input Parameters input2");
        }

        IData input1Wrapped = input1List.get(0);      //other elements are attributes
        IData input2Wrapped = input2List.get(0);

        //Must put class check
        Integer inputData1 = (Integer) input1Wrapped.getPayload();
        Integer inputData2 = (Integer) input2Wrapped.getPayload();




            JSONParser jsonParser = new JSONParser();
            org.json.simple.JSONObject jsonObject= null;
            try {
                jsonObject = (org.json.simple.JSONObject) jsonParser.parse(sendGet(inputData1,inputData2));    // send the HTTP request and parse
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
                IData wrappedValue = new LiteralStringBinding(value);
                resultMap.put(key,wrappedValue);
            }


            return resultMap;
    }

    private String sendGet(Integer inputData1, Integer inputData2) throws IOException {

        url = url + "?input1=" + inputData1 +"&input2="+inputData2;

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();


    }


}


*/
