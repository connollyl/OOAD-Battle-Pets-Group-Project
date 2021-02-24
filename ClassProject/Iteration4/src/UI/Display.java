package UI;

import java.io.InputStream;
import java.io.PrintStream;
import UI.Terminals.StreamTerminal;
import UI.Terminals.IdeTerminal;
import UI.Terminals.SystemTerminal;
import UI.Interfaces.Terminal;

/**
 * This class contains methods to display and read text from a Terminal.
 * Methods are also provided for input type checking, and returning number
 * values between a given range, or positive number values.
 *
 * @see UI.Interfaces.Terminal
 */
public class Display
{

    private Terminal terminal;
    private static Display disp = null;
    private static Display testing = null;

    /**
     * Used to specify the 'inclusivity' of both end points used in the
     * readBetween() method.
     *
     * INCLUSIVE - include numbers at range extremes [a,b]
     * EXCLUSIVE - excludes numbers at range extremes (a, b)
     */
    public static enum INCLUSIVITY
    {
        INCLUSIVE, EXCLUSIVE
    };

    /**
     * Creates a Display object.
     * Attempts to use java.io.Console to allow for proper screen clearing.
     * If java.io.Console is null, System.out is used instead
     *
     * @see java.io.Console
     * @see System.out
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
     * Creates an instance of Display using a given
     * InputStream and OutputStream
     *
     * @param is inputStream to read input from
     * @param ps outputStream to write information to
     *
     * @see java.io.InputStream
     * @see java.io.OutputStream
     */
    private Display(InputStream is, PrintStream ps)
    {
        terminal = new StreamTerminal(is, ps);
    }

    /**
     * Returns an instance of Display that reads and writes from files.
     * Used to debugging purposes. This instance of Display is independent
     * from the main instance of Display
     *
     * The testing display is a singleton
     *
     * @return instance of display
     */
    public static Display getTestingDisplay()
    {
        if(!testing.getClass().equals(StreamTerminal.class))
        {
            InputStream is = null;
            PrintStream ps = null;

            testing = new Display(is, ps);
        }
        return testing;
    }

    /**
     * Method used to get the singleton instance of the Display class
     *
     * @return instance of Display Class
     */
    public static Display getDisplay()
    {
        if(disp == null)
        {
            disp = new Display();
        }

        return disp;
    }

    /**
     * Writes text to display
     *
     * @param s String to be written to the display
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
     * Clears display
     *
     * @see UI.Interfaces.Terminal
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

    /**
     * Reads input from the terminal independent of the type of input
     *
     * @return Object read from the terminal
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
     * Returns a boolean based on if the Object o is a member of
     * Class c
     *
     * @param o Object to compare
     * @param c Class to compare instance of
     *
     * @return true if is of type c.
     */
    private boolean isTypeOf(Object o, Class c)
    {
        if(o.getClass().equals(c))
        {
            return true;
        }

        return parsePrimative.samePrimativeClass(c, o);
    }

    /**
     * Writes a general input error to the terminal
     */
    private void displayInputGeneralError()
    {
        println("Error parsing Input");
    }

    /**
     * Writes error message prompting the input of a valid instance of
     * Class c
     *
     * @param c Class that the input Object should be a member of
     */
    private void displayInputTypeError(Class c) throws Exception
    {
        println("Invalid input. Please enter a valid " + c.getSimpleName());
    }

    /**
     * Writes an error that the input value is not in the range
     *
     * @param lower lower bound of the range
     * @param upper upper bound of the range
     * @param c     Class that inputs and bounds are instances of
     * @param inc   member of INCLUSIVITY Enum
     *
     * @see UI.Display.INCLUSIVITY
     */
    private void displayInputRangeError(String lower, String upper, Class c, INCLUSIVITY inc)
    {
        String inclusivity = "inclusively";

        if(inc == INCLUSIVITY.EXCLUSIVE)
        {
            inclusivity = "exclusivly";
        }

        println("Invalid input. Please enter a valid " + c.getSimpleName()
                + " between " + lower + " and " + upper + " " + inclusivity);
    }

    /**
     * Writes a fatal to System.err and exits the application
     */
    private void fatalTerminalError()
    {
        System.err.println("Fatal Error has been encountered");
        System.exit(0);
    }

    /**
     * Writes a message to System.err
     *
     * @param msg message to be written to System.err
     */
    private void printSystemError(String msg)
    {
        System.err.println(msg);
    }

    /**
     * Reads input from terminal and verifies it is an instance of Class c
     *
     * @param c Class that input should be an instance of
     *
     * @return String representation of input that is verified
     *         to be an instance of Class c
     */
    public String read(Class c)
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
            printSystemError("ERROR... IE Exception in Class Display  Method read()");
            ie.printStackTrace();
        }
        catch(ClassCastException cce)
        {
            //This statement should not be reachable because of error checking
            printSystemError("ERROR... CCE Exception in Class Display  Method read()");
            cce.printStackTrace();
        }
        catch(Exception e)
        {
            printSystemError("Error, unexpected Exception in Class Display, Method read()");
            e.printStackTrace();
        }

        //This value should never be NULL
        return returnObject;
    }

    /**
     * Returns a verified valid String from the terminal
     *
     * @return String input to terminal
     */
    public String readString()
    {
        return read(String.class);
    }

    /**
     * Returns a verified valid Integer from the terminal
     *
     * @return integer input from terminal
     */
    public int readInt()
    {
        String str = read(int.class);
        return Integer.parseInt(str);
    }

    /**
     * Returns a verified valid double from the terminal
     *
     * @return double input from terminal
     */
    public double readDouble()
    {
        String str = read(double.class);
        return Double.parseDouble(str);
    }

    /**
     * Returns a verified valid long from the terminal
     *
     * @return long input from terminal
     */
    public long readLong()
    {
        String str = read(long.class);
        return Long.parseLong(str);
    }

    /**
     * Returns a verified valid boolean from the terminal
     *
     * @return boolean input from terminal
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
     * Returns an integer between lower and upper bounds. The inclusivity of
     * bounds is specified by inc
     *
     * @param lower lower bound of range
     * @param upper upper bound of range
     * @param inc   member of INCLUSIVITY Enum, specifies inclusivity of endpoints
     *
     * @return integer value between upper and lower bounds
     *
     * @see UI.Display.INCLUSIVITY
     */
    public int readBetween(int lower, int upper, INCLUSIVITY inc)
    {
        int lowerVal = lower;
        int upperVal = upper;

        if(inc == INCLUSIVITY.EXCLUSIVE)
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
     * Returns an double between lower and upper bounds. The inclusivity of
     * bounds is specified by inc
     *
     * @param lower lower bound of range
     * @param upper upper bound of range
     * @param inc   member of INCLUSIVITY Enum, specifies inclusivity of endpoints
     *
     * @return double value between upper and lower bounds
     *
     * @see UI.Display.INCLUSIVITY
     */
    public double readBetween(double lower, double upper, INCLUSIVITY inc) //e must implement compareable for this method to work
    {
        double lowerVal = lower;
        double upperVal = upper;

        if(inc == INCLUSIVITY.EXCLUSIVE)
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
     * Writes a blank line to the terminal
     */
    public void blankln()
    {
        println("");
    }

    /**
     * Writes n blank lines to the terminal
     *
     * @param repetition n number of blank lines to write
     */
    public void blankln(int repetition)
    {
        for(int i = 0; i < repetition; i++)
        {
            blankln();
        }
    }

    /**
     * Returns a verified positive integer
     *
     * @return positive of integer
     */
    public int readPositiveInt()
    {
        return readBetween(1, Integer.MAX_VALUE - 1, INCLUSIVITY.INCLUSIVE);
    }

    /**
     * Returns a verified positive double
     *
     * @return positive integer
     */
    public double readPositiveDouble()
    {
        return readBetween(0.0, Double.MAX_VALUE - 1, INCLUSIVITY.EXCLUSIVE);
    }

}
