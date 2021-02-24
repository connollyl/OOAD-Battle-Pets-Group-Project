package UI;

import java.util.Random;
/**
 *
 * @author weberer
 */
public class TitleGenerator extends genericUI
{
    private boolean titleChosen;
    private StringBuilder titleString;
    private int numTitles;
    private int choseTitleNum;
    private int numChosenTitleLines;
    
    public TitleGenerator()
    {
        /**
         * Name of the property file the titles are stored in
         */
        PROPERTY_FILE_NAME = "BattlePetsTitles";        
        
        titleString = new StringBuilder();
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
        int randomInt = rand.nextInt((upper - lower) + 1);
        
        return randomInt;
    }
    
    /**
     * Returns a string representation of a random title stored in the 
     * file BattlePetsTitles.properties
     */
    public String getRandomTitle()
    {
        int titleNumber = generateRandomNumber(0, Integer.parseInt(constStrings.getProperty("total.titles")));
        StringBuilder title = new StringBuilder();
        int numLines = Integer.parseInt(constStrings.getProperty("title."+titleNumber+".lines"));
        
        for(int i = 0; i < numLines; i++)
        {
            title.append(constStrings.getProperty("title."+titleNumber+"."+i));
        }
        return title.toString();       
    }
    
    public static void main(String[] args)
    {
        TitleGenerator t = new TitleGenerator();
        System.out.println(t.getRandomTitle());
    }
}
