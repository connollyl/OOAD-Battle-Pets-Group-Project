package UI;

/**
 * InputReader provides methods to allow easier use of the Scanner object
 * in the context of the game.
 * @Author Eric Weber
 * @Version 0.5
 */
import java.io.*;
import java.util.*;
/**
 * Class that reads input from the console. Coincides with the
 * Buffer and ConsoleUI class
 * @author Eric Weber
 *
 */
class InputReader
   {
        private Scanner scanner;
      
        private static String PROPERTY_FILE_PATH = "\\build\\classes\\UI\\InputReader.properties";
        private static Properties outputStrings;
      
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
      
      private void getPropertiesFile()
      {
          try
          {
              InputStream input = new FileInputStream(System.getProperty("user.dir") + PROPERTY_FILE_PATH);
              outputStrings = new Properties();
              outputStrings.load(input);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
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
               scanner.next();
               throw new java.util.InputMismatchException(outputStrings.getProperty("notIntExc"));
            }            
         }
         else
         {
            throw new java.io.IOException(outputStrings.getProperty("noInStreamExc"));
         }
      }
      
      
      /**
       * Returns the next Long from the input stream.
       * @throws java.util.InputMismatchException when next object in the
       * input stream is not a long.
       * @throws java.io.IOException when no inputStream is set.
       */
      protected long getLong() throws java.io.IOException,java.util.InputMismatchException{
    	  if(scanner != null)
          {
             if(scanner.hasNextLong())
             {
                return scanner.nextLong();
             }
             else
             {
                throw new java.util.InputMismatchException(outputStrings.getProperty("nxtLineNotValid"));
             }            
          }
          else
          {
             throw new java.io.IOException(outputStrings.getProperty("noInStreamExc"));
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
               throw new java.util.InputMismatchException(outputStrings.getProperty("nxtLineNotValid"));
            }            
         }
         else
         {
            throw new java.io.IOException(outputStrings.getProperty("noInStreamExc"));
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
               throw new java.util.InputMismatchException(outputStrings.getProperty("nxtLineNotValid"));
            }            
         }
         else
         {
            throw new java.io.IOException(outputStrings.getProperty("noInStreamExc"));
         }
      }
   }