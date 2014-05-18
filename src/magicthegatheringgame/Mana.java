/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import javax.swing.JLabel;



/** @brief Mana implementation of card.
 * Mana is basic source of power to summon spells in the game.
 * This class contains all common information to all mana cards there are.
 * Every mana should be customised by visitors which will define thier properties.
 * @author werit
 */
public class Mana extends Card{
    /** @brief Class constructor creates mana with picture, name and owner.
     * Constructor fills parent variables and sets basic settings of mana card.
     * For example, that card is mana, it is tapable and what is it's cost.
     */
    Mana(String cardName,Player owner,JLabel pict){
        super(pict);
        super.cardName = cardName;
        super.isTapAble = true;
        super.owner = owner;
        super.controller = owner;
        super.fileSource = pict;
        super.type = Game.cardType.LAND;
        for(Game.manaColours colour : Game.manaColours.values()){
            super.manaCosts.put(colour,0);
        }
    }
    /** @brief Visitor pattern method giving ability of white mana.
     * Mana accept WhiteMana as it's ability.
     * @param ability WhiteMana is ability to produce white mana.
     */
    @Override
    public void accept(WhiteMana ability){
        ability.visit(this);
    }
    /** @brief Default card ability decorator.
     * It's purpose is catching for unknown or not accepted abilities of mana card.
     * @param ability Unknown or not accepted ability.
     */
    @Override
    public void accept(AbilityDecorator ability){
        ability.visit(this);
    }
    /**
     * Redefinition of toString method which returns name of mana.
     * @return String representation of name of card.
     */
    @Override
    public String toString(){
        return this.cardName;
    }
}
