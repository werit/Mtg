/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 *
 * @author werit
 */
public class ReadDeck extends DefaultHandler {

    public static void readDeckMain(String inputFile,Player owner) {
        // TODO code application logic here
        try {
            // assign input file
            INPUT_FILE = inputFile;
            // Create parser instance
            XMLReader parser = XMLReaderFactory.createXMLReader();

            // Create input stream from source XML document
            InputSource source = new InputSource(INPUT_FILE);

            // Set our custom content handler for handling SAX events (it must implements ContentHandler interface)
            parser.setContentHandler(new ReadDeck(owner));

            // Process input data
            parser.parse(source);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
    public ReadDeck(Player owner){
        readingPath = false;
        tagValue = new HashMap<>();
        this.owner = owner;
        //this.owner.deck = owner.deck;
        proper = new ArrayList<>();
        allCardProp = new HashMap<>();
    }
     @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        inpTag = Game.inputTagTranslator.get(localName);
        
        
        if (inpTag != null ){
            switch (inpTag){
                case CARD:
                    if (atts.getLength() == 2)
                    {
                        tagValue.clear();
                        currCardType = null;
                        allCardProp.clear();
                        cardName = atts.getValue(0);
                        currCardType = Game.cardTypeTranslator.get(atts.getValue(1));
                        if (currCardType == null)
                        {}  // doplnit prestup na error
                    }
                    else 
                    {} // error
                    inpTag = null;
                    break;
                case BOOST:
                    boostUsabil = null;
                    proper.clear();
                    if (atts.getLength() == 1)
                    {
                        boostUsabil = Game.inpBoostUsAblTransl.get(atts.getValue(0));
                        if (boostUsabil == null)
                        {}  // doplnit prestup na error
                    }
                    else 
                    {} // error
                    inpTag = null;
                    break;
                case PICTURE_PATH:
                    readingPath = true;
                    inpTag = null;
                    break;
            }         
        }
        else
        {
            Game.cardProperties cp = Game.propertyTranslator.get(localName);
            if(cp != null)
                proper.add(cp);    
            else{
                // go to error
            }
        }
    }
     @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(Game.inputTagTranslator.get(localName) != null && Game.inputTagTranslator.get(localName).equals(Game.inputTags.CARD)){
            assert (currCardType != null);
            switch(currCardType){
                case CREATURE:
                    createCreatures(owner,tagValue.get(Game.inputTags.POWER),tagValue.get(Game.inputTags.TOUGHNESS));
                    break;
            }
        }
        else 
            if (Game.inputTagTranslator.get(localName) != null && Game.inputTagTranslator.get(localName).equals(Game.inputTags.BOOST))
            {
                allCardProp.put(boostUsabil, new ArrayList<>(proper));
            }
    }
     @Override
    public void characters(char[] chars, int start, int length) throws SAXException {       
        if (readingPath)
            picturePath = new String(chars,start,length);
        readingPath = false;
        if (inpTag != null){
            setCount(new String(chars,start,length));
            inpTag = null;
        }
        
    }
     @Override
    public void endDocument() throws SAXException {
    }
    private void createCreatures(Player owner,byte power ,byte toughness){
            JLabel pict = inicCardsPicture(picturePath);

            for (byte i = 0; i < tagValue.get(Game.inputTags.COUNT);++i){
                Card c = new Creature(cardName,owner,pict,tagValue.get(Game.inputTags.WHITE),tagValue.get(Game.inputTags.BLACK),
                                tagValue.get(Game.inputTags.GREEN),tagValue.get(Game.inputTags.BLUE),tagValue.get(Game.inputTags.RED),
                                tagValue.get(Game.inputTags.COLOURLESS),power,toughness);
                for (int j = 0; j < proper.size(); ++j) {
                    c.abilUse = new HashMap<>(allCardProp);
                }
                
                this.owner.deck.add(c);          
        }
    }
    
    private JLabel inicCardsPicture(String path){
        try{
            BufferedImage image = ImageIO.read(new File(path));
            Image resizedimg = image.getScaledInstance(50, 50, 0);
            return new JLabel(new ImageIcon(resizedimg));    
        }
        catch(IOException e){
            System.out.println("Nastala chyba :" + e.toString());
            e.printStackTrace();
            Game.exceptTrack.add(e);
            return null;
        }
    }
    
    /** @brief Method setting value of currently read tag.
     *  Value of tag is set to collection containing all values <b>byte</b> of all tags of currently read card.
     *  inpTag stores information about what tag are we currently reading.
     *  In case of exception during converting, exception is added to Game#exceptTrack
     * @param number Input number value is passed as string and then converted to byte.
     */
    private void setCount(String number){
        try{
                tagValue.put(inpTag, Byte.parseByte(number));
            }
            catch(NumberFormatException e){
                Game.exceptTrack.add(e);
            }
    }
    
    private static String INPUT_FILE;
    private Player owner;                   /**< Local storage of reference to owner of deck. */
    private String picturePath;             /**< String path to real location of picture representing actualy read card.*/
    private boolean readingPath;            /**< Determining if picture path of card is beign read.*/
    private Game.cardType currCardType;     /**< Variable storing information about current card type.*/
    private String cardName;                /**< String representing name of card actually read.*/
    private Game.boostUsabil boostUsabil;   /**< Variable representating when can be properties of a card be used. This information will be later passed to Card::abilUse*/
    /**
     * Container keeping track of byte values of designated tags.
     * 
     * A few of used tags has byte value and this container contains them all.
     */
    private Map<Game.inputTags,Byte> tagValue;
    private Game.inputTags inpTag; /**< Variable representing what tag is currrently read.*/
    private Map<Game.boostUsabil,ArrayList<Game.cardProperties>> allCardProp; /**<  Map containing all properties of all kinds of one card*/
    private ArrayList<Game.cardProperties> proper; /**<  Collection containing all properties of one type of a card.It is in enum form.*/
}

