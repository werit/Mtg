/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

/**
 * Class of visitor pattern.
 * All other visitors classes should extend this class.
 * In case of expanding for new types of card, each and every have to have public method visiting that type of card.
 * 
 */
public abstract class AbilityDecorator {

    /** @brief Variable indicating, if ability is forced or optional.
     * 
     *  Determines if this ability must be used or usage is optional and therefore evoked by player, not be game.
     */
    public boolean isForced;
    /**
     * Constructor setting :isForced variable to false by default.
     */
    public AbilityDecorator() {
        this.isForced = false; 
    }
    /**
     * Method of Visitor pattern. 
     * Method is supposed to be overridden by descendant if it accepts Creature.   
     * @param card Parameter is card with creature type. Card:type == Game.cardType.CREATURE
     */
    public void visit(Creature card){
        
    }
    /**
     * Method of Visitor pattern. 
     * Not supposed to be used. Only time used when undefined Game:cardType was passed as parameter.
     * @param card Card with unknown type in Card:type. 
     */
    public void visit(Card card){
        
    }
    /**
     * Method of Visitor pattern. 
     * Method is supposed to be overridden by descendant if it accepts Land card.   
     * @param card Parameter is card with creature type. Card:type == Game.cardType.LAND
     */
    public void visit(Mana card){
        
    }

}
