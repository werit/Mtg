/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author werit
 */
public class Data {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    JLabel myHp = new JLabel("Your lifes : 20");
    JLabel enemyHp = new JLabel("Enymy lifes :20");
    JLabel colorlessMana = new JLabel("colorless mana");
    JLabel plainMana = new JLabel("plain mana");
    JLabel mountainMana = new JLabel("mountain mana");
    JLabel islandMana = new JLabel("island mana");
    JLabel swampMana = new JLabel("swamp mana");
    JLabel forestMana = new JLabel("forest mana");
    JPanel manaPanel = new JPanel(new GridLayout(12,1));
    JPanel enemyPreviewCard;
    JPanel myPreviewCard;
    JPanel myPreviewMana;
    
    Data(){
        rand = new Random();
        players = new Player[2];
        gameStateInd = new JButton(Game.state.toString());
        try{
            inicCardsPicture();
        }
        catch (Exception e){
            System.err.println("ERROR.");
            e.printStackTrace();
        }
    }
    void inicCardsPicture() throws Exception{
        try{
            cardBack = assignProperImage("D:\\Source pictures\\back.jpg", 110*2/3, 153*2/3);
            /*BufferedImage image = ImageIO.read(new File("D:\\Source pictures\\back.jpg"));
            Image resizedimg = image.getScaledInstance(220/2,305/2,0);//(image.getHeight() / 2, image.getWidth() / 2, 0); TODO !!!
            cardBack = new ImageIcon(resizedimg);*/
            cardBackEmpty = assignProperImage("D:\\Source pictures\\backEmpty.png", 110*2/3, 153*2/3);
            blackMana = assignProperImage("D:\\Source pictures\\Black.png", 40,40);
            blueMana = assignProperImage("D:\\Source pictures\\Blue.png", 40,40);
            greenMana = assignProperImage("D:\\Source pictures\\Green.png", 40,40);
            whiteMana = assignProperImage("D:\\Source pictures\\White.png", 40,40);
            redMana = assignProperImage("D:\\Source pictures\\Red.png", 40,40);
            colourlessMana = assignProperImage("D:\\Source pictures\\Colorless.png", 40,40);
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
