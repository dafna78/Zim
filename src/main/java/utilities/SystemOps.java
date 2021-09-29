package utilities;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * System operation class
 */
public class SystemOps
{

    /**
     * Reads data from XML file
     * @param filePath path to the XML file
     * @param nodeName the node to read
     * @return returns the node value
     */
    public static String getDataFromXML (String filePath, String nodeName)
    {
        File fXmlFile;
        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;
        Document doc;
        String textToReturn = null;

        try
        {
            fXmlFile = new File(filePath);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            textToReturn = doc.getElementsByTagName(nodeName).item(0).getTextContent();
        }
        catch(NullPointerException e)
        {
            System.out.println("XML file does not contain node: " + nodeName);
        }
        catch(Exception e)
        {
            System.out.println("Exception in reading XML file: " + e);
        }

        return textToReturn;
    }
}
