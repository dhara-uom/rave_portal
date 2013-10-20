package org.dhara.portal.web.wps52NorthService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class WPSConnect52ServiceImpl implements WPS52NorthService{
    private WPS52NorthConfig wps52NorthConfig;
    public void uploadClass(String generatedClass,String workFlowId) throws IOException {

        String defaultPackage = wps52NorthConfig.getDefaultPackage();
        String classNameWithPackages = defaultPackage.substring(8,defaultPackage.length()-1)+"."+workFlowId;


        String encodedClass = URLEncoder.encode(generatedClass, WPSNorthServiceConstants.UTF8);
        String encodedClassName=URLEncoder.encode(classNameWithPackages, WPSNorthServiceConstants.UTF8);

        String inputAdjusted = "class=" + encodedClass+"&"+"classname="+encodedClassName;

        URL obj = new URL(wps52NorthConfig.getServerUrl());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        // optional default is GET
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("charset", "utf-8");
        con.setRequestProperty("Content-Length", "" + Integer.toString(inputAdjusted.getBytes().length));
        con.setUseCaches(false);

        //add request header
        con.setRequestProperty("User-Agent", WPSNorthServiceConstants.USER_AGENT);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(inputAdjusted);
        wr.flush();
        wr.close();
        con.disconnect();

        int responseCode = con.getResponseCode();
        /*System.out.println("\nSending 'POST' request to URL : " + WPSNorthServiceConstants.WPS_52NORTH_URL);
        System.out.println("Post parameters : " + encoded);
        System.out.println("Response Code : " + responseCode);
        */
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        /*//print result
        System.out.println(response.toString());*/

    }

    public WPS52NorthConfig getWps52NorthConfig() {
        return wps52NorthConfig;
    }

    public void setWps52NorthConfig(WPS52NorthConfig wps52NorthConfig) {
        this.wps52NorthConfig = wps52NorthConfig;
    }
}
