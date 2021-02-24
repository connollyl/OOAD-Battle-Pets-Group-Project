
package UI.Terminals;

import UI.Interfaces.Terminal;
import java.io.PrintStream;
import java.util.Scanner;

public class IdeTerminal implements Terminal
{
    private static final int NUM_CLS_LINES = 100;
    
    private final Scanner input;
    private final PrintStream output;
    
    public IdeTerminal()
    {
        input = new Scanner(System.in);
        output = System.out;
    }
    
    @Override
    public void write(String s) throws Exception
    {
       output.println(s);
    }
    
    @Override
    public void clearScreen() throws Exception
    {
        for(int i = 0; i < NUM_CLS_LINES; i++)
        {
            output.println();
        }
    }
    
    @Override
    public Object read() throws Exception
    {
        return input.nextLine();
    }
    
    @Override
    public boolean isTerminalValid()
    {
        return (input!= null && output != null);
    }
    
}
