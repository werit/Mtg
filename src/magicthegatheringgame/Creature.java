/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import javax.swing.JLabel;

public class Creature extends Card {
    private byte power;
    private byte toughness;

    public Creature(String cardName,Player owner,JLabel pict,int whitenCst,int blackpCst,int greenCst,int blueCst,int redCst,int colorlessCst,byte power, byte toughness) {
        super(pict);
        super.cardName = cardName;
        super.isTapAble = true;
        super.owner = owner;
        super.controller = owner;
        super.fileSource = pict;
        super.type = Game.cardType.CREATURE;
        super.manaCosts.put(Game.manaColours.PLAIN, whitenCst);
        super.manaCosts.put(Game.manaColours.SWAMP, blackpCst);
        super.manaCosts.put(Game.manaColours.FOREST, greenCst);
        super.manaCosts.put(Game.manaColours.ISLAND, blueCst);
        super.manaCosts.put(Game.manaColours.MOUNTAIN, redCst);
        super.manaCosts.put(Game.manaColours.COLORLESS, colorlessCst);
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
    @Override 
       public String toString(){
        return this.cardName + " " + this.power + "/" + this.toughness;    
    }
}
