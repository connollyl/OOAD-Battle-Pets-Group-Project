/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class handles the all general ui input and output.
 * @author weberer
 */
public class genericUI
{
    protected static final String UI_FOLDER_PATH = "/UI/Properties/";
    protected static final String PROPERTIES = ".properties";
    
    protected String PROPERTY_FILE_NAME;
    protected Properties constStrings;
    /**
     * Retrieves the file path of properties for given file.
     * @param fileName is file trying to retrieved
     * @return full name of file in form of string.
     */
    private static String getPropertiesFilePath(String fileName)
    {
        return UI_FOLDER_PATH + fileName + PROPERTIES;
    }
     /**
     * Retrieves the properties file for given file name.
     * @param fileName is full filename
     * @return the properties file.
     */
    private static Properties loadPropertyFile(String fileName)
    {
        Properties prop = new Properties();
        InputStream input = null;
        
        try
        {     
            String filePath = getPropertiesFilePath(fileName);
            input = ClassLoader.class.getResourceAsStream(filePath);
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
     * A generic function for checking if given array contains the given string.
     * @param arr is array generated from properties file.
     * @param str is string being searched for.
     * @return true if found, false otherwise.
     */
    protected static boolean arrayContains(String[] arr, String str)
    {
        for (String idx : arr) {
            if (idx.equals(str)) {
                return true;
            }
        }            
        return false;
    }
    /**
     * Returns content string to be displayed to screen from given file.
     * @param fileName is properties file be parsed.
     * @return the content string.
     */
    public static Properties getContStrings(String fileName)
    {
        return loadPropertyFile(fileName);
    }
    /**
     * Returns property name.
     * @param fileName is properties file be parsed.
     * @return the content string.
     */
    private String prop(String propertyName, Properties prop)
    {
        return prop.getProperty(propertyName);
    }
    /**
     * Returns the property based on given propery name.
     * @param propertyName
     * @return
     */
    protected String prop(String propertyName)
    {
        return prop(propertyName, this.constStrings);
    }
}
