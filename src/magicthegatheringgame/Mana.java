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
