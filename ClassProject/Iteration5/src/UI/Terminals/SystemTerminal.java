package UI.Terminals;

import UI.Interfaces.Terminal;

public class SystemTerminal implements Terminal
{

    private java.io.Console c = null;

    public SystemTerminal()
    {
        c = System.console();
    }

    @Override
    public void write(String s) throws Exception
    {
        c.printf(s);
        c.printf("\n");
    }

    @Override
    public void clearScreen() throws Exception
    {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    @Override
    public Object read() throws Exception
    {
        return c.readLine();
    }

    @Override
    public boolean isTerminalValid()
    {
        return !(c == null);
    }

    //Testbed Main ----------------------------------------------------------
    /* public static void main(String[] args)
     * {
     * try
     * {
     *
     * }
     * catch(Exception e)
     * {
     *
     * }
     * } */
}
