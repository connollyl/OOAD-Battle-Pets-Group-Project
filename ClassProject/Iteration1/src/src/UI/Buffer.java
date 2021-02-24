package UI;
/**
 * Buffer provides methods to allow easier use of PrintStream objects
 * in the context of the game.
 * @Author Eric Weber
 * @Version 0.5
 */
import java.io.PrintStream;
import java.util.ArrayList;

public class Buffer
   {
      /**
      * Holds text until it is written to the output stream <code>ps</code>
      */
      private ArrayList<String> screenBuffer;
      /**
      * PrintStream to write to
      */
      private PrintStream ps = null;
      
      /**
      * Default constructor for Buffer class. 
      */
      Buffer()
      {
         screenBuffer = new ArrayList<>();
      }
      
      /**
      * Constructor for Buffer class
      * @param stream - <code>printStream</code> to use
      */
      Buffer(PrintStream stream)
      {
         ps = stream;
         screenBuffer = new ArrayList<>();
      }
      
      /**
      * Empties the screen buffer.
      */
      private void empty()
      {
         screenBuffer.clear();
      }
      
      /**
      * Updates the <code>PrintStream</code> used by the Buffer class
      * @param stream - <code>PrintStream</code> to use
      */
      private void setPrintStream(PrintStream stream)
      {
         ps = stream;
      }
      
      /**
      * Clears the screen by displaying 50 blank lines to the print stream.
      * @throws <code>java.io.exception</code> when no PrintStream exists
      */
      protected void clearScreen() throws java.io.IOException
      {
         try
         {
         for(int i = 0; i < 50; i++)
            screenBuffer.add("");
         
         write();
         }
         catch(java.io.IOException e)
         {
        	System.out.println(e.getStackTrace());
            throw e;
         }
         finally
         {
            empty();
         }
      }     
      
      /**
      * Writes the buffer to the default PrintStream. 
       * @see <code>write(PrintStream stream)</code>
      */
      protected void write() throws java.io.IOException
      {
         write(ps);
      }
      
      /**
      * Writes the buffer to the specified PrintStream.
      * @param stream - print stream to write to.
      */
      private void write(PrintStream stream) throws java.io.IOException
      {
         if(ps != null)
         {         
            for(String line : screenBuffer)
            {
               stream.println(line);      
            }
            empty();
         }
         else
         {
            throw new java.io.IOException(" No Stream to write to");
         }
      }
      
      /**
      * Adds a new line to the screen buffer.
      * @param line - text to add to the buffer as a new line.
      */
      protected void add(String line)
      {
         screenBuffer.add(line);
      }
      
      /**
      * Adds a blank line to the screen buffer.
      */
      protected void blankLine()
      {
         screenBuffer.add("");
      }
      
      /**
      * Removes the last line from the screen buffer. 
      */
      protected void removeLastLine()
      {
         if(screenBuffer.size() > 0)
            screenBuffer.remove(screenBuffer.size() - 1);
      }
   }