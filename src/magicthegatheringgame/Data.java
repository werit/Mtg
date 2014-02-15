/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
        try{
            inicCardsPicture();
        }
        catch (Exception e){
            System.err.println("ERROR.");
            e.printStackTrace();
        }
    }
    
    public void addComponentsToPane(Container pane) {
    if (RIGHT_TO_LEFT) {
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    }
    JScrollPane cardPreview;
    pane.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    if (shouldFill) {
    //natural height, maximum width
    c.fill = GridBagConstraints.BOTH;
    }
    
    enemyPreviewCard = new JPanel();
    
    enemyPreviewCard.add(new JLabel("enemy first card"));
   
    if (shouldWeightX) {
    c.weightx = 0;
    }
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    //c.ipady = 200;
    //c.ipadx = 150;
    pane.add(enemyPreviewCard, c);
    
    cardPreview = new JScrollPane();
    
    cardPreview.add(new JPanel().add(new JLabel("enemies's cards")));
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.gridx = 1;
    c.gridy = 0;
    c.gridwidth = 3; 
    pane.add(cardPreview, c);
 
    myPreviewCard = new JPanel(new GridLayout(1,2));
    //myPreviewCard.add(new JLabel("player first card"));
    myPreviewCard.add(allCards.get(4).fileSource);
    c.weightx = 0.1;
    c.gridwidth = 1; 
    c.fill = GridBagConstraints.HORIZONTAL;
    //c.weightx = 0.2;
    
   // c.gridwidth = 1; 
    //c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 1;
    pane.add(myPreviewCard, c);
    
    cardPreview = new JScrollPane();
    cardPreview.getViewport().add(new JPanel().add(new JLabel("player's cards")));
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.gridx = 1;
    c.gridy = 1;
    c.gridwidth = 3; 
    pane.add(cardPreview, c);
    
    myPreviewMana = new JPanel();
    myPreviewMana.add(new JLabel("player first mana"));
    c.weightx = 0.2;
    c.gridwidth = 1; 
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 2;
    pane.add(myPreviewMana, c);
    
    cardPreview = new JScrollPane();
    cardPreview.getViewport().add(new JPanel().add(new JLabel("player's manas")));
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 3; 
    pane.add(cardPreview, c);
    
    JPanel playerInfo = new JPanel(new GridLayout(1,2));
    
    
    myHp.setFont(new Font("Serif",Font.PLAIN,15));
    enemyHp.setFont(new Font("Serif",Font.PLAIN,15));
    
    manaPanel.add(myHp);
    manaPanel.add(enemyHp);
    manaPanel.add(colorlessMana);
    manaPanel.add(plainMana);
    manaPanel.add(mountainMana);
    manaPanel.add(islandMana);
    manaPanel.add(swampMana);
    manaPanel.add(forestMana);
   
    playerInfo.add(manaPanel);
    playerInfo.add(allCards.get(0).fileSource);
    playerInfo.setPreferredSize(new Dimension(200,300));
    c.weightx = 0.1;
    c.gridwidth = 1; 
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 3;
    pane.add(playerInfo, c);
    
    cardPreview = new JScrollPane();
    JPanel handContainer = new JPanel();
    
    handContainer.add(allCards.get(1).fileSource);
    
    handContainer.add(allCards.get(2).fileSource);
    handContainer.add(allCards.get(3).fileSource);
    //handContainer.add(allCards.get(0).fileSorce);
    handContainer.setPreferredSize(new Dimension(100,100));
    cardPreview.getViewport().add(handContainer);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 3; 
    pane.add(cardPreview, c);
    }
    void inicCardsPicture() throws Exception{
        try{
            BufferedImage image = ImageIO.read(new File("D:\\Source pictures\\back.jpg"));
            Image resizedimg = image.getScaledInstance(100, 50, 0);
            backPict = new JLabel(new ImageIcon(resizedimg));
        }
        catch(IOException e){
            System.out.println("Nastala chyba :" + e.toString());
            throw new Exception();
        }
    }
    JLabel backPict;
    Player[] players;
    Random rand;
    Cast cast;
    ArrayList<Card> allCards = new ArrayList<>();
    Turn turn;
    Fight fight;
    EndOfTurn endOfTurn;
    ReadDeck readDeck;
   
}
