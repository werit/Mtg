/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;


/** @brief Class containing all common information used through game.
 *
 * @author Tibor
 */
public class Game {
    /** @brief Enum containing names of each position on game board
     * This enumeration contain names describing every possible position used in GUI
     */
    
    Game(){
    }

    public enum composition{
        HAND_OP,
        HAND_CP,
        LANDS_OP,
        LANDS_CP,
        CREATURES_OP,
        CREATURES_CP,
        NON_CREATURE_PERN_OP,
        NON_CREATURE_PERN_CP,
        LIBRARY_OP,
        LIBRARY_CP,
        GRAVE_OP,
        GRAVE_CP,
        RFG_OP,
        RFG_CP,
        LIFES_OP,
        LIFES_CP,
        MOUNTAIN_OP,
        MOUNTAIN_CP,
        ISLAND_OP,
        ISLAND_CP,
        FOREST_OP,
        FOREST_CP,
        SWAMP_OP,
        SWAMP_CP,
        PLAIN_OP,
        PLAIN_CP,
        COLORLESS_OP,
        COLORLESS_CP,
        NEXT_STEP_BUTTON_OP,
        NEXT_STEP_BUTTON_cP
    }
    public enum cardType{
        INSTANT,
        SORCERY,
        CREATURE,
        ARTIFACT,
        LAND,
        ENCHANCEMENT
    }
    public enum creatureBattlePossib{
        ATTACKER,
        DEFENDER,
        BOTH,
        NONE
    }
    public enum battlefieldStirikes{
        FIRST_STRIKE_ATTACKER,
        FIRST_STRIKE_DEFENDER,
        ATTACKER,
        DEFENDER
    }
    
    /** @brief Enumeration describing position of player as attacker or defender.
     *  As attacker player has 0 and as defender player is assigned number 1.
     *  When is turn of other player all is needed is to add 1 and modulo by 2.
     */
    public enum playerPosition{
        FIRST (0),
        SECOND (1);
        private final int positionValue; /**< Private variable storing defining value of player position. */
        playerPosition(int posValue){ /**<  Constructor assigning value to private variable.*/
            this.positionValue = posValue;
        }
        public int position(){      /**< Public method to get value of private variable.*/
            return positionValue;
        }
    }

    
    /** @brief Enumeration of names of properties.
     *  This enum contains all names of possible properties of cards.
     */
    public enum cardProperties{
        HASTE,
        FIRST_STRIKE,
        POWER_BOOST,
        WHITE_MANA
    }   
    /**
     *  Enumeration containing error's type's. 
     */
    public enum Errs{
        XML_PROPERTY_UNKNOWN,
        XML_CARD_TYPE_UNKNOWN,
        XML_BOOST_TIME_OF_USE_UNKNOWN
    }
    /** @brief List containing all input tags with readable content.
     */
    public enum inputTags{
        WHITE,
        BLACK,
        GREEN,
        BLUE,
        RED,
        COLOURLESS,
        COUNT,
        PICTURE_PATH,
        BOOST,
        CARD,
        POWER,
        TOUGHNESS,
        DECK,
        DECK_NAME,
        CARDS,
        CASTING_COST,
        PROPERTIES   
    }
    
    /** @brief Enum containing all possible types of attribute "usable" of tag boost.
     *  Tag boost can be find in input file with attribute usable.
     *  And attribute usable can be one of latter possibilities.
     */
    public enum boostUsabil{
        COMES_INTO_PLAY,
        ATTACK,
        DEFENSE,
        IN_PLAY_GLOBAL_EFFECT,
        INSTANT,
        UPKEEP,
        UNTAP,
        LEAVES_PLAY
    }        
    
    /** @brief Enum characterising all possible states of game.
     *  Game states represents real game states in game Magic the Gathering.
     *  This enumeration purpose is to determine behaviour of cards that depend on game state.
     *  For example creature can use attack only during attack step, but abilities can be used nearly anytime.
     */
    public enum gameState{
        
        UNTAP(0),
        UPKEEP(1),
        DRAW(2),
        MAIN_PHASE(3),
        ATTACK(4),
        DEFENSE(5),
        MAIN_PHASE2(6),
        EOT(7),
        PLAYER_SWAP(8);
        private final int state;
        gameState(int state){
            this.state = state;
        }
        public int stateOfGame(){
            return this.state;
        }
        @Override
        public String toString(){
            switch(this.state){
                case 0:
                    return "Untap";
                case 1:
                    return "Upkeep";
                case 2:
                    return "Draw";
                case 3:
                    return "Main Phase";
                case 4:
                    return "Attack";
                case 5:
                    return "Defend";
                case 6:
                    return "Second Main Phase";
                case 7:
                    return "End of the turn";
                default:
                    return "Player swap";
                    
            }
        }
        
    }
    /** @brief Enumeration stating where is the card in game.
     *  When card is read, it is assigned it's location in the deck.
     *  Later on, this position is changing depending on play.
     *  For example, when card drawn, then location changes from #IN_DECK to #IN_HAND
     */
    public enum cardLocation{
        IN_HAND,
        IN_PLAY,
        IN_GRAVE,
        IN_RFG_PILE,
        IN_DECK
    }
    
    /** @brief Enumeration representing colours of manas.
     *  Enumeration is used as key values of HashMap for casting cost of cards and
     * 
     *  players mana pool.
     */
    public enum manaColours{
        PLAIN,
        SWAMP,
        FOREST,
        ISLAND,
        MOUNTAIN,
        COLORLESS;
        public static manaColours colourFromInt(int colour){
            switch(colour){
                case 0:
                    return PLAIN;
                case 1:
                    return SWAMP;
                case 2:
                    return FOREST;
                case 3:
                    return ISLAND;
                case 4:
                    return MOUNTAIN;
                case 5:
                    return COLORLESS;
                default:
                    return null;
            }
        }
        
    }
    
    static int enemyPlPreviewPosX;
    static int enemyPlPreviewPosY;
    static int playerPreviewPosX;
    static int playerPreviewPosY;
    static int pictWidth; /**< Variable defining width of cards.*/
    static int pictHeight; /**< Variable defining height of cards.*/
    static int shift; /**< Variable storing value of shift of scroll panels on game board.*/
    static gameState state; /**< Static variable characterising state of round.*/
    static int currentPlayer; /**< Variable storing number characterising player currently playing. By this number player can be found in .*/
    static public Map<Game.cardProperties,AbilityDecorator> propertyStorage; /**< Collection processing conversion between names of card properties and have stored pointers to them.*/
    static public Map<String,Game.cardType> cardTypeTranslator; /**< Collection processing conversion between string name from input file to enum, which represent type of creature.*/
    static public Map<String,Game.cardProperties> propertyTranslator; /**< Collection processing conversion between string name from input file to enum, which represent names of card properties.*/
    static public Map<Game.composition,JPanel> GUIComposition = new HashMap<>();/**< Collection to map GUI components. Ease access throughout the game.*/
    static public ArrayList<Exception> exceptTrack; /**< Collection of all exceptions thrown during run.*/
    static public Map<String,Game.inputTags> inputTagTranslator; /**< Collection processing conversion between string to enum mapping input tags. */
    static public Map<String,Game.boostUsabil> inpBoostUsAblTransl; /**< Collection processing conversion between string to enum mapping input attribute usAble of tag boost */
    static public MouseAdapter mousLis; /**< Variable granting global acces to mouse listener.*/
    /** @brief static initialiser filling all HashMaps used as dictionaries.
     *  Method define how will conversion look like. Which means, if string in input file should be changed so must be his conversion to enum definition be changed.
     *  If any new ability is created it is necessary to add it translation.
     */
    static {
        state = gameState.MAIN_PHASE; // first player starts in main phase.
        shift = 3;
        
        pictWidth = 110*2/3;
        pictHeight = 153*2/3;
        
        cardTypeTranslator = new HashMap<>();
        propertyTranslator = new HashMap<>();
        inputTagTranslator = new HashMap<>();
        exceptTrack = new ArrayList<>();
        inpBoostUsAblTransl = new HashMap<>();
        propertyStorage = new HashMap<>();
        
        cardTypeTranslator.put("land", cardType.LAND);
        cardTypeTranslator.put("creature", cardType.CREATURE);
        cardTypeTranslator.put("artifact", cardType.ARTIFACT);
        cardTypeTranslator.put("enchantment", cardType.ENCHANCEMENT);
        cardTypeTranslator.put("sorcery", cardType.SORCERY);
        cardTypeTranslator.put("instant", cardType.INSTANT);
        
        propertyTranslator.put("Haste", cardProperties.HASTE);
        propertyTranslator.put("FirstStrike", cardProperties.FIRST_STRIKE);
        propertyTranslator.put("PowerBoost", cardProperties.POWER_BOOST);
        propertyTranslator.put("WhiteMana", cardProperties.WHITE_MANA);
        
        inputTagTranslator.put("white", Game.inputTags.WHITE);
        inputTagTranslator.put("black", Game.inputTags.BLACK);
        inputTagTranslator.put("green", Game.inputTags.GREEN);
        inputTagTranslator.put("blue", Game.inputTags.BLUE);
        inputTagTranslator.put("red", Game.inputTags.RED);
        inputTagTranslator.put("colourless", Game.inputTags.COLOURLESS);
        inputTagTranslator.put("picturePath", Game.inputTags.PICTURE_PATH);
        inputTagTranslator.put("count", Game.inputTags.COUNT);
        inputTagTranslator.put("boost", Game.inputTags.BOOST);
        inputTagTranslator.put("card", Game.inputTags.CARD);   
        inputTagTranslator.put("power", Game.inputTags.POWER);
        inputTagTranslator.put("toughness", Game.inputTags.TOUGHNESS);
        inputTagTranslator.put("deck", inputTags.DECK);
        inputTagTranslator.put("deckName", inputTags.DECK_NAME);
        inputTagTranslator.put("cards", inputTags.CARDS);
        inputTagTranslator.put("castingCost", inputTags.CASTING_COST);
        inputTagTranslator.put("properties", inputTags.PROPERTIES);
        
        inpBoostUsAblTransl.put("inPlay", Game.boostUsabil.IN_PLAY_GLOBAL_EFFECT);
        inpBoostUsAblTransl.put("attack", Game.boostUsabil.ATTACK);
        inpBoostUsAblTransl.put("instant",Game.boostUsabil.INSTANT);
        inpBoostUsAblTransl.put("comeIntoPlay", Game.boostUsabil.COMES_INTO_PLAY);
        inpBoostUsAblTransl.put("leavesPlay", Game.boostUsabil.LEAVES_PLAY);
        inpBoostUsAblTransl.put("untap", Game.boostUsabil.UNTAP);
        inpBoostUsAblTransl.put("upkeep", Game.boostUsabil.UPKEEP);
        inpBoostUsAblTransl.put("defense",Game.boostUsabil.DEFENSE);
        
        propertyStorage.put(Game.cardProperties.FIRST_STRIKE,new FirstStrike());
        propertyStorage.put(cardProperties.HASTE, new Haste());
        propertyStorage.put(cardProperties.WHITE_MANA,new WhiteMana());
    }
    public static composition gravePlace(Player player){
        if(player.pos == Game.playerPosition.FIRST)
            return composition.GRAVE_CP;
        else
            return composition.GRAVE_OP;
    }
    
    public static composition handPlace(Player player){
        if(player.pos == Game.playerPosition.FIRST)
            return composition.HAND_CP;
        else
            return composition.HAND_OP;
    }
    public static composition libraryPlace(Player player){
        if(player.pos == Game.playerPosition.FIRST)
            return composition.LIBRARY_CP;
        else
            return composition.LIBRARY_OP;
    }
    public static composition creaturePlace(Player player){
        if(player.pos == Game.playerPosition.FIRST)
            return composition.CREATURES_CP;
        else
            return composition.CREATURES_OP;
    }
}
