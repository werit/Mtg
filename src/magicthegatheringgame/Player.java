/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** @brief Class containing all information related to player.
 *  Stores creatures of player, life of player etc. 
 *  Also provides public methods to their access eventually to their alternation.
 * @author werit
 */
public class Player {

    
    
    public Game.playerPosition battlefieldPosition; /**< Variable determining attacking and defending player.*/
    public int manaLimit;
    public int manaPlayed;
    Player(){
        mountainPoolPlayer = 0;
        plainPoolPlayer = 0;
        islandPoolPlayer = 0;
        swampPoolPlayer = 0;
        forestPoolPlayer = 0;
        colorlessPoolPlayer = 0;
        manaLimit = 1;
        manaPlayed = 0;
        lifes = 20;
        hand = new ArrayList<>(150);
        deck = new ArrayList<>(150);
        creatures = new HashMap<>();
        inPlayCard = new ArrayList<>();
    }
    
    /** @brief Method to add attacker to array of attackers.
     *  Method add to private store space of player possible attacker passed as parameter.
     *  @param attacker Creature card that is able to attack this turn.
     */
    void addAttacker(Card attacker){
        this.creatures.get(Game.creatureBattlePossib.ATTACKER).add(attacker);
    }
    
    /** @brief Method giving access to all possible attackers of player.
     *  Return array has no reference to original array stored in class Player, so any change will NOT be done to original array.
     *  @return Return value is array of all attacker. This array has no reference to original objects.
     */
    CardEvent[] getAttackers(){
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
    void addDefender(Card defender){
        this.creatures.get(Game.creatureBattlePossib.DEFENDER).add(defender);
    }
    
    /** @brief Method giving access to all possible defenders of player.
     *  Return array has no reference to original array stored in class Player, so any change will NOT be done to original array.
     *  @return Return value is array of all attacker. This array has no reference to original objects.
     */
    CardEvent[] getDefenders(){
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
    void addAttackerAndDef(Card attAndDef){
        this.creatures.get(Game.creatureBattlePossib.BOTH).add(attAndDef);
    }
    
    /** @brief Method to add non attacker and non defender at the same time to array of NONE doers.
     *  Method add to private store space of player non attacker and non defender passed as parameter.
     *  @param norAttNorDef Creature card that is unable to attack or block now.
     */
    void addNonAttackerNonDef(Card norAttNorDef){
        this.creatures.get(Game.creatureBattlePossib.NONE).add(norAttNorDef);
    }
    
    /** 
     * methods to add certain amount of mana to mana pool 
     */
    void addPlain(int count){
        plainPoolPlayer += count ;
    }
    void addMountain(int count){
        mountainPoolPlayer += count ;
    }
    void addIsland(int count){
        islandPoolPlayer += count ;
    }
    void addSwamp(int count){
        swampPoolPlayer += count ;
    }
    void addForest(int count){
        forestPoolPlayer += count ;
    }
    void addColorless(int count){
        colorlessPoolPlayer += count;
    }
    /** @brief Method to subtract used plains.
     * @param count Number of mana spent.
     */
    void remPlain(int count){
        plainPoolPlayer -= count ;
    }
    void remMountain(int count){
        mountainPoolPlayer -= count ;
    }
    void remIsland(int count){
        islandPoolPlayer -= count ;
    }
    void remSwamp(int count){
        swampPoolPlayer -= count ;
    }
    void remForest(int count){
        forestPoolPlayer -= count ;
    }
    void remColorless(int count){
        colorlessPoolPlayer -= count;
    }
    /** @brief Method to reveal current amount of white mana in pool.
     */
    int getPlainCount(){
        return plainPoolPlayer;
    }
    int getMountainCount(){
       return mountainPoolPlayer;
    }
    int getIslandCount(){
       return islandPoolPlayer;
    }
    int getSwampCount(){
        return swampPoolPlayer;
    }
    int getForestCount(){
        return forestPoolPlayer;
    }
    int getColorlessCount(){
        return colorlessPoolPlayer;
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
    byte cardsOnHand(){
        return (byte)hand.size();
    }
    boolean isActive = false;
   
    public void subtractLifes(byte subtractor){ /**< public API for subtracting dealt demage. */
        lifes += subtractor;
    }
    
    public int getLifes(){ /**< public API for getting actual ammount of lifes. */
        return lifes;
    }
    /**
     * Method removing card from players hand.
     * Also removes picture from hand.
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
    }
    
    /** @brief Stores all creatures of this player currently on the battlefield. 
     *  Enumeration defines possibilities of creature. If it can attack, block, do both or none.
     */
    private Map<Game.creatureBattlePossib,ArrayList<Card>> creatures; 
    ArrayList<Card> abilityUsers;
    ArrayList<Card> attackers;
    ArrayList<Card> inPlayAbil;
    ArrayList<Card> inPlayCard; /**< Here are stored all cards that have been played. */
    private ArrayList<Card> hand; /**> Cards on hand of this player.*/
    ArrayList<Card> deck; /**> Cards in library of this player.*/
    private int lifes; /**< Ammount of lifes of player.*/
    private byte indexOfCardToDraw;
    private byte colorlessPoolPlayer;
    private byte mountainPoolPlayer;
    private byte plainPoolPlayer;
    private byte islandPoolPlayer;
    private byte swampPoolPlayer;
    private byte forestPoolPlayer;
}
