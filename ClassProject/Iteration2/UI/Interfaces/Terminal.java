package UI.Interfaces;


public interface Terminal 
{
    public void write(String s) throws Exception;
    
    
    public void clearScreen() throws Exception;
    
    
    public Object read() throws Exception;
    
    public boolean isTerminalValid();
    
}