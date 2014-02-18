/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.util.ArrayList;
import java.util.Map;
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
        this.colorlessCost = 0;
        this.plainCost = 0;
        this.mountainCost = 0;
        this.islandCost = 0;
        this.swampCost = 0;
        this.forestCost = 0;
    }
}
