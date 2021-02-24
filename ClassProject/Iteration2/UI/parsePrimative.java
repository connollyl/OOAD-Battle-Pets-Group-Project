package UI;
/**
 * This class handles all parsing of primitive types within the properties files.
 * @author Eric Weber, Logan Garza
 *
 */
public class parsePrimative 
{
    private static final String[] TRUE_BOOLEAN_VALUES = {"TRUE", "YES", "T", "Y"};
    private static final String[] FALSE_BOOLEAN_VALUES = {"FALSE", "NO", "F", "N"};
    /**
     * Determines if input is of type integer.
     * @param val is string representation of parser input
     * @return true if integer, false otherwise.
     */
    private static boolean isInt(String val)
    {
        try
        {
            Integer.parseInt(val);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    /**
     * Determines if input is of type double.
     * @param val is string representation of parser input
     * @return true if double, false otherwise.
     */
    private static boolean isDouble(String val)
    {
        try
        {
            Double.parseDouble(val);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    /**
     * Determines if input is of type Long.
     * @param val is string representation of parser input
     * @return true if Long, false otherwise.
     */
    private static boolean isLong(String val)
    {
        try
        {
            Long.parseLong(val);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    /**
     * Determines if input is of type boolean.
     * @param val is string representation of parser input
     * @return true if boolean, false otherwise.
     */
    private static boolean isBoolean(String val)
    {        
       return (isBooleanTrue(val) || isBooleanFalse(val));        
    }
    /**
     * Determines if given string value represents any true boolean value.
     * @param val is string representation of parser input
     * @return true if string represents true boolean value, false otherwise.
     */
    private static boolean isBooleanTrue(String val)
    {        
        String str = val.toUpperCase();
        
        for (String tbv : TRUE_BOOLEAN_VALUES)
        {
            if (tbv.equals(str)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Determines if given string value represents any false boolean value.
     * @param val is string representation of parser input
     * @return true if string represents false boolean value, false otherwise.
     */
    private static boolean isBooleanFalse(String val)
    {
         String str = val.toUpperCase();
         
        for (String tbv : FALSE_BOOLEAN_VALUES)
        {
            if (tbv.equals(str)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Determines if given string value represents any primitive types.
     * @param val is string representation of parser input
     * @return true if string represents true boolean value, false otherwise.
     */
    public static boolean samePrimativeClass(Class c1, Object o)
    {
        String str = (String)o;
        
       if(c1.equals(int.class))
       {
           return isInt(str);
       }
       if(c1.equals(long.class))
       {
           return isLong(str);
       }
       if(c1.equals(double.class))
       {
           return isDouble(str);
       }
       if(c1.equals(boolean.class))
       {
           return isBoolean(str);
       }
       return false;
    }
    /**
     * Determines if given string value represents any valid boolean value.
     * @param val is string representation of parser input
     * @return true if string represents boolean value, false otherwise.
     */
    public static boolean getBooleanValue(String str) throws Exception
    {
        if(isBoolean(str))
        {
            return isBooleanTrue(str);
        }
        throw new Exception("Not a valid Boolean");
    }
}
