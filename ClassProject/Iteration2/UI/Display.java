package UI;

//The UI methods, Battle Setup UI, Console UP, and Round UI will all have a 

import UI.Terminals.StreamTerminal;
import UI.Terminals.IdeTerminal;
import UI.Terminals.SystemTerminal;
import UI.Interfaces.Terminal;
import java.io.InputStream;
import java.io.PrintStream;
/**
 * 
 * Reference to a display object, which will be the same object b/c it is a singleton
 * They will each in turn call display methods, which will be routed to the proper
 * Terminal for how/where the application is running.
 */


public class Display 
{
    private Terminal terminal;
    private static Display disp = null;
    private static Display testing = null;
    
    public static enum INCLUSIVITY {INCLUSIVE, EXCLUSIVE};
    
    /**
     * Standard Constructor for display.
     */
    private Display()
    {
        terminal = new SystemTerminal();
        
        if(!terminal.isTerminalValid())
        {
            terminal = new IdeTerminal();
             
            if(!terminal.isTerminalValid())
            {
                fatalTerminalError();
            }
        }
    }
     /**
     * Private Constructor for display.
     * @param is is input stream for display.
     * @param ps is print stream for display.
     */
    private Display(InputStream is, PrintStream ps)
    {
        terminal = new StreamTerminal(is, ps);
    }
    
    /**
    * Returns testing display
    * @return
    */
    public static Display getTestingDisplay()
    {
        if(!testing.getClass().equals(StreamTerminal.class))
        {
            InputStream is = null;
            PrintStream ps = null;
            
            //Redefine the Streams here to point to the proper files for testing
            //Testing 'display' is independent of the retular display.
            
            testing = new Display(is, ps);
        }
        return testing;
    }
    /**
     * Returns text display used in game
     * @return the display.
     */
    public static Display getDisplay()
    {
        //Singleton
        if(disp == null)
            disp = new Display();
        
        return disp;
    }
    
     /**
     * Writes text to display
     * @param s is string meant to be displayed.
     */ 
    public void println(String s)
    {
        try
        {
            terminal.write(s);
        }
        catch(Exception e)
        {
            fatalTerminalError();
        }
    }
    
    /**
     * Clears display (Adds 50 new lines)
     */
    public void clearScreen()
    {
        try
        {
            terminal.clearScreen();
        }
        catch(Exception e)
        {
            fatalTerminalError();
        }
    }
    //Interface method
    /**
     * Reads texts from display, converts
     * text into object and then returns object
     * @return the object based from string read in.
     */
    private Object readObject()
    {
        try
        {
            Object o = terminal.read();
            return o;
        }
        catch(Exception e)
        {
            fatalTerminalError();
            return null;
        }
    }    
     /**
     * Checks type of object, if the object is of type
     * c then return true;
     * @param o object being compared
     * @param c is class that object is being compared to.
     * @return true if is of type c.
     */
    private boolean isTypeOf(Object o, Class c)
    {
        if(o.getClass().equals(c))
            return true;
        
        return parsePrimative.samePrimativeClass(c, o);
    }
    
    //Used for invalid type input by private private methods...
    //START COMMENT SECTION
    /**
     * Display input type error to display.
     * @param c 
     * @throws Exception if input is incorrect.
     */
    private void displayInputGeneralError()
    {
        println("Error parsing Input");
    }
     
    //Used for invalid type input by private private methods...
    //START COMMENT SECTION 
    /**
     * Displays parsing error message.
     */
    private void displayInputTypeError(Class c) throws Exception
    {
        println("Invalid input. Please enter a valid " + c.getSimpleName());
    }
     /**
     * Display input type error to display.
     * @param c 
     * @throws Exception if input is incorrect.
     */
    private void displayInputRangeError(String lower, String upper, Class c, INCLUSIVITY inc)
    {
        String inclusivity = "inclusively";
        
        if(inc == INCLUSIVITY.EXCLUSIVE)
        {
            inclusivity = "exclusivly";
        }
        
        println("Invalid input. Please enter a valid " + c.getSimpleName() + 
                " between " + lower + " and " + upper + " " + inclusivity);
    }
    /**
     * Displays fatal error message.
     */
    private void fatalTerminalError()
    {
        System.out.println("Fatal Error has been encountered");
        System.exit(0);
    }
    //END COMMENT SECTION
    /**
     * Reads user input from display.
     * @param c
     * @return object based on input.
     */
    public String read(Class c) //will be implemented by all other read methods
    {
        String returnObject = null;
        
        try            
        {             
            Object input = readObject();
            if(isTypeOf(input, c))
            {
                returnObject = (String)input;
            }
            else
            {            
                displayInputTypeError(c);
                returnObject = read(c);
            }
            
        }
        catch(InstantiationException ie)
        {
            System.out.println("ERROR... IE Exception in Class Display  Method <T>read");
            ie.printStackTrace();
        }
        catch(ClassCastException cce)
        {
            //Shouldn't be reachable because of isTypeOf() check
            System.out.println("ERROR... CCE Exception in Class Display  Method <T>read");
            cce.printStackTrace();
        }
        catch(Exception e)
        {
            System.out.println("Error, unexpected Exception in Class Display, Method <T>read");
            e.printStackTrace();
        } 
        
        //Should never be null
        return returnObject;
    }
     /**
     * Reads string values from display input.
     * @return string value;
     */
    public String readString()
    {
        return read(String.class);
    }
    /**
     * Reads integer values from display input.
     * @return integer value;
     */
    public int readInt()
    {
        String str = read(int.class);
        return Integer.parseInt(str);
    }
    /**
     * Reads double values from display input.
     * @return double value;
     */
    public double readDouble()
    {
        String str = read(double.class);
        return Double.parseDouble(str);
    }
    /**
     * Reads long values from display input.
     * @return long value;
     */
    public long readLong()
    {
        String str = read(long.class);
        return Long.parseLong(str);
    }
    /**
     * Reads boolean values from display input.
     * @return boolean value;
     */
    public boolean readBoolean()            
    {
        String str = read(boolean.class);
        try
        {
            return parsePrimative.getBooleanValue(str);
        }
        catch(Exception e)
        {
            try
            {
                displayInputTypeError(boolean.class);
            }
            catch(Exception e1)
            {
                fatalTerminalError();
            }
        }
        return false;
    }
    /**
     * Reads integer values from display input.Makes sure input is in between lower and upper limits before continuing.
     * @return integer value;
     */
    public int readBetween( int lower, int upper, INCLUSIVITY inc ) //e must implement compareable for this method to work
    {     
        int lowerVal = lower;
        int upperVal = upper;
        
        if( inc == INCLUSIVITY.EXCLUSIVE)
        {
            lowerVal++;
        }
        else
        {
            upperVal++;
        }
        
        int input = readInt();
        
        while(!(input >= lowerVal && input < upperVal))
        {
            displayInputRangeError(Integer.toString(lower), Integer.toString(upper), int.class, inc);
            input = readInt();
        }
        
        return input;
    }
    /**
     * Reads double values from display input.Makes sure input is in between lower and upper limits before continuing.
     * @return double value;
     */
    public double readBetween( double lower, double upper, INCLUSIVITY inc ) //e must implement compareable for this method to work
    {        
        double lowerVal = lower;
        double upperVal = upper;
        
        if( inc == INCLUSIVITY.EXCLUSIVE)
        {
            lowerVal++;
        }
        else
        {
            upperVal++;
        }
        
        double input = readDouble();
        
        while(!(input >= lowerVal && input < upperVal))
        {
            displayInputRangeError(Double.toString(lower), Double.toString(upper), double.class, inc);
            input = readDouble();
        }
        
        return input;
    }
    /**
     * Prints a singular blank line to display.
     */
    public void blankln()
    {
        println("");
    }
    /**
     * Prints multiple blank lines to display.
     */
    public void blankln(int repetition)
    {
        for(int i = 0; i < repetition; i++)
            blankln();
    }
    /**
     * Reads in integer, and makes sure it is positive.
     * @return
     */
    public int readPositiveInt()
    {
        return readBetween(1, Integer.MAX_VALUE - 1, INCLUSIVITY.INCLUSIVE);
    }
      /**
     * Reads in double, and makes sure it is positive.
     * @return
     */
    public double readPositiveDouble()
    {
        return readBetween(0.0, Double.MAX_VALUE - 1, INCLUSIVITY.EXCLUSIVE);
    }
    
    
    //--------------------------------------------------------------------------
    /*public static void main(String[] args)
    {
       Display disp = new Display();
        
        /*disp.println("Input a String:");
        String s = disp.readString();
        disp.clearScreen();
        disp.println(">>" + s);
        disp.println("Input an integer: ");
        int i = disp.readInt();
        disp.println(">>" + i);
        disp.println("Input a double");
        double d = disp.readDouble();
        disp.println(">>" + d);
        disp.println("Input a Y or N");
        boolean b = disp.readBoolean();
        disp.println(">>" + b);
        disp.println("Input a Y or N");
        b = disp.readBoolean();
        disp.println(">>" + b);
        disp.println("Input Yes or N0");
        b = disp.readBoolean();
        disp.println(">>" + b);
        disp.println("Input Yes or N0");
        b = disp.readBoolean();
        disp.println(">>" + b);
        disp.println("Enter an int between 1 and 10 inclusive");
        int in = disp.readBetween(1, 10, INCLUSIVITY.INCLUSIVE);
        disp.println(">>" + in);
        disp.println("Enter an int between 1 and 10 exclusive");
        int in = disp.readBetween(1, 10, INCLUSIVITY.EXCLUSIVE);
        disp.println(">>" + in);
        disp.println("Enter an double between 1 and 10 inclusive");
        double dbl = disp.readBetween(1.0, 10.0, INCLUSIVITY.INCLUSIVE);
        disp.println(">>" + dbl);
        disp.println("Enter an double between 1 and 10 exclusive");
        dbl = disp.readBetween(1.0, 10.0, INCLUSIVITY.EXCLUSIVE);
        disp.println(">>" + dbl);
        
        disp.println("Enter a positive integer");
        int in = disp.readPositiveInt();
        disp.println(">>" + in);
        
        disp.println("Enter a positive double");
        double dbl = disp.readPositiveDouble();
        disp.println(">>" + dbl);
    }*/
}
