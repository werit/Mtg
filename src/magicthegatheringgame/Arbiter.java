/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 *
 * @author Werit
 */
public class Arbiter {
    private JFrame pane;
    private GridBagConstraints constr;
    private Data data;
    private int order;
    Arbiter(JFrame pane){
        this.pane = pane;
    }
    public void arbitGame(){
        data = new Data();
        initializeGamePLay();
        //data.addComponentsToPane(pane.getContentPane());
    }
    
    public void createnviroment(){
        /* TODO 
            zistenie pouzitelnej velkosti a funkciu na prepocet velkosti jednotlivych komponent
        */
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        constr = new GridBagConstraints();
        
        
        constr.fill = GridBagConstraints.NONE;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.gridheight = 1;
        constr.gridwidth = 2; // hovorim, ze bude zaberat dve policka. Potom nesmiem zabudnut nastavit scrollpanel na 8, aby sa spravil spravny pomer.
        constr.ipady = 10;
        constr.weightx = 0; // hovorim aby nedostal ziadne miesto naviac, ak by aj bolo
        constr.weighty = 0;
        
        // Library_op
        createPanelAndComponents(0, 0, new JLabel[]{new JLabel("Library"),new JLabel("Lifes")},Game.composition.LIBRABRY_OP);
        
        //Grave_op
        createPanelAndComponents(0, 1, new JLabel[]{new JLabel("Grave_op"),new JLabel("Manas")},Game.composition.GRAVE_OP);
        
        //RFG_op
        createPanelAndComponents(0, 2, new JLabel[]{new JLabel("RFG"),new JLabel("empty")},Game.composition.RFG_OP);
        
        //RFG_cp
        createPanelAndComponents(0, 3, new JLabel[]{new JLabel("RFG"),new JLabel("empty")},Game.composition.RFG_OP);
        
         //Grave_op
        createPanelAndComponents(0, 4, new JLabel[]{new JLabel("Grave_op"),new JLabel("Manas")},Game.composition.GRAVE_CP);
        
        // Library_op
        createPanelAndComponents(0, 5, new JLabel[]{new JLabel("Library"),new JLabel("Lifes")},Game.composition.LIBRARY_CP);
        
        constr.gridheight = 1;
        constr.gridwidth = 8; // hovorim, ze bude zaberat dve policka. Potom nesmiem zabudnut nastavit scrollpanel na 8, aby sa spravil spravny pomer.
        constr.ipady = 10;
        constr.weightx = 0; // hovorim aby nedostal ziadne miesto naviac, ak by ah bolo
        constr.weighty = 0;
        
        // Hand_op
        createScrollAndComponents(2, 0, new JLabel[]{new JLabel("Card1"),new JLabel("Card2")},Game.composition.HAND_OP);
        
        // Manas_op
        createScrollAndComponents(2, 1, new JLabel[]{new JLabel("Mana1"),new JLabel("Mana2")},Game.composition.LANDS_OP);
        
        // Creatures_op
        createScrollAndComponents(2, 2, new JLabel[]{new JLabel("Creature1"),new JLabel("Creature2")},Game.composition.CREATURES_OP);
        
         // Creatures_cp
        createScrollAndComponents(2, 3, new JLabel[]{new JLabel("Creature1.1"),new JLabel("Creature2.1")},Game.composition.CREATURES_OP);
        
        // Manas_cp
        createScrollAndComponents(2, 4, new JLabel[]{new JLabel("Mana1"),new JLabel("Mana2")},Game.composition.LANDS_OP);
        
        // Hand_cp
        createScrollAndComponents(2, 5, new JLabel[]{new JLabel("Card1"),new JLabel("Card2")},Game.composition.HAND_OP);

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
        addToPanel(jp,labels);
        constr.gridx = pos_x;
        constr.gridy = pos_y;
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
        for (int i = 0; i < labels.length; ++i) {
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
        
        // set beginning player
        order = 0;
        Game.currentPlayer = data.players[order];
        
     }
     
     /** @brief Method handling each round.
      * 
      */
     public void round(){
         Battleground.refresh();
         
         // resst of the round
         order = (order+1)%2;
         Game.currentPlayer = data.players[order];
     }
    
     /**
      * Method when card is cast into play.
      */
     private void cardCast(Card card){
         switch(card.type){
             case CREATURE:
                 card.controller.addAttackerAndDef(card);
                 break;
         }
     }
     
    /** @brief Method representing and process untap phase of game.
     * 
     */
    private void untapPhase(){
        
    }
    
    /** @brief Method representing and process attack phase of game.
     * 
     */
    private void attackPhase(){
        
    }
    
    private void readDeck(){
        
    }
}
