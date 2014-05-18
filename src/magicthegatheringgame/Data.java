/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/** @brief Class storing essential data.
 * Most of the data are stored inside Player class, but all players are stored here.
 * Also this class stores pictures of non card origin. 
 * @author werit
 */
public class Data {
    /**  @brief Class constructor.
     * Constructor prepares space for players storage, initialise picture of card's back
     * and prepares game state button.
     * @param picturesPath Path to source game pictures.
     */
    public Data(final String picturesPath){
        rand = new Random();
        players = new Player[2];
        gameStateInd = new JButton(Game.state.toString());
        try{
            inicCardsPicture(picturesPath);
        }
        catch (Exception e){
            System.err.println("ERROR.");
            e.printStackTrace();
        }
    }
    private void inicCardsPicture(final String picturesPath) throws Exception{
        try{
            cardBack = assignProperImage(picturesPath + File.separator + "back.jpg", 110*2/3, 153*2/3);
            /*BufferedImage image = ImageIO.read(new File("D:\\Source pictures\\back.jpg"));
            Image resizedimg = image.getScaledInstance(220/2,305/2,0);//(image.getHeight() / 2, image.getWidth() / 2, 0); TODO !!!
            cardBack = new ImageIcon(resizedimg);*/
            cardBackEmpty = assignProperImage(picturesPath + File.separator + "backEmpty.png", 110*2/3, 153*2/3);
            blackMana = assignProperImage(picturesPath + File.separator + "Black.png", 40,40);
            blueMana = assignProperImage(picturesPath + File.separator + "Blue.png", 40,40);
            greenMana = assignProperImage(picturesPath + File.separator + "Green.png", 40,40);
            whiteMana = assignProperImage(picturesPath + File.separator + "White.png", 40,40);
            redMana = assignProperImage(picturesPath + File.separator + "Red.png", 40,40);
            colourlessMana = assignProperImage(picturesPath + File.separator + "Colorless.png", 40,40);
        }
        catch(IOException e){
            System.out.println("Nastala chyba :" + e.toString());
            throw new Exception();
        }
    }
    
    private ImageIcon assignProperImage(String path,int pictWidth,int pictHeight) throws Exception{
        return new ImageIcon(ImageIO.read(new File(path)).getScaledInstance(pictWidth, pictHeight, 0));
    }
            
            
    ImageIcon cardBack;
    ImageIcon cardBackEmpty;
    ImageIcon whiteMana;
    ImageIcon blackMana;
    ImageIcon greenMana;
    ImageIcon blueMana;
    ImageIcon redMana;
    ImageIcon colourlessMana;
    Player[] players;
    Random rand;
    ArrayList<Card> allCards = new ArrayList<>();
    ReadDeck readDeck;
    JButton gameStateInd;
}
