package UI.Terminals;

import UI.Interfaces.Terminal;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class StreamTerminal implements Terminal
{
    private Scanner input;
    private PrintStream output;
    
    public StreamTerminal(InputStream is, PrintStream os)
    {
        input = new Scanner(is);
        output = os;
    }
    
    @Override
    public void write(String s) throws Exception
    {
        output.println(s);
    }
    
    @Override
    public void clearScreen() throws Exception
    {
        String cls = "--Cleared Screen--";
        
        output.println(cls);
    }
    
    @Override
    public Object read() throws Exception
    {
        Object o = input.next();
        return o;
    }
    
    @Override
    public boolean isTerminalValid()
    {
        return(input != null && output != null);
    }
    
    
}
