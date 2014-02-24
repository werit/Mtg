/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.sound.midi.SysexMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


/**
 *
 * @author Werit
 */
public class Arbiter extends MouseAdapter{
    private final JFrame pane;
    private GridBagConstraints constr;
    private final Data data;
    private int order;
    Arbiter(JFrame pane){
        this.pane = pane;
        data = new Data();
    }
    public void arbitGame(){
        initializeGamePLay();
        createnviroment();
        play();
        //data.addComponentsToPane(pane.getContentPane());
    }
    private void play(){
        
        for (int i = 0; i < 7; ++i) {
            drawACard();
        }
    }
    private void drawACard(){
        Card card;
        card = data.players[Game.currentPlayer].draw();
            if(card != null){
                Game.GUIComposition.get(Game.composition.HAND_CP).add(card);
            }
            else{
                JPanel jp = Game.GUIComposition.get(Game.composition.LIBRARY_CP);
                jp.removeAll();
                jp.add(new JLabel(data.cardBackEmpty));
                jp.revalidate();
                OUtput.lostTheGame();
            }
    }
    private void createnviroment(){
        /* TODO 
            zistenie pouzitelnej velkosti a funkciu na prepocet velkosti jednotlivych komponent
        */
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        constr = new GridBagConstraints();
        
        
        constr.fill = GridBagConstraints.NONE;
        constr.gridx = 0;
        constr.gridy = 0;

        constr.gridwidth = 1; // hovorim, ze bude zaberat jedno policko. Potom nesmiem zabudnut nastavit scrollpanel na 8, aby sa spravil spravny pomer.
        constr.ipady = 10;
        constr.weightx = 0; // do not give any free space event if there is some.
        constr.weighty = 0;
        
        constr.gridheight = 3;
        
        // Library_op
        createPanelAndComponents(0, 0, new JLabel[]{new JLabel(data.cardBack)},Game.composition.LIBRABRY_OP);
        // Library_currPl
        createPanelAndComponents(0, 11, new JLabel[]{new JLabel(data.cardBack)},Game.composition.LIBRARY_CP);
      
        constr.gridheight = 2;
        //Grave_op
        createPanelAndComponents(0, 3, new JLabel[]{new JLabel(data.cardBackEmpty)},Game.composition.GRAVE_OP);
        
        //RFG_op
        createPanelAndComponents(0, 5, new JLabel[]{new JLabel(data.cardBackEmpty)},Game.composition.RFG_OP);
        
        //RFG_currPl
        createPanelAndComponents(0, 7, new JLabel[]{new JLabel(data.cardBackEmpty)},Game.composition.RFG_OP);
        
         //Grave_currPl
        createPanelAndComponents(0, 9, new JLabel[]{new JLabel(data.cardBackEmpty)},Game.composition.GRAVE_CP);
 
        
        constr.gridheight = 1;
        constr.gridwidth = 1; // hovorim, ze bude zaberat jedno policko. Potom nesmiem zabudnut nastavit scrollpanel na 8, aby sa spravil spravny pomer.
        constr.ipady = 10;
        constr.weightx = 0; // do not give any free space event if there is some.
        constr.weighty = 0;     
        

        
        JLabel[] labels ={
            new JLabel("Lifes"),new JLabel(data.blackMana),new JLabel(data.whiteMana),
            new JLabel(data.greenMana),new JLabel(data.blueMana),
            new JLabel(data.redMana),new JLabel(data.colourlessMana),
            new JLabel(data.colourlessMana),new JLabel(data.redMana),
            new JLabel(data.blueMana),new JLabel(data.greenMana),
            new JLabel(data.whiteMana),new JLabel(data.blackMana),new JLabel("Lifes")
        };
        Player currPl = data.players[Game.currentPlayer];
        Player currOp = data.players[(Game.currentPlayer+1)%2]; 
        
        // prepare values to be displayed by default
        int[] values = {currOp.getLifes(),currOp.getSwampCount(),currOp.getPlainCount(),currOp.getForestCount(),
            currOp.getIslandCount(),currOp.getMountainCount(),currPl.getColorlessCount(),currPl.getColorlessCount(),
            currPl.getIslandCount(),currPl.getMountainCount(),currPl.getForestCount(),currPl.getPlainCount(),currPl.getSwampCount(),
            currPl.getLifes()};
        Game.composition[] compose ={Game.composition.LIFES_OP,Game.composition.SWAMP_OP,Game.composition.PLAIN_OP,Game.composition.FOREST_OP,
            Game.composition.ISLAND_OP,Game.composition.MOUNTAIN_OP,Game.composition.COLORLESS_OP,Game.composition.COLORLESS_CP,
            Game.composition.MOUNTAIN_CP,Game.composition.ISLAND_CP,Game.composition.FOREST_CP,Game.composition.PLAIN_CP,Game.composition.SWAMP_CP,
            Game.composition.LIFES_CP};
        
        for(int i = 0; i< labels.length;++i){
            createPanelAndComponents(1, i, new JLabel[]{labels[i]}, null);
            createPanelAndComponents(2, i, new JLabel[]{new JLabel(new Integer(values[i]).toString())}, compose[i]);
        }
        // adding button for next step
        data.gameStateInd.addMouseListener(Game.mousLis);//(new NextStepButtonLis());
        constr.gridx = 12;
        constr.gridy = 11;
        pane.add(data.gameStateInd,constr);
        
        constr.gridwidth = 8; // hovorim, ze bude zaberat dve policka. Potom nesmiem zabudnut nastavit scrollpanel na 8, aby sa spravil spravny pomer.
        constr.ipady = 10;
        constr.weightx = 0; // hovorim aby nedostal ziadne miesto naviac, ak by ah bolo
        constr.weighty = 0;
        
        // necessary because of three jlabels of Lifes and manas in same height
        constr.gridheight = 3;
        
        // Hand_op
        createScrollAndComponents(Game.shift, 0, new JLabel[]{new JLabel(data.cardBack),new JLabel("Card2")},Game.composition.HAND_OP);
         // Hand_currPl
        createScrollAndComponents(Game.shift, 11,null,Game.composition.HAND_CP);
        
        constr.gridheight = 2;
        
        // Manas_op
        createScrollAndComponents(Game.shift, 3, new JLabel[]{new JLabel("Mana1"),new JLabel("Mana2")},Game.composition.LANDS_OP);
        
        // Creatures_op
        createScrollAndComponents(Game.shift, 5, new JLabel[]{new JLabel("Creature1"),new JLabel("Creature2")},Game.composition.CREATURES_OP);
        
         // Creatures_currPl
        createScrollAndComponents(Game.shift, 7, new JLabel[]{new JLabel("Creature1.1"),new JLabel("Creature2.1")},Game.composition.CREATURES_CP);
        
        // Manas_currPl
        createScrollAndComponents(Game.shift, 9, new JLabel[]{new JLabel("Mana1"),new JLabel("Mana2")},Game.composition.LANDS_CP);
        
       

        addToPanel(Game.GUIComposition.get(Game.composition.HAND_OP),new JLabel[]{ new JLabel("testing")});

        
    }
    /**  @brief method creating and inserting JPanels on main JFrame
     * 
     * @param pos_x Position on x axis in GridBagLayout
     * @param pos_y Position on y axis in GridBagLayout
     * @param labels Labels to be inserted into new panel created by this method
     * @param pos_name Key to HashMap <code> Game.GUIComposition </code> 
     */
    private void createPanelAndComponents(int pos_x,int pos_y,JLabel[] labels,Game.composition pos_name){
        JPanel jp = new JPanel();
        // jp.processMouseEvent TODO !!!
        addToPanel(jp,labels);
        constr.gridx = pos_x;
        constr.gridy = pos_y;
        if (pos_name != null)
            Game.GUIComposition.put(pos_name, jp);
        pane.add(jp,constr);
    }
    
    /**  @brief method creating and inserting JScrollPanes on main JFrame
     * 
     * @param pos_x Position on x axis in GridBagLayout
     * @param pos_y Position on y axis in GridBagLayout
     * @param labels Labels to be inserted into new panel created by this method
     * @param pos_name Key to HashMap <code> Game.GUIComposition </code> 
     */
    private void createScrollAndComponents(int pos_x,int pos_y,JLabel[] labels,Game.composition pos_name){
        JScrollPane compPanel = new JScrollPane();
        compPanel.setPreferredSize(new Dimension(800, 150));
        JPanel inside = new JPanel();
        compPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        compPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addToPanel(inside,labels);
        compPanel.getViewport().add(inside);
        constr.gridx = pos_x;
        constr.gridy = pos_y;
        Game.GUIComposition.put(pos_name, inside);
        pane.add(compPanel,constr);
    }
    
    /** @brief Method used in filling JPanels of GUI. 
     * Use this method when adding cards to your GUI. 
     * Method will fill JPanel, passed as argument, with Cards from second argument.
     * @param jpToFill reference to JPanel which will be filled with cards.
     * @param labels Parameter setting length of layout of JPanel and adding them to that panel.
     * @return Filled JPane
     */
    private void addToPanel(JPanel jpToFill,JLabel[] labels){      
        assert (jpToFill != null);
        if(labels != null)
            for (int i = 0; i < labels.length; ++i) {
                if(labels[i] != null)
                    jpToFill.add(labels[i]);
            }
        
    }
    
    /** @brief Initialising procedure of the GAME (not actual MATCH).
     *  Procedure creates players with their decks, decides order of players.
     * 
     */
     private void initializeGamePLay(){
        order = data.rand.nextInt(2);
        // set order of players
        data.players[order] = new Player();
        data.players[((order+1)%2)] = new Player();
        ReadDeck.readDeckMain("C:\\Users\\msi\\Documents\\NetBeansProjects\\MagicTheGatheringGame\\src\\magicthegatheringgame\\deck1.xml",data.players[0]);
        ReadDeck.readDeckMain("C:\\Users\\msi\\Documents\\NetBeansProjects\\MagicTheGatheringGame\\src\\magicthegatheringgame\\deck1.xml",data.players[1]);
        
       // addToPanel(Game.GUIComposition.get(Game.composition.HAND_OP),new JLabel[]{ new JLabel(data.cardBack),new JLabel(data.cardBack)});
        
        // set beginning player
        order = 0;
        Game.currentPlayer = (byte)order;
        
     }
     
     /** @brief Method handling each round.
      * 
      */
     public void round(){
         Battleground.refresh();
         
         // resst of the round
         order = (order+1)%2;
         Game.currentPlayer = (byte)order;
     }
     
    /** @brief Method representing and process untap phase of game.
     * 
     */
    private void untapPhase(){
        changeGameState(Game.gameState.UPKEEP);
    }
    private void upKeep(){
        changeGameState(Game.gameState.DRAW);
    }
    /** @brief Method processing draw phase of the game. 
     *  Method changes Game.state to #MAIN_PHASE and makes player draw a card.
     */
    private void drawPhase(){
        drawACard();
        changeGameState(Game.gameState.MAIN_PHASE);
        
    }
    private void mainPahse(){
        changeGameState(Game.gameState.ATTACK);
    }
    private void changeGameState(Game.gameState newGameState){
        Game.state = newGameState;
        data.gameStateInd.setText(Game.state.toString());
        
    }
    /** @brief Method representing and process attack phase of game.
     * 
     */
    private void attackPhase(){
        
    }
    
    private void readDeck(){
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == data.gameStateInd){
            switch(Game.state){
                case UNTAP:
                    untapPhase();
                    break;
                case UPKEEP:
                    upKeep();
                    break;
                case DRAW:
                    drawPhase();
                    break;
                case MAIN_PHASE:
                    break;
                case ATTACK:
                    break;
                case DEFENSE:
                    mainPahse();
                    break;
                case MAIN_PHASE2:
                    break;
                case EOT:
                    break;
            }
            return;
        }
        Card card = (Card)e.getSource();
        switch(card.cardLoc){
            case IN_HAND:
                if( Game.state == Game.gameState.MAIN_PHASE || Game.state == Game.gameState.MAIN_PHASE2 || card.type == Game.cardType.INSTANT){
                    cast(card);
                }
                break;
            case IN_PLAY:
                if(!card.isTapped && card.isTapAble)
                    card.onTap(Game.state);
                break;
        }
        this.pane.revalidate();
        this.pane.repaint();
    }
    private void cast(Card c){
        
        switch(c.type){
            case LAND:
                manaConditions(c);
                break;
            case CREATURE:
                Game.GUIComposition.get(Game.composition.CREATURES_CP).add(c);
                c.cardCast();
                break;
            case ARTIFACT:
                break;
            case ENCHANCEMENT:
                break;
            case INSTANT:
                break;
            case SORCERY:
                break;
        }
        
    }
    private void manaConditions(Card c){
        if(c.controller.manaLimit > c.controller.manaPlayed){
            Game.GUIComposition.get(Game.composition.HAND_CP).remove(c);
            Game.GUIComposition.get(Game.composition.LANDS_CP).add(c);
            ++c.controller.manaPlayed;
            c.cardCast();
        }
    }
}
