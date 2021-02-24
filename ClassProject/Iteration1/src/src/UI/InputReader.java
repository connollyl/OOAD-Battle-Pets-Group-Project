package UI;

/**
 * InputReader provides methods to allow easier use of the Scanner object
 * in the context of the game.
 * @Author Eric Weber
 * @Version 0.5
 */
import java.io.InputStream;
import java.util.Scanner;
/**
 * Class that reads input from the console. Coincides with the
 * Buffer and ConsoleUI class
 * @author Eric Weber
 *
 */
class InputReader
   {
      private Scanner scanner;
      
      /**
      * Default constructor for InputReader class. 
      */
      InputReader()
      {
         scanner = null;
      }
      
      /**
      * Creates an input reader using the specified <code>InputStream</code>.
      */
      InputReader(InputStream is)
      {
         scanner = new Scanner(is);
      }
      
      /**
      * Updates the <code>InputStream</code> used by the InputReader class.
      * @param is - InputStream to use.
      */
      private void setInputStream(InputStream is)
      {
         scanner = new Scanner(is);
      }
      
      /**
      * Returns the next integer from the input stream.
      * @throws java.util.InputMismatchException when next object in the
      * input stream is not an integer.
      * @throws java.io.IOException when no inputStream is set.
      */
      protected int getInt() throws java.io.IOException, java.util.InputMismatchException
      {
         if(scanner != null)
         {
            if(scanner.hasNextInt())
            {
               return scanner.nextInt();
            }
            else
            {
               throw new java.util.InputMismatchException("Next line is not an integer");
            }            
         }
         else
         {
            throw new java.io.IOException("No input Stream");
         }
      }
      
      /**
      * Returns the next string from the input stream.
      * @throws java.util.InputMismatchException when next object in the
      * input stream is not valid.
      * Andrew spent hours debugging to find a problem in this function....
      * @throws java.io.IOException when no inputStream is set.
      */
      protected String getString()throws java.io.IOException, java.util.InputMismatchException
      {   
         if(scanner != null)
         {
            if(scanner.hasNext())
            {
            	String str = scanner.nextLine();
            	if(str.isEmpty()) {
            		return scanner.nextLine();
            	}else {
            		return str;
            	}
            }
            else
            {
               throw new java.util.InputMismatchException("Next line is not valid");
            }            
         }
         else
         {
            throw new java.io.IOException("No input Stream");
         }
      }
      
      /**
      * Returns the next Integer or String from the Input Stream as an Object
      * to be cast later.
      * @throws java.util.InputMismatchException when next object in the
      * input stream is not valid.
      * @throws java.io.IOException when no inputStream is set.
      */
      protected Object getLine() throws java.io.IOException, java.util.InputMismatchException
      {
         if(scanner != null)
         {
            if(scanner.hasNext())
            {
               if(scanner.hasNextInt())
                  return getInt();
               
               return getString();
            }
            else
            {
               throw new java.util.InputMismatchException("Next line is not valid");
            }            
         }
         else
         {
            throw new java.io.IOException("No input Stream");
         }
      }
   }