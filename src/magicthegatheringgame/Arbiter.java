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
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



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
            drawACard(0);
            drawACard(1);
        }
        hideHand(1);
    }
    public void hideHand(int oponentNR){
        Game.composition hand;
        if(oponentNR == 0){
            hand = Game.composition.HAND_CP;
        }
        else{
            hand = Game.composition.HAND_OP;
        }
        JPanel jp;
        jp = Game.GUIComposition.get(hand);
        jp.removeAll();
        for (int i = 0; i < data.players[oponentNR].hand.size(); ++i) {
            jp.add(new JLabel(data.cardBack));
        }
    }
    
    public void showHand(int oponentNR){
        Game.composition hand;
        if(oponentNR == 0){
            hand = Game.composition.HAND_CP;
        }
        else{
            hand = Game.composition.HAND_OP;
        }
        JPanel jp;
        jp = Game.GUIComposition.get(hand);
        jp.removeAll();
        for (int i = 0; i < data.players[oponentNR].hand.size(); ++i) {
            jp.add(data.players[oponentNR].hand.get(i));
        }
    }
    private void drawACard(int playerPos){
        Card card;
        Game.composition hand;
        Game.composition library;
        if(playerPos == 0){
            hand = Game.composition.HAND_CP;
            library = Game.composition.LIBRARY_CP;
        }
        else{
            hand = Game.composition.HAND_OP;
            library = Game.composition.LIBRARY_OP;
        }
        card = data.players[playerPos].draw();
            if(card != null){
                Game.GUIComposition.get(hand).add(card);
            }
            else{
                JPanel jp = Game.GUIComposition.get(library);
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
        createPanelAndComponents(0, 0, new JLabel[]{new JLabel(data.cardBack)},Game.composition.LIBRARY_OP);
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
        int[] values = {currOp.getLifes(),currOp.getManaCount(Game.manaColours.SWAMP),currOp.getManaCount(Game.manaColours.PLAIN),
            currOp.getManaCount(Game.manaColours.FOREST),currOp.getManaCount(Game.manaColours.ISLAND),currOp.getManaCount(Game.manaColours.MOUNTAIN),
            currPl.getManaCount(Game.manaColours.COLORLESS),currPl.getManaCount(Game.manaColours.COLORLESS),currPl.getManaCount(Game.manaColours.ISLAND),
            currPl.getManaCount(Game.manaColours.MOUNTAIN),currPl.getManaCount(Game.manaColours.FOREST),currPl.getManaCount(Game.manaColours.PLAIN),
            currPl.getManaCount(Game.manaColours.SWAMP),currPl.getLifes()};
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
        
        data.players[0].pos = Game.playerPosition.FIRST;
        data.players[1].pos = Game.playerPosition.SECOND;
        for (Player player : data.players) {
            Collections.shuffle(player.deck);
        }
        
        // set beginning player
        Game.currentPlayer = 0;
        
     }
     
     /** @brief Method handling beginning of each new round.
      * 
      */
     public void newRound(){
         Battleground.refresh();
         
         // resst of the round
         order = (order+1)%2;
         Game.currentPlayer = order;
     }
     private void triggerCardGamePhaseAbil(){
         for (int i = 0; i < data.players[Game.currentPlayer].inPlayCard.size(); ++i) {
            data.players[Game.currentPlayer].inPlayCard.get(i).gameStateEvokeAbil(Game.state);
        }
     }
    /** @brief Method representing and process untap phase of game.
     * 
     */
    private void untapPhase(){
        triggerCardGamePhaseAbil();
        Card c;
        for(int i = 0; i < data.players[Game.currentPlayer].inPlayCard.size();++i){
            c = data.players[Game.currentPlayer].inPlayCard.get(i);
            if(c.isTapAble && c.isTapped){
                c.isTapped = false;
            }
        }
        data.players[Game.currentPlayer].manaPlayed = 0;
        changeGameState(Game.gameState.UPKEEP);
    }
    private void upKeep(){
        triggerCardGamePhaseAbil();
        changeGameState(Game.gameState.DRAW);
    }
    /** @brief Method processing draw phase of the game. 
     *  Method changes Game.state to #MAIN_PHASE and makes player draw a card.
     */
    private void drawPhase(){
        triggerCardGamePhaseAbil();
        drawACard(Game.currentPlayer);
        changeGameState(Game.gameState.MAIN_PHASE);
        
    }
    private void mainPahse(){
        switch(Game.state){
            case MAIN_PHASE:
                changeGameState(Game.gameState.ATTACK);
                break;
            case MAIN_PHASE2:
                changeGameState(Game.gameState.EOT);
                break;
        }
        
    }
    /** @brief Method taking care of reseting amount of each mana to 0 and untapping cards.
     *  Method calls showNoMana to set right amount of mana on screen.
     * @param newGameState 
     */
    private void changeGameState(Game.gameState newGameState){
        Game.state = newGameState;
        data.gameStateInd.setText(Game.state.toString());
        
        for (int i = 0; i < data.players.length; ++i) {
            for(Game.manaColours colour : Game.manaColours.values()){
                data.players[i].remMana(colour, data.players[i].getManaCount(colour));
            }
        }
        showNoMana(Game.composition.PLAIN_OP);
        showNoMana(Game.composition.PLAIN_CP);
        showNoMana(Game.composition.SWAMP_OP);
        showNoMana(Game.composition.SWAMP_CP);
        showNoMana(Game.composition.FOREST_OP);
        showNoMana(Game.composition.FOREST_CP);
        showNoMana(Game.composition.ISLAND_OP);
        showNoMana(Game.composition.ISLAND_CP);
        showNoMana(Game.composition.MOUNTAIN_OP);
        showNoMana(Game.composition.MOUNTAIN_CP);
        showNoMana(Game.composition.COLORLESS_OP);
        showNoMana(Game.composition.COLORLESS_CP);
    }
    /** @brief Method repainting zeros as mana count.
     *  Method used at the end of each phase to show zero as mana count.
     * @param manaPlace Key to Game.GUIComposition, which contains all components.
     */
    private void showNoMana(Game.composition manaPlace){
        JPanel jp; 
        jp = Game.GUIComposition.get(manaPlace);
        jp.removeAll();
        jp.add(new JLabel(new Integer(0).toString()));
    }
    /** @brief Method representing and process attack phase of game.
     * 
     */
    private void attackPhase(){
        triggerCardGamePhaseAbil();
        changeGameState(Game.gameState.DEFENSE);
    }
    private void defensePhase(){
        triggerCardGamePhaseAbil();
        changeGameState(Game.gameState.MAIN_PHASE2);
    }
    private void eot(){
        hideHand(Game.currentPlayer);
        changeGameState(Game.gameState.PLAYER_SWAP);

    }
    /** @brief Method blacking cards during change of players.
     * 
     */
    private void playerChange(){
        Game.currentPlayer += 1;
        Game.currentPlayer %= 2;    
        showHand(Game.currentPlayer);
        changeGameState(Game.gameState.UNTAP);
    }
    
    private void readDeck(){
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // catch event of changing game phase
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
                    mainPahse();
                    break;
                case ATTACK:
                    attackPhase();
                    break;
                case DEFENSE:
                    defensePhase();
                    break;
                case MAIN_PHASE2:
                    mainPahse();
                    break;
                case EOT:
                    eot();
                    break;
                case PLAYER_SWAP:
                    playerChange();
                    break;
            }
            return;
        }
        // Event click on card. Evaluate use of card.
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
                creatureConditions(c);
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
            Game.composition hand;
            Game.composition lands;
            if(c.controller == data.players[0]){
                hand = Game.composition.HAND_CP;
                lands = Game.composition.LANDS_CP;
            }
            else{
                hand = Game.composition.HAND_OP;
                lands = Game.composition.LANDS_OP;
            }
            Game.GUIComposition.get(hand).remove(c);
            Game.GUIComposition.get(lands).add(c);
            ++c.controller.manaPlayed;
            c.cardCast();
        }
    }
    private void creatureConditions(Card c){
        if(payCard(c)){
            Game.composition hand;
            Game.composition creatures;
            if(c.controller == data.players[0]){
                hand = Game.composition.HAND_CP;
                creatures = Game.composition.CREATURES_CP;
            }
            else{
                hand = Game.composition.HAND_OP;
                creatures = Game.composition.CREATURES_OP;
            }
            Game.GUIComposition.get(hand).remove(c);
            Game.GUIComposition.get(creatures).add(c);
            c.cardCast();
        }
         
    }
    /** Method checking if player have got enough mana in pool to cast a card.
     *  Method checks right amount of specified mana and finds out if rest of mana pool is high enough to cover colorless mana cost.
     * @param c Card to be casted.
     * @return Returns true, if player has enough mana and false otherwise.
     */
    private boolean checkCost(Card c){
        int colorlessPool = 0;
        for(Game.manaColours colour : Game.manaColours.values()){
            if(c.manaCosts.get(colour) > c.controller.getManaCount(colour)){
                if(Game.manaColours.COLORLESS.equals(colour)){
                    if(c.manaCosts.get(colour) > c.controller.getManaCount(colour) + colorlessPool){
                        return false;
                    }
                }
                else
                    return false;
            }
            else{
                colorlessPool += c.controller.getManaCount(colour) - c.manaCosts.get(colour);
            }
        }
        return true;
    }
    /** @brief Method tries pay mana cost of card.
     *  Method check if player has enough mana in pool. 
     * @param c Card to be paid for.
     * @return True if card is playable and played.
     */
    private boolean payCard(Card c){
        if(!checkCost(c))
            return false;
        // value needed to determine how much more manas must be subtracted from rest of manas
        int colorlessPayment = c.manaCosts.get(Game.manaColours.COLORLESS) - c.controller.getManaCount(Game.manaColours.COLORLESS);
        if(colorlessPayment < 0){
            colorlessPayment = 0;
            c.controller.remMana(Game.manaColours.COLORLESS,c.manaCosts.get(Game.manaColours.COLORLESS));
        }
        else
            c.controller.remMana(Game.manaColours.COLORLESS,c.manaCosts.get(Game.manaColours.COLORLESS) - colorlessPayment);
        // just looking at the first 5 colours. Because colourless mana is solved
        for (int i = 0; i < 4; ++i) {
            c.controller.remMana((Game.manaColours.colourFromInt(i)), c.manaCosts.get(Game.manaColours.colourFromInt(i)));
            if(c.controller.getManaCount(Game.manaColours.colourFromInt(i)) > 0 && colorlessPayment > 0){
                if(colorlessPayment > c.controller.getManaCount(Game.manaColours.colourFromInt(i))){
                    colorlessPayment -= c.controller.getManaCount(Game.manaColours.colourFromInt(i));
                    c.controller.remMana(Game.manaColours.colourFromInt(i),c.controller.getManaCount(Game.manaColours.colourFromInt(i)));
                }
                else{
                    c.controller.remMana(Game.manaColours.colourFromInt(i), colorlessPayment);
                    colorlessPayment = 0;
                }
            }
        }
        JPanel jp;
        Game.composition plain;
        Game.composition swamp;
        Game.composition forest;
        Game.composition island;
        Game.composition mountain;
        Game.composition colorless;
        if(c.controller == data.players[0]){
            plain = Game.composition.PLAIN_CP;
            swamp = Game.composition.SWAMP_CP;
            forest = Game.composition.FOREST_CP;
            island = Game.composition.ISLAND_CP;
            mountain = Game.composition.MOUNTAIN_CP;
            colorless = Game.composition.COLORLESS_CP;
        }
        else{
            plain = Game.composition.PLAIN_OP;
            swamp = Game.composition.SWAMP_OP;
            forest = Game.composition.FOREST_OP;
            island = Game.composition.ISLAND_OP;
            mountain = Game.composition.MOUNTAIN_OP;
            colorless = Game.composition.COLORLESS_OP;
        }
        //show plain
        jp = Game.GUIComposition.get(plain);
        jp.removeAll();
        jp.add(new JLabel(new Integer(c.controller.getManaCount(Game.manaColours.PLAIN)).toString()));
        //show swamp
        jp = Game.GUIComposition.get(swamp);
        jp.removeAll();
        jp.add(new JLabel(new Integer(c.controller.getManaCount(Game.manaColours.SWAMP)).toString()));
        //show forest
        jp = Game.GUIComposition.get(forest);
        jp.removeAll();
        jp.add(new JLabel(new Integer(c.controller.getManaCount(Game.manaColours.FOREST)).toString()));
        //show island
        jp = Game.GUIComposition.get(island);
        jp.removeAll();
        jp.add(new JLabel(new Integer(c.controller.getManaCount(Game.manaColours.ISLAND)).toString()));
        //show mountain
        jp = Game.GUIComposition.get(mountain);
        jp.removeAll();
        jp.add(new JLabel(new Integer(c.controller.getManaCount(Game.manaColours.MOUNTAIN)).toString()));
        //show colorless
        jp = Game.GUIComposition.get(colorless);
        jp.removeAll();
        jp.add(new JLabel(new Integer(c.controller.getManaCount(Game.manaColours.COLORLESS)).toString()));
        
        return true;
    }
}
