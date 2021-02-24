package UI.Terminals;

import java.io.PrintStream;
import java.util.Scanner;
import UI.Interfaces.Terminal;

/**
 * Implementation of System.out and System.in using the 
 * Terminal interface
 * @see UI.Interfaces.Terminal
 * @author weberer
 */
public class IdeTerminal implements Terminal
{

    /**
     * Number of lines to write to simulate screen clearing
     */
    private static final int NUM_CLS_LINES = 100;

    /**
     * Scanner used to read input
     */
    private final Scanner input;
    
    /**
     * PrintStream to write output
     */
    private final PrintStream output;

    /*
     * Creates an IdeTerminal interface
     * using System.in and System.out
     */
    public IdeTerminal()
    {
        input = new Scanner(System.in);
        output = System.out;
    }

    /**
     * {@inheritDoc }
     * @param s String to write
     * @throws Exception error writing string
     */
    @Override
    public void write(String s) throws Exception
    {
        output.println(s);
    }

    /**
     * Simulates clearing the screen by printing NUM_CLS_LINES 
     * blank lines
     * @throws Exception error when clearing screen
     */
    @Override
    public void clearScreen() throws Exception
    {
        for(int i = 0; i < NUM_CLS_LINES; i++)
        {
            output.println();
        }
    }

    /**
     * {@inheritDoc }
     * @return Object read in
     * @throws Exception when reading in object
     */
    @Override
    public Object read() throws Exception
    {
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     * @return boolean corresponding to both System.in and System.out 
     * being succesfully instantiated
     */
    @Override
    public boolean isTerminalValid()
    {
        return (input != null && output != null);
    }

}
