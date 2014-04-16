/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import javax.swing.JLabel;

public class Creature extends Card {
    private int power;
    private int toughness;
    private int damageReceived;
    private int basePower;
    private int baseToughness;

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
        this.basePower = power;
        this.baseToughness = toughness;
    }
    @Override
    public void refreshCard(){
        super.refreshCard();
        power = basePower;
        toughness = baseToughness;
    }
    public void setPower(byte addition){
        this.power += addition;
    }
    public int getPower(){
        return this.power;
    }
    public void setToughness(byte addition){
        this.toughness += addition;
    }
    public int getToughness(){
        return this.toughness;
    }
    /**
     * Method handling damage dealt to creature.
     * @param damage Damage received by creature. Damage received is positive number, healing is negative.
     */
    public void damageReceived(int damage){
        this.damageReceived += damage;
        this.toughness -= damage;
    }
    /**
     * Method called at the end of the turn, resets creature to proper state.
     */
    public void endOfTurn(){
        this.toughness += damageReceived;
        damageReceived = 0;
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
