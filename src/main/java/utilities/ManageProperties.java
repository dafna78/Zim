package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class ManageProperties extends CommonOps
{
    Properties properties;

    public ManageProperties() throws Exception
    {
        try
        {
            FileInputStream testsPropFile = new FileInputStream(getData("TestProperties"));
            properties = new Properties();
            properties.load(testsPropFile);
        }
        catch (FileNotFoundException e)
        {
            throw new Exception(String.format("Tests properties file cannot be found. %s.", getData("TestProperties")));
        }
        catch (IOException e)
        {
            throw new Exception(String.format("Tests properties file could not be loaded. %s.", getData("TestProperties")));
        }
    }

    /**
     * @param propertyName The required property name
     * @return returns the property value as a String
     * @throws Exception exception will be thrown if the property name cannot be found
     */
    public String getProperty(String propertyName) throws Exception
    {
        //Verify the file contains the key
        if(!properties.containsKey(propertyName))
        {
            throw new Exception(String.format("Test property '%s' cannot be found in TestsProperties file", propertyName));
        }

        String propertyValue = properties.getProperty(propertyName);

        if(propertyValue == null || propertyValue.isEmpty())
        {
            throw new Exception(String.format("Test property '%s' returned an empty value", propertyName));
        }

        return propertyValue;
    }

    /**
     * Get all test properties from TestProperties file
     * @param testFile the test file the is running, i.e WebTests etc.
     * @param testName test test that is running within the file, i.e test01.
     * @return a list of the test properties excluding the file and test names
     */
    public HashMap<String, String> getTestProperties(String packageName, String testFile, String testName)
    {
        String propertyKey = String.format("%s.%s.%s", packageName, testFile, testName);
        Enumeration<?> propertyNames = properties.propertyNames();

        //List<String> testProperties = new ArrayList<>();
        HashMap<String, String> testProperties = new HashMap<String, String> ();

        while (propertyNames.hasMoreElements())
        {
            String param = propertyNames.nextElement().toString();
            if(param.contains(propertyKey))
            {
                String key = param.replace(propertyKey + ".", "");
                String value = properties.getProperty(param);
                testProperties.put(key, value);
            }
        }
        return testProperties;
    }
}
