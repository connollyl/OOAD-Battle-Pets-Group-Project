package UI.Terminals;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import UI.Interfaces.Terminal;

/**
 * Contains implementation of Terminal interface
 * using runtime specified InputStream and PrintStream
 * @author weberer
 * @see UI.Interfaces.Terminal
 */
public class StreamTerminal implements Terminal
{

    /**
     * Scanner used to read input
     */
    private final Scanner input;
    
    /**
     * PrintStream to write output
     */
    private final PrintStream output;

    /**
     * Creates the StreamTerminal object
     * @param is InputStream to read from
     * @param os OutputStream to write to
     */
    public StreamTerminal(InputStream is, PrintStream os)
    {
        input = new Scanner(is);
        output = os;
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
     * Writes a string to the output to simulate clearing the screen
     * @throws Exception error when clearing screen
     */
    @Override
    public void clearScreen() throws Exception
    {
        String cls = "--Cleared Screen--";

        output.println(cls);
    }

    /**
     * {@inheritDoc }
     * @return Object read in
     * @throws Exception when reading in object
     */
    @Override
    public Object read() throws Exception
    {
        Object o = input.next();
        return o;
    }

    /**
     * {@inheritDoc}
     * @return boolean corresponding to both input and output having
     * being succesfully instantiated
     */
    @Override
    public boolean isTerminalValid()
    {
        return (input != null && output != null);
    }

}
