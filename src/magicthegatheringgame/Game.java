/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

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
    enum composition{
        HAND_OP,
        HAND_CP,
        LANDS_OP,
        LANDS_CP,
        CREATURES_OP,
        CREATURES_CP,
        NON_CREATURE_PERN_OP,
        NON_CREATURE_PERN_CP,
        LIBRABRY_OP,
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
        COLORLESS_CP
    }
    enum cardType{
        INSTANT,
        SORCERY,
        CREATURE,
        ARTIFACT,
        LAND,
        ENCHANCEMENT
    }
    enum creatureBattlePossib{
        ATTACKER,
        DEFENDER,
        BOTH,
        NONE
    }
    enum battlefieldStirikes{
        FIRST_STRIKE_ATTACKER,
        FIRST_STRIKE_DEFENDER,
        ATTACKER,
        DEFENDER
    }
    
    /** @brief Enumeration describing position of player as attacker or defender.
     *  As attacker player has 0 and as defender player is assigned number 1.
     *  When is turn of other player all is needed is to add 1 and modulo by 2.
     */
    enum playerPosition{
        ATTACKER (0),
        DEFENDER (1);
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
    enum cardProperties{
        HASTE,
        FIRST_STRIKE,
        POWER_BOOST,
        WHITE_MANA
    }    
    
    /** @brief List containing all input tags with readable content.
     */
    enum inputTags{
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
        TOUGHNESS
        
    }
    
    /** @brief Enum containing all possible types of attribute "usable" of tag boost.
     *  Tag boost can be find in input file with attribute usable.
     *  And attribute usable can be one of latter possibilities.
     */
    enum boostUsabil{
        COMES_INTO_PLAY,
        ATTACK,
        IN_PLAY_GLOBAL_EFFECT,
        INSTANT,
        LEAVES_PLAY
    }        
    
    /** @brief Enum characterising all possible states of game.
     *  Game states represents real game states in game Magic the Gathering.
     *  This enumeration purpose is to determine behaviour of cards that depend on game state.
     *  For example creature can use attack only during attack step, but abilities can be used nearly anytime.
     */
    enum gameState{
        UNTAP,
        UPKEEP,
        DRAW,
        MAIN_PHASE,
        ATTACK,
        DEFENSE
    }
    
    static int enemyPlPreviewPosX;
    static int enemyPlPreviewPosY;
    static int playerPreviewPosX;
    static int playerPreviewPosY;
    static gameState state; /**< Static variable characterising state of round.*/
    static byte currentPlayer; /**< Variable storing number characterising player currently playing. By this number player can be found in .*/
    static public Map<Game.cardProperties,CreatureDecorator> propertyStorage; /**< Collection processing conversion between names of card properties and have stored pointers to them.*/
    static public Map<String,Game.cardType> cardTypeTranslator; /**< Collection processing conversion between string name from input file to enum, which represent type of creature.*/
    static public Map<String,Game.cardProperties> propertyTranslator; /**< Collection processing conversion between string name from input file to enum, which represent names of card properties.*/
    static public Map<Game.composition,JPanel> GUIComposition = new HashMap<>();/**< Collection to map GUI components. Ease access throughout the game.*/
    static public ArrayList<Exception> exceptTrack; /**< Collection of all exceptions thrown during run.*/
    static public Map<String,Game.inputTags> inputTagTranslator; /**< Collection processing conversion between string to enum mapping input tags. */
    static public Map<String,Game.boostUsabil> inpBoostUsAblTransl; /**< Collection processing conversion between string to enum mapping input attribute usAble of tag boost */
    
    /** @brief static initialiser filling all HashMaps used as dictionaries.
     *  Method define how will conversion look like. Which means, if string in input file should be changed so must be his conversion to enum definition be changed.
     *  If any new ability is created it is necessary to add it translation.
     */
    static {
        state = gameState.UNTAP;
        
        cardTypeTranslator = new HashMap<>();
        propertyTranslator = new HashMap<>();
        inputTagTranslator = new HashMap<>();
        exceptTrack = new ArrayList<>();
        inpBoostUsAblTransl = new HashMap<>();
        
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
        
        inpBoostUsAblTransl.put("inPlay", Game.boostUsabil.IN_PLAY_GLOBAL_EFFECT);
        inpBoostUsAblTransl.put("attack", Game.boostUsabil.ATTACK);
        inpBoostUsAblTransl.put("instant",Game.boostUsabil.INSTANT);
        inpBoostUsAblTransl.put("comeIntoPlay", Game.boostUsabil.COMES_INTO_PLAY);
        inpBoostUsAblTransl.put("leavesPlay", Game.boostUsabil.LEAVES_PLAY);
        
    }

}
