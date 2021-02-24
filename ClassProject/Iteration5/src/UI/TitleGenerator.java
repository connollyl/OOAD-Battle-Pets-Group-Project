package UI;

import java.util.Random;
/**
 *
 * @author weberer
 */
public class TitleGenerator extends genericUI
{
    
    public TitleGenerator()
    {
        /**
         * Name of the property file the titles are stored in
         */
        PROPERTY_FILE_NAME = "BattlePetsTitles";  
        loadTitles();
    }
        
    /**
     * Loads in titles from the property file
     */
    private void loadTitles()
    {
        /**
         * Properties object containing the titles
         */
        constStrings = getConstStrings(PROPERTY_FILE_NAME);
    }
    
    /**
     * Generates an random integer between lower and upper bounds inclusive
     * @param lower lower bound
     * @param upper upper bound
     * @return random integer
     */
    private int generateRandomNumber(int lower, int upper)
    {
        Random rand = new Random();
        int randomInt = rand.nextInt((upper - lower));
        
        return randomInt;
    }
    
    /**
     * Returns a string representation of a random title stored in the 
     * file BattlePetsTitles.properties
     */
    public String getRandomTitle()
    {
        int titleNumber = generateRandomNumber(0, Integer.parseInt(prop("total.titles")));
        StringBuilder title = new StringBuilder();
        int numLines = Integer.parseInt(prop("title."+titleNumber+".lines"));
        
        for(int i = 0; i < numLines; i++)
        {
            title.append(prop("title."+titleNumber+"."+i));
        }
        return title.toString();       
    }
    
    /**
      Static method that procides the same functionality as getRandomTitle()
    @return  String representation of the title
    */
    public static String getTitle()
    {
       TitleGenerator t = new TitleGenerator();
       return t.getRandomTitle();
    }
}
