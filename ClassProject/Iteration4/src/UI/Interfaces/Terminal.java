package UI.Interfaces;

/**
 * Specifies the Terminal interface for display objects
 * @author weberer
 */
public interface Terminal
{

    /**
     * Writes a string
     * 
     * @param s String to write
     * @throws Exception 
     */
    public void write(String s) throws Exception;

    /**
     * Clears the screen
     * @throws Exception when unable to clear screen 
     */
    public void clearScreen() throws Exception;

    /**
     * Reads an Object
     * @return object read in
     * @throws Exception unable to read object properly
     */
    public Object read() throws Exception;

    public boolean isTerminalValid();

}
