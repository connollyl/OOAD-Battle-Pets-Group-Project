package UI;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class contains static methods for all generic I/O functions
 *
 * @author Eric Weber
 */
public class genericUI
{

    /**
     * Path to prepend to fileName
     */
    protected static final String UI_FOLDER_PATH = "/UI/Properties/";
    
    /**
     * file type to append to fileName
     */
    protected static final String PROPERTIES = ".properties";

    /**
     * fileName determined by the calling class
     */
    protected String PROPERTY_FILE_NAME;
    
    /**
     * Properties object to be used by the calling class
     */
    protected Properties constStrings;

    /**
     * Returns the assembled reference path to the .properties file with
     * name fileName
     *
     * @param fileName name of .properties file without a file extension
     *
     * @return path to file with name fileName
     */
    private static String getPropertiesFilePath(String fileName)
    {
        return UI_FOLDER_PATH + fileName + PROPERTIES;
    }

    /**
     * Returns a Properties object for a given .property file
     *
     * @param fileName name of property file without file extension
     *
     * @return Properties object representing the .property file with 
     * name fileName
     */
    private static Properties loadPropertyFile(String fileName)
    {
        Properties prop = new Properties();
        InputStream input = null;

        try
        {
            String filePath = getPropertiesFilePath(fileName);
            input = genericUI.class.getResourceAsStream(filePath);
            prop.load(input);

            return prop;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Returns a boolean value that represents if the given String is contained
     * inside the given String array
     * 
     * @param arr array of Strings
     * @param str String to serarch for
     * @return boolean value.
     *              TRUE - The array contains the string
     *              FALSE - The array doesn't contain the string
     */
    protected static boolean arrayContains(String[] arr, String str)
    {
        for(String idx: arr)
        {
            if(idx.equals(str))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * The .property file with name fileName containing the 'constant strings'
     * Used for output messages
     *
     * @param fileName name of .property file without file extension
     *
     * @return Properties object containing constant strings
     */
    public static Properties getConstStrings(String fileName)
    {
        return loadPropertyFile(fileName);
    }

    /**
     * String from property file prop with key propertyName
     * 
     * @param propertyName Name (key) of property in properties file
     * @param prop properties file to search in
     * @return String (value) with Name (key) propertyName
     */
    private String prop(String propertyName, Properties prop)
    {
        return prop.getProperty(propertyName);
    }

    /**
     * Wrapper method for prop(String propertyName, Properties prop)
     * that uses the Properties object constStrings from the calling class
     *
     * @param propertyName Name(key) of string to find in this.constStrings
     *
     * @return Contents of string propertyName
     */
    protected String prop(String propertyName)
    {
        return prop(propertyName, this.constStrings);
    }

}
