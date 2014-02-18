/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import javax.swing.JLabel;

public class Creature extends Card {
    private byte power;
    private byte toughness;

    public Creature(String cardName,Player owner,JLabel pict,byte whitenCst,byte blackpCst,byte greenCst,byte blueCst,byte redCst,byte colorlessCst,byte power, byte toughness) {
        super(pict);
        super.cardName = cardName;
        super.isTapAble = true;
        super.owner = owner;
        super.controller = owner;
        super.fileSource = pict;
        super.type = Game.cardType.CREATURE;
        this.plainCost = whitenCst;
        this.swampCost = blackpCst;
        this.forestCost = greenCst;
        this.islandCost = blueCst;
        this.mountainCost = redCst;
        this.colorlessCost = colorlessCst;
        this.power = power;
        this.toughness = toughness;
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
    @Override
    void accept(Haste ability){
        ability.visit(this);
    }    
    @Override
    void accept(FirstStrike ability){
        ability.visit(this);
    }    

}
