package org.dhara.portal.web.portalServices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 6/1/13
 * Time: 8:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisualizationServices {

    public boolean KMLDataVisualizationService(String KMLData)
    {
        String KMLFileLocation="/users/mkyong/filename.txt";
        try {
            File file = new File(KMLFileLocation);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(KMLData);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
      return true;
    }
}
