/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.awt.Event;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author werit
 */
public abstract class Card extends JPanel{
   

    
    Game.cardType type;
    protected Player owner;
    protected Player controller;
    boolean isTapAble;
    Game.cardLocation cardLoc;
    protected boolean isTapped = false;
    protected String cardName;
    
    public Map<Game.boostUsabil,ArrayList<Game.cardProperties>> abilUse;
    
    byte colorlessCost;
    byte plainCost;
    byte mountainCost;
    byte islandCost;
    byte swampCost;
    byte forestCost;
    
    JLabel fileSource;
    protected Card(JLabel pict){
        fileSource = pict;
        add(pict);
        cardLoc = Game.cardLocation.IN_DECK;
    }
    void visit(Untap untap){}
    void visit(Upkeep up){}
    void visit(Draw dr){}
    void visit(MainPhase maPh){}
    void visit(Attack atck){}
    void visit(Discard dis){}
    void visit(EndOfTurn eot){}
    
    void accept(CreatureDecorator ability){
        ability.visit(this);
    }
    void accept(Haste ability){
        ability.visit(this);
    }
    void accept(FirstStrike ability){
        ability.visit(this);
    }
    /** @brief Method handling tapping of card.
     *  When executed method behaves in two directions depending on game state.
     *  If during attack, then call all anytime usable abilities and attack abilities otherwise call only anytime usable abilities.
     * @param state Parameter defining in which game state is currently game
     */
    void onTap(Game.gameState state){
        ArrayList<Game.cardProperties> abil = new ArrayList<>();
        assert (isTapAble);
        switch(state){
            case ATTACK:
                abil.addAll(abilPerBoostState(Game.boostUsabil.ATTACK));
                abil.addAll(abilPerBoostState(Game.boostUsabil.INSTANT));
                abilChoices(abil);
                break;
            default:
                abil.addAll(abilPerBoostState(Game.boostUsabil.INSTANT));
                abilChoices(abil);
                break;
        }
    }      
            
      /**
      * Method when card is cast into play.
      */
     void cardCast(){
         ArrayList<Game.cardProperties> abil;
         this.cardLoc = Game.cardLocation.IN_PLAY;
                 abil = this.abilUse.get(Game.boostUsabil.COMES_INTO_PLAY);
                 if (abil != null){
                     for(int i = 0;i < abil.size();++i){
                         Game.propertyStorage.get(abil.get(i)).visit(this);
                     }
                 }
     }
    /** @brief Method testing presence of array assigned to key given as parameter.
     *  
     * @param state Parameter carries name of key, which is to be tested.
     * @return Method returns array assigned to key or new empty array. Never null.
     */
    ArrayList<Game.cardProperties> abilPerBoostState(Game.boostUsabil state){
        if(abilUse.containsKey(state)){
            return abilUse.get(state);
        }
        else 
            return new ArrayList<>();
    }
    
    /** @brief Method deciding what to do with usable methods of card.
     *  There are three possibilities what can happen.
     *  If zero abilities is present, then card cannot be used.
     *  If one, then use that ability.
     *  If more, then choose which one.
     * @param possibleProp Array of all found abilities, that can be used during this state of game.
     */
    void abilChoices(ArrayList<Game.cardProperties> possibleProp){
        switch(possibleProp.size()){
                    case 0:
                        OUtput.errCanotTap();
                        break;
                    case 1:
                        Game.propertyStorage.get(possibleProp.get(0)).visit(this);
                        break;
                    default:
                        OUtput.addChoices(possibleProp);
                        OUtput.showChoices();
                        break;
                }
    }

}
