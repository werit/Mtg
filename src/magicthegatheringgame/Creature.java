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
public class Creature extends Card {
    @Override void onTap(){
        
    }
    
    private byte power;
    private byte toughness;

    public Creature(String cardName,Player owner,JLabel pict,byte whitenCst,byte blackpCst,byte greenCst,byte blueCst,byte redCst,byte colorlessCst,byte power, byte toughness) {
        super.cardName = cardName;
        super.isTapAble = true;
        this.owner = owner;
        this.controller = owner;
        this.plainCost = whitenCst;
        this.swampCost = blackpCst;
        this.forestCost = greenCst;
        this.islandCost = blueCst;
        this.mountainCost = redCst;
        this.colorlessCost = colorlessCst;
        this.power = power;
        this.toughness = toughness;
        this.fileSource = pict;
        this.type = Game.cardType.CREATURE;
        
    }

    public void setPower(byte addition){
        this.power += addition;
    }
    public byte getPower(){
        return this.power;
    }
    @Override
    void accept(CreatureDecorator ability){
        ability.visit(this);
    }
    
}
