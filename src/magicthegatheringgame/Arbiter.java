/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;



public class Arbiter extends MouseAdapter{
    private final JPanel pane;
    private final JPanel discardPane;
    private GridBagConstraints constr;
    private final Data data;
    private int order;
    final JFileChooser fc;

    private AttackButtonsListener attAdapt;
     /**
     * Enumeration telling which player is on loosing side.
     */
    private enum LoosingPlayer{
        UPPER,
        BOTTON,
        BOTH
    }
    Arbiter(JPanel pane){
        this.pane = pane;
        data = new Data();
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new FileNameExtensionFilter("xml files (*.xml)", "xml"));
        attAdapt = new AttackButtonsListener((JFrame)SwingUtilities.getWindowAncestor(pane));
        discardPane = new JPanel();
    }
    public void arbitGame(){
        initializeGamePLay();
        createnviroment();
        play();
    }
    /**
     * Method begins play.
     * Let both players draw cards and hide hand of second player.
     */
    private void play(){
        
        for (int i = 0; i < 7; ++i) {
            drawACard(0);
            drawACard(1);
        }
        hideHand(1);
    }
    /**
     * Hide hand of player specified by parameter.
     * @param oponentNR Parameter is number of player who's hand is about to be hidden. Expected numbers are 0 and 1. Where 0 is Bottom player and 1 is upper. 
     */
    public void hideHand(int oponentNR){
        JPanel jp;
        jp = Game.GUIComposition.get(Game.handPlace(data.players[oponentNR]));
        jp.removeAll();
        for (int i = 0; i < data.players[oponentNR].handMaxSize; ++i) {
            jp.add(new JLabel(data.cardBack));
        }
    }
    /**
     * Method showing hand of player defined by parameter.
     * @param oponentNR Parameter is number, position, of player. Expected numbers are 0 and 1. Where 0 is Bottom player and 1 is upper. 
     */
    public void showHand(int oponentNR){
        JPanel jp;
        jp = Game.GUIComposition.get(Game.handPlace(data.players[oponentNR]));
        jp.removeAll();
        for (int i = 0; i < data.players[oponentNR].hand.size(); ++i) {
            jp.add(data.players[oponentNR].hand.get(i));
        }
    }
    /**
     * Method handling drawing card of player defined by parameter.
     * @param playerPos Parameter is number, position, of player. Expected numbers are 0 and 1. Where 0 is Bottom player and 1 is upper. 
     */
    private void drawACard(int playerPos){
        Card card;
        Game.composition hand = Game.handPlace(data.players[playerPos]);
        Game.composition library = Game.libraryPlace(data.players[playerPos]);
    
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
    }/**
     * Main method for creating environment.
     * Set all layouts of main frame and adds all needed components.
     */
    private void createnviroment(){
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
        
         //Grave_currPlf
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
        createScrollAndComponents(Game.shift, 0, null,Game.composition.HAND_OP);
         // Hand_currPl
        createScrollAndComponents(Game.shift, 11,null,Game.composition.HAND_CP);
        
        constr.gridheight = 2;
        
        // Manas_op
        createScrollAndComponents(Game.shift, 3, null,Game.composition.LANDS_OP);
        
        // Creatures_op
        createScrollAndComponents(Game.shift, 5, null,Game.composition.CREATURES_OP);
        
         // Creatures_currPl
        createScrollAndComponents(Game.shift, 7, null,Game.composition.CREATURES_CP);
        
        // Manas_currPl
        createScrollAndComponents(Game.shift, 9, null,Game.composition.LANDS_CP);

        
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
        for (int i = 0; i < 2; ++i) {
            data.players[i] = new Player();
            ReadDeck.readDeckMain(getDeck(i),data.players[i]);
            Collections.shuffle(data.players[i].deck);
        }
        data.players[0].pos = Game.playerPosition.FIRST;
        data.players[1].pos = Game.playerPosition.SECOND;
        // set beginning player
        Game.currentPlayer = 0;
        
     }
     /**
      * Method for choosing of deck through fileChooser.
      * @return Return value is file path.
      */
     private String getDeck(int order){
         String message = null;
         int endIndex = 0;
         try{
            switch(order){
                case 0:
                    message = "Choose deck for first player.";
                    break;
                case 1:
                    message = "Choose deck for second player";
                    break;
                    default:
                        throw new Exception("Unknown number of players. Unknown player number is " + order);
            }
         }
         catch (Exception e){
             JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(pane),e.getMessage());
             System.exit(0);
         }
         // maybe allow to explicitly say to user what to do.
         //JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(pane),message);
         fc.setDialogTitle(message);
         int returnVal = fc.showOpenDialog(pane);
         while(returnVal != JFileChooser.APPROVE_OPTION){
             returnVal = fc.showOpenDialog(pane);
             if(endIndex > 3)
                 System.exit(0);
             else
                ++endIndex;
         }
         return fc.getSelectedFile().getPath();
     }
     
     /** @brief Method handling beginning of each new round.
      * 
      */
     public void newRound(){
         Battleground.refresh();
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
            if(c.type == Game.cardType.CREATURE)
                ((Creature)c).setNoSummoningSickness();
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
        showCurrentMana(Game.composition.PLAIN_OP,Game.manaColours.PLAIN,1);
        showCurrentMana(Game.composition.PLAIN_CP,Game.manaColours.PLAIN,0);
        showCurrentMana(Game.composition.SWAMP_OP,Game.manaColours.SWAMP,1);
        showCurrentMana(Game.composition.SWAMP_CP,Game.manaColours.SWAMP,0);
        showCurrentMana(Game.composition.FOREST_OP,Game.manaColours.FOREST,1);
        showCurrentMana(Game.composition.FOREST_CP,Game.manaColours.FOREST,0);
        showCurrentMana(Game.composition.ISLAND_OP,Game.manaColours.ISLAND,1);
        showCurrentMana(Game.composition.ISLAND_CP,Game.manaColours.ISLAND,0);
        showCurrentMana(Game.composition.MOUNTAIN_OP,Game.manaColours.MOUNTAIN,1);
        showCurrentMana(Game.composition.MOUNTAIN_CP,Game.manaColours.MOUNTAIN,0);
        showCurrentMana(Game.composition.COLORLESS_OP,Game.manaColours.COLORLESS,1);
        showCurrentMana(Game.composition.COLORLESS_CP,Game.manaColours.COLORLESS,0);
    }
    /** @brief Method repainting zeros as mana count.
     *  Method used at the end of each phase to show zero as mana count.
     * @param manaPlace Key to Game.GUIComposition, which contains all components.
     */
    private void showCurrentMana(Game.composition manaPlace,Game.manaColours colour,int playerPos){
        JPanel jp; 
        jp = Game.GUIComposition.get(manaPlace);
        jp.removeAll();
        jp.add(new JLabel(new Integer(data.players[playerPos].getManaCount(colour)).toString()));
    }
    /** @brief Method representing and process attack phase of game.
     * 
     */
    private void attackPhase(){
        triggerCardGamePhaseAbil();
        changeGameState(Game.gameState.DEFENSE);
        if(Battleground.fighters.keySet().size() > 0){
            ArrayList<Creature> blockers = availableCreatToBlock();
            JPanel defMainP = new JPanel(new BorderLayout());
                // all creatures in set are attackers 
            this.attAdapt.refreshData(Arrays.copyOf(Battleground.fighters.keySet().toArray(), Battleground.fighters.keySet().toArray().length,Creature[].class),
                    blockers,defMainP);            
            Battleground.setDefPlayer( data.players[(Game.currentPlayer + 1) % 2]);
            if (blockers.size() > 0){
                JFrame jf = (JFrame)SwingUtilities.getWindowAncestor(pane);
                jf.setEnabled(false);
                JFrame def_frame = new JFrame("Defense");
                def_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                defMainP.setLayout(new javax.swing.BoxLayout(defMainP, javax.swing.BoxLayout.Y_AXIS));
                def_frame.add(defMainP);
                def_frame.pack();
                // maximalize to whole screen
                def_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                def_frame.setFocusable(true);
                def_frame.setVisible(true);
                def_frame.revalidate();
                def_frame.repaint();
                
                this.attAdapt.chooseBlockers();

            }
        }
        triggerCardGamePhaseAbil();
    }
    /**
     * Method returning all creatures of defending player, usable to block.
     * @return ArrayList of Cards, that are creatures and are available to block, NEVER null.
     */
    private ArrayList<Creature> availableCreatToBlock(){
        ArrayList<Creature> blockers = new ArrayList<>();
        for (Card c :data.players[(Game.currentPlayer+1)%2].inPlayCard){
            if(c.type == Game.cardType.CREATURE && !c.isTapped)
                blockers.add((Creature)c);
        }
        return blockers;
        
    }
    private void defensePhase(){
        if(Battleground.fighters.keySet().size() > 0){
            evaluate_attack(Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER);
            evaluate_attack(Game.battlefieldStirikes.ATTACKER);
        }
        Battleground.refresh();
        triggerCardGamePhaseAbil();
        changeGameState(Game.gameState.MAIN_PHASE2);
    }
    /**
     * Method starting evaluating attack and then clearing dead creatures.
     * @param strike parameter defining what kind of strike it is. Usable are #FIRST_STRIKE_ATTACKER or #ATTACKER
     */
    private void evaluate_attack(Game.battlefieldStirikes strike){
        assert(strike == Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER || strike == Game.battlefieldStirikes.ATTACKER);
        Battleground.evaluateFight(strike);
        //remove all dead creatures
        removeDead();
        chackGameOver();
    }
    /**
     * Method removing all dead creatures from battlefield.
     */
    private void removeDead(){
        Game.composition defCrea = Game.creaturePlace(data.players[(Game.currentPlayer + 1) % 2]);
        Game.composition attCrea = Game.creaturePlace(data.players[Game.currentPlayer]);
        Game.composition defGr = Game.gravePlace(data.players[(Game.currentPlayer + 1) % 2]);
        Game.composition attGr = Game.gravePlace(data.players[Game.currentPlayer]);

        JPanel jpFrom = Game.GUIComposition.get(defCrea);
        JPanel jpTo = Game.GUIComposition.get(defGr);
        Card pict = null; // remember last dead creature
        for(Creature defen:Battleground.deadBlockers()){
           jpFrom.remove(defen);
           data.players[(Game.currentPlayer + 1) % 2].inPlayCard.remove(defen);
           data.players[(Game.currentPlayer + 1) % 2].inGraveyard.add(defen);
           defen.cardLoc = Game.cardLocation.IN_GRAVE;
           defen.refreshCard();
           pict = defen;
        }
        
        if(pict != null){
            jpTo.removeAll();
            jpTo.add(pict);
        }
        pict = null;
        jpFrom = Game.GUIComposition.get(attCrea);
        jpTo = Game.GUIComposition.get(attGr);
        for(Creature att:Battleground.deadAttackers()){
           jpFrom.remove(att);
           data.players[Game.currentPlayer].inPlayCard.remove(att);
           data.players[Game.currentPlayer].inGraveyard.add(att);
           att.cardLoc = Game.cardLocation.IN_GRAVE;
           att.refreshCard();
           pict = att;
        }
        
        if(pict != null){
            jpTo.removeAll();
            jpTo.add(pict);
        }
    }
    private void eot(){
        if(data.players[Game.currentPlayer].hand.size() > data.players[Game.currentPlayer].handMaxSize){
            discardCard(data.players[Game.currentPlayer],data.players[Game.currentPlayer].hand.size() - data.players[Game.currentPlayer].handMaxSize);
        }
        hideHand(Game.currentPlayer);
        changeGameState(Game.gameState.PLAYER_SWAP);

    }
    /**
     * Method handling discarding card.
     * Let player choose and then discard chosen card.
     * @param player Parameter is player who is discarding card.
     * @param howManyToDics Parameter determining how many cards have to be discarded.
     */
    public void discardCard(Player player,int howManyToDics){
        ((JFrame)SwingUtilities.getWindowAncestor(this.pane)).setEnabled(false);
        JFrame jf = new JFrame();
        jf.add(this.discardPane.add(chooseCardToDiscard(player,howManyToDics)));
        jf.pack();
        jf.setFocusable(true);
        jf.setEnabled(true);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jf.setTitle("Discarding card.");
        jf.revalidate();
        jf.repaint();
    }
    
    /** Creates the panel shown by the first tab. */
    private JPanel chooseCardToDiscard(final Player player,final int howManyToDics) {
        JPanel discPane = new JPanel(new BorderLayout());
        final int numButtons = player.hand.size();
        JRadioButton[] radioButtons = new JRadioButton[numButtons];
        final ButtonGroup group = new ButtonGroup();
        
        for(int i = 0;i < numButtons; ++i){
            radioButtons[i] = new JRadioButton(player.hand.get(i).toString());
            group.add(radioButtons[i]);
            radioButtons[i].setActionCommand(new Integer(i).toString());
        }
        radioButtons[0].setSelected(true);

        JButton showItButton = new JButton("Discard.");
        showItButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = group.getSelection().getActionCommand();
                for(int i = 0;i < numButtons;++i){
                    if(command.equals(new Integer(i).toString())){
                        fromHandToGrave(player,player.hand.get(i));
                        ((JFrame)SwingUtilities.getWindowAncestor((JButton)e.getSource())).dispose();                  
                        discardPane.removeAll();
                        ((JFrame)SwingUtilities.getWindowAncestor(pane)).setEnabled(true);
                        ((JFrame)SwingUtilities.getWindowAncestor(pane)).setVisible(true);
                        if(howManyToDics > 1)
                            discardCard(player, howManyToDics - 1);
                    }
                }
            }
        });
        JPanel internDiscPane = new JPanel();
        JLabel label = new JLabel("Choose card to be discarded.");
        
        internDiscPane.setLayout(new BoxLayout(internDiscPane, BoxLayout.PAGE_AXIS));
        internDiscPane.add(label);
        
        for(int i = 0;i < radioButtons.length;++i){
            internDiscPane.add(radioButtons[i]);
        }
        
        JPanel jp = new JPanel(new BorderLayout());
        JPanel showButt = new JPanel(new FlowLayout());
        showButt.add(showItButton);
        jp.add(internDiscPane, BorderLayout.PAGE_START);
        jp.add(showButt, BorderLayout.PAGE_END);
        discPane.add(jp);
        return discPane;
    }
    /**
     * Method handling discarding card from players hand to the grave.
     * @param player Player who is discarding card from his hand.
     * @param card Card which is about to be discarded.
     */
    public void fromHandToGrave(Player player,Card card){
        Game.GUIComposition.get(Game.gravePlace(player)).removeAll();
        Game.GUIComposition.get(Game.handPlace(player)).remove(card);
        Game.GUIComposition.get(Game.gravePlace(player)).add(card);

        player.hand.remove(card);
        player.inGraveyard.add(card);
        card.cardLoc = Game.cardLocation.IN_GRAVE;
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
    @Override
    public void mouseClicked(MouseEvent e) {
        this.pane.repaint();
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
                card.onUse(Game.state);
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
        showCurrentMana(plain,Game.manaColours.PLAIN,c.controller.pos.position());
        //show swamp
        showCurrentMana(swamp,Game.manaColours.SWAMP,c.controller.pos.position());
        //show forest
        showCurrentMana(forest,Game.manaColours.FOREST,c.controller.pos.position());
        //show island
        showCurrentMana(island,Game.manaColours.ISLAND,c.controller.pos.position());
        //show mountain
        showCurrentMana(mountain,Game.manaColours.MOUNTAIN,c.controller.pos.position());
        //show colorless
        showCurrentMana(colorless,Game.manaColours.COLORLESS,c.controller.pos.position());
        
        return true;
    }
    /**
     * Method called to check, if the game is not finished.
     * In case it is, this method calls method game over.
     */
    public void chackGameOver(){
       String message = null;
        if(data.players[Game.currentPlayer].getLifes() <= 0){
            if(data.players[(Game.currentPlayer+1)%2].getLifes() <= 0){
                message = "Both players lost";
            }
            else{
                message = data.players[Game.currentPlayer].positionMessage() + " player lost the game.";
            }
        }
        else{
            if(data.players[(Game.currentPlayer+1)%2].getLifes() <= 0)
                message = data.players[(Game.currentPlayer+1)%2].positionMessage() + " player lost the game.";
        }
        if(message != null){
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(pane),message);
            System.exit(0);
        }
    }
}
