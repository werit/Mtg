/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.util.ArrayList;
import javax.swing.JLabel;



/**
 *
 * @author werit
 */
public class Mana extends Card{
    /**
     * 
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
    @Override
    void accept(WhiteMana ability){
        ability.visit(this);
    }
    @Override
    void accept(AbilityDecorator ability){
        ability.visit(this);
    }
    @Override
    public String toString(){
        return this.cardName;
    }
}
