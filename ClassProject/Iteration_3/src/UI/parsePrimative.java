package UI;

/**
 * Used to determine the type of an Object if the given object is a primitive
 * data type.
 * Also contains methods for parsing boolean values from strings
 *
 * @author Eric Weber
 *
 */
public class parsePrimative
{

    /**
     * Acceptable input strings that map to 'true'
     */
    private static final String[] TRUE_BOOLEAN_VALUES =
    {
        "TRUE", "YES", "T", "Y"
    };

    /**
     * Acceptable input strings that map to 'false'
     */
    private static final String[] FALSE_BOOLEAN_VALUES =
    {
        "FALSE", "NO", "F", "N"
    };

    /**
     * Returns a boolean value specified by if the String val
     * is an int
     *
     * @param val is string to check
     *
     * @return boolean value TRUE if val is an int, FALSE otherwise
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
     * Returns a boolean value specified by if the String val
     * is a double
     *
     * @param val is string to check
     *
     * @return boolean value TRUE if val is a double, FALSE otherwise
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
     * Returns a boolean value specified by if the String val
     * is a long
     *
     * @param val is string to check
     *
     * @return boolean value TRUE if val is a long, FALSE otherwise
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
     * Returns a boolean value specified by if the String val
     * is a boolean
     *
     * @param val is string to check
     *
     * @return boolean value TRUE if val is a boolean, FALSE otherwise
     */
    private static boolean isBoolean(String val)
    {
        return (isBooleanTrue(val) || isBooleanFalse(val));
    }

    /**
     * Returns a boolean value corresponding to the string being contained
     * inside the list of know true strings TRUE_BOOLEAN_VALUES
     *
     * @param val string to check
     *
     * @return TRUE if string is in TRUE_BOOLEAN_VALUES, FALSE otherwise
     */
    private static boolean isBooleanTrue(String val)
    {
        String str = val.toUpperCase();

        for(String tbv: TRUE_BOOLEAN_VALUES)
        {
            if(tbv.equals(str))
            {
                return true;
            }
        }
        return false;
    }

        /**
     * Returns a boolean value corresponding to the string being contained
     * inside the list of know true strings FALSE_BOOLEAN_VALUES
     *
     * @param val string to check
     *
     * @return TRUE if string is in FALSE_BOOLEAN_VALUES, FALSE otherwise
     */
    private static boolean isBooleanFalse(String val)
    {
        String str = val.toUpperCase();

        for(String tbv: FALSE_BOOLEAN_VALUES)
        {
            if(tbv.equals(str))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a boolean corresponding to Object o being an instance
     * of Class cl
     * 
     * @param c1 Class for the Object to be an instance of
     * @param o Object to check
     * @return TRUE if object o is an instance of Class cl, FALSE otherwies
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
     * Returns the boolean representation of a string containing a 
     * boolean-mappable value
     * 
     * @param str value containing string
     * @return boolean value of String str
     * @throws Exception if value is not a valid boolean
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
