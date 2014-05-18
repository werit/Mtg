/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** @brief Class containing all information related to player.
 *  Stores creatures of player, life of player etc. 
 *  Also provides public methods to their access eventually to their alternation.
 * @author werit
 */
public class Player {

    
    
    public Game.playerPosition battlefieldPosition; /**< Variable determining attacking and defending player.*/
    public int manaLimit;
    public int manaPlayed;
    public int handMaxSize;
    public Game.playerPosition pos;
    public Player(){
        manaPoolPlayer = new HashMap<>();
        for(Game.manaColours colour: Game.manaColours.values()){
            manaPoolPlayer.put(colour, 0);
        }
        manaLimit = 1;
        manaPlayed = 0;
        lifes = 20;
        hand = new ArrayList<>(150);
        deck = new ArrayList<>(150);
        creatures = new HashMap<>();
        inPlayCard = new ArrayList<>();
        inGraveyard = new ArrayList<>();
        RFG = new ArrayList<>();
        pos = null;
        handMaxSize = 7;
    }
    
    /** @brief Method to add attacker to array of attackers.
     *  Method add to private store space of player possible attacker passed as parameter.
     *  @param attacker Creature card that is able to attack this turn.
     */
    public void addAttacker(Card attacker){
        this.creatures.get(Game.creatureBattlePossib.ATTACKER).add(attacker);
    }
    
    /** @brief Method giving access to all possible attackers of player.
     *  Return array has no reference to original array stored in class Player, so any change will NOT be done to original array.
     *  @return Return value is array of all attacker. This array has no reference to original objects.
     */
    public CardEvent[] getAttackers(){
        CardEvent[] att = new CardEvent[this.creatures.get(Game.creatureBattlePossib.ATTACKER).size() + this.creatures.get(Game.creatureBattlePossib.BOTH).size()];
        System.arraycopy(this.creatures.get(Game.creatureBattlePossib.ATTACKER).toArray(), 0, att, 0,this.creatures.get(Game.creatureBattlePossib.ATTACKER).size() );
        System.arraycopy(this.creatures.get(Game.creatureBattlePossib.BOTH).toArray(), 0, att,
                this.creatures.get(Game.creatureBattlePossib.ATTACKER).size() , this.creatures.get(Game.creatureBattlePossib.BOTH).size());
        
        return  att;
    }
    
    /** @brief Method to add attacker to array of attackers.
     *  Method add to private store space of player possible defender passed as parameter.
     *  @param defender Creature card that is able to defend this turn.
     */
    public void addDefender(Card defender){
        this.creatures.get(Game.creatureBattlePossib.DEFENDER).add(defender);
    }
    
    /** @brief Method giving access to all possible defenders of player.
     *  Return array has no reference to original array stored in class Player, so any change will NOT be done to original array.
     *  @return Return value is array of all attacker. This array has no reference to original objects.
     */
    public CardEvent[] getDefenders(){
        CardEvent[] def = new CardEvent[this.creatures.get(Game.creatureBattlePossib.DEFENDER).size() + this.creatures.get(Game.creatureBattlePossib.BOTH).size()];
        System.arraycopy(this.creatures.get(Game.creatureBattlePossib.DEFENDER).toArray(), 0, def, 0,this.creatures.get(Game.creatureBattlePossib.DEFENDER).size() );
        System.arraycopy(this.creatures.get(Game.creatureBattlePossib.BOTH).toArray(), 0, def,
                this.creatures.get(Game.creatureBattlePossib.DEFENDER).size() , this.creatures.get(Game.creatureBattlePossib.BOTH).size());
        
        return  def;
    }
 
    /** @brief Method to add attacker and defender to array of attackers.
     *  Method add to private store space of player possible attacker and blocker passed as parameter.
     *  @param attAndDef Creature card that is able to attack or block this turn.
     */
    public void addAttackerAndDef(Card attAndDef){
        this.creatures.get(Game.creatureBattlePossib.BOTH).add(attAndDef);
    }
    
    /** @brief Method to add non attacker and non defender at the same time to array of NONE doers.
     *  Method add to private store space of player non attacker and non defender passed as parameter.
     *  @param norAttNorDef Creature card that is unable to attack or block now.
     */
    public void addNonAttackerNonDef(Card norAttNorDef){
        this.creatures.get(Game.creatureBattlePossib.NONE).add(norAttNorDef);
    }
    
    /** 
     * methods to add certain amount of mana to mana pool 
     * @param colour
     * @param count
     */
    public void addMana(Game.manaColours colour,int count){
        switch(colour){
            case PLAIN:
                manaPoolPlayer.put(Game.manaColours.PLAIN, manaPoolPlayer.get(Game.manaColours.PLAIN) + count);
                break;
            case SWAMP:
                manaPoolPlayer.put(Game.manaColours.SWAMP, manaPoolPlayer.get(Game.manaColours.SWAMP) + count);
                break;
            case FOREST:
                manaPoolPlayer.put(Game.manaColours.FOREST, manaPoolPlayer.get(Game.manaColours.FOREST) + count);
                break;
            case ISLAND:
                manaPoolPlayer.put(Game.manaColours.ISLAND, manaPoolPlayer.get(Game.manaColours.ISLAND) + count);
                break;
            case MOUNTAIN:
                manaPoolPlayer.put(Game.manaColours.MOUNTAIN, manaPoolPlayer.get(Game.manaColours.MOUNTAIN) + count);
                break;
            case COLORLESS:
                manaPoolPlayer.put(Game.manaColours.COLORLESS, manaPoolPlayer.get(Game.manaColours.COLORLESS) + count);
                break;
        }
    }
    /** @brief Method to subtract used plains.
     * @param count Number of mana spent.
     */
    void remMana(Game.manaColours colour,int count){
        addMana(colour,-count);
    }
    /**
     * @param colour *  @brief Method to reveal current amount of mana specified as parameter in pool.
     * @return 
     */
    public int getManaCount(Game.manaColours colour){
        switch(colour){
            case PLAIN:
                return manaPoolPlayer.get(Game.manaColours.PLAIN);
            case SWAMP:
                return manaPoolPlayer.get(Game.manaColours.SWAMP);
            case FOREST:
                return manaPoolPlayer.get(Game.manaColours.FOREST);
            case ISLAND:
                return manaPoolPlayer.get(Game.manaColours.ISLAND);
            case MOUNTAIN:
                return manaPoolPlayer.get(Game.manaColours.MOUNTAIN);
            case COLORLESS:
                return manaPoolPlayer.get(Game.manaColours.COLORLESS);
        }
        return 0;
    }
    
    Card draw(){
        Card card;
        if(deck.size() > indexOfCardToDraw){
            card = deck.get(indexOfCardToDraw++);
            hand.add(card);
            card.cardLoc = Game.cardLocation.IN_HAND;
            return card;
        }
        else
            return null;
        
    }
    void discard(byte[] cardsToDiscard){
        // gets array of index of cards to be discarded
        for( byte i = 0; i< cardsToDiscard.length;++i){
            hand.remove(cardsToDiscard[i]);
        }
    }
    /**
     * public API for subtracting dealt damage.
     * @param subtractor This parameter is supposed to by non negative number.  
     */
    public void subtractLifes(int subtractor){
        lifes -= subtractor;
        Game.composition destLife;
        if(this.pos == Game.playerPosition.FIRST)
            destLife = Game.composition.LIFES_CP;
        else
            destLife = Game.composition.LIFES_OP;
        JPanel jp = Game.GUIComposition.get(destLife);
        jp.removeAll();
        jp.add(new JLabel(new Integer(this.getLifes()).toString()));
        
    }
    
    public int getLifes(){ /**< public API for getting actual ammount of lifes. */
        return lifes;
    }
    /**
     * Method removing card from players hand.
     * Also removes picture from hand.
     * @param c
     * @param loc
     */
    public void remCard(Card c,Game.cardLocation loc){
        switch(loc){
            case IN_HAND:
                this.hand.remove(c);                
                break;
            case IN_PLAY:
                this.inPlayCard.remove(c);
                break;
        }  
    }/**
     * Method says what is players position in string form.
     * @return String form of side of player.
     */
    public String positionMessage(){
        if (this.pos == Game.playerPosition.FIRST)
            return "Botton";
        else
            return "Upper";
    } 
    /** @brief Stores all creatures of this player currently on the battlefield. 
     *  Enumeration defines possibilities of creature. If it can attack, block, do both or none.
     */
    private Map<Game.creatureBattlePossib,ArrayList<Card>> creatures; 
    ArrayList<Card> abilityUsers;
    ArrayList<Card> attackers;
    ArrayList<Card> inPlayAbil;
    ArrayList<Card> inPlayCard; /**< Here are stored all cards that have been played. */
    ArrayList<Card> hand; /**> Cards on hand of this player.*/
    ArrayList<Card> deck; /**> Cards in library of this player.*/
    ArrayList<Card> inGraveyard; /**> Cards in graveyard of this player.*/
    ArrayList<Card> RFG; /**> Cards , this player owns, removed from the game.*/
    private int lifes; /**< Ammount of lifes of player.*/
    private Map<Game.manaColours,Integer> manaPoolPlayer;
    private byte indexOfCardToDraw;
}
