/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import javax.swing.JLabel;
/** @brief Concrete implementation of card.
 * This card represents creature in terms of Magic: The Gathering.
 * Creature card has power and toughness and stays in the game permanently, until killed.
 * To be able to attack with creature it has to have ability to attack.
 * @author msi
 */
public class Creature extends Card {
    private int power;
    private int toughness;
    private int damageReceived;
    private int basePower;
    private int baseToughness;
    private boolean summoningSickness; /*< Indicator if creature can use it's abilities.*/
/** @brief creature constructor. Calls parent constructor and set's basic properties of creature card.
 * Set all variables given as parameter, sets summoning sickness, power, toughness and colour costs.
 * @param cardName Name of card.
 * @param owner Player who owns card.
 * @param pict Card picture.
 * @param whitenCst White mana colour cost.
 * @param blackpCst Black mana colour cost.
 * @param greenCst Green mana colour cost.
 * @param blueCst Blue mana colour cost.
 * @param redCst Red mana colour cost.
 * @param colorlessCst Colourless mana colour cost.
 * @param power Creature's power.
 * @param toughness Creature's toughness.
 */
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
        this.summoningSickness = true;
    }
    /** @brief Method overrides parent :refreshCard method.
     * Method calls parent method and refreshes power and toughness of creature. Usually at the end of turn.
     */
    @Override
    public void refreshCard(){
        super.refreshCard();
        power = basePower;
        toughness = baseToughness;
    }
    /**
     * Method used to clear summoning sickness from creature.
     */
    public void setNoSummoningSickness(){
        this.summoningSickness = false;
    }
    /**
     * Method separating inside implementation from user main control.
     * @return Return true, if creature has summoning sickness. Otherwise false.
     */
    public boolean hasSummoningSickness(){
            return this.summoningSickness;
    }
    /** Method separating inside implementation from user main control of power of creature.
     * Creature's power will be increased by passed parameter.
     * @param addition Positive number will increase power of creature and negative will decrease.
     */
    public void setPower(byte addition){
        this.power += addition;
    }
    /** Method separating inside implementation from user main control of getting power of creature.
     * Method that knows actual amount of creature's power.
     * @return Return value is actual creature's power.
     */
    public int getPower(){
        return this.power;
    }
    /** Method separating inside implementation from user main control of toughness of creature.
     * Creature's toughness will be increased by passed parameter.
     * @param addition Positive number will increase toughness of creature and negative will decrease.
     */
    public void setToughness(byte addition){
        this.toughness += addition;
    }
    /** Method separating inside implementation from user main control of getting toughness of creature.
     * Method that knows actual amount of creature's toughness.
     * @return Return value is actual creature's toughness.
     */
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
    /** @brief Visitor method for accepting unknown ability.
     * This method catches unknown ability passed as parameter for creature card.
     * Call of this method is error.
     * @param ability Unknown ability or ability not accepted by creature class. 
     */
    @Override
    public void accept(AbilityDecorator ability){
        ability.visit(this);
    }
    /** @brief Visitor pattern method for Haste ability.
     * Method accept Haste as ability of creature.
     * @param ability Haste ability, that removes summoning sickness.
     */
    @Override
    public void accept(Haste ability){
        ability.visit(this);
    } 
     /** @brief Visitor pattern method for FirstStrike ability.
     * Method accept FirstStrike as ability of creature.
     * @param ability FirstStrike ability, that allows creature to attack in first attack phase.
     */
    @Override
    public void accept(FirstStrike ability){
        ability.visit(this);
    }  
    /** @brief Visitor pattern method for Attack ability.
     * Method accept Attack as ability of creature.
     * @param ability Attack ability, grants creature ability to attack in normal attack phase..
     */
    @Override
    public void accept(Attack ability){
        ability.visit(this);
    } 
    /** @brief Method overriding toString method.
     * 
     * @return Method return name of creature and it's current power and toughness.
     */
    @Override 
       public String toString(){
        return this.cardName + " " + this.power + "/" + this.toughness;    
    }
}
