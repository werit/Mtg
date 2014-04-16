/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author werit
 */
public abstract class Card extends JPanel{
   

    
    Game.cardType type;
    protected Player owner;
    protected Player controller;
    boolean isTapAble;
    Game.cardLocation cardLoc;
    protected boolean isTapped = false;
    protected String cardName;
    
    public Map<Game.boostUsabil,ArrayList<Game.cardProperties>> abilUse;
    
    Map<Game.manaColours,Integer> manaCosts; /**< Variable storing all mana cost per every color.*/
    /*byte colorlessCost;
    byte plainCost;
    byte mountainCost;
    byte islandCost;
    byte swampCost;
    byte forestCost;*/
    
    JLabel fileSource;
    protected Card(JLabel pict){
        fileSource = pict;
        add(pict);
        cardLoc = Game.cardLocation.IN_DECK;
        manaCosts = new HashMap<>();
    }
    
    void accept(CreatureDecorator ability){
        ability.visit(this);
    }
    void accept(Haste ability){
        ability.visit(this);
    }
    void accept(FirstStrike ability){
        ability.visit(this);
    }
    void accept(WhiteMana ability){
        ability.visit(this);
    }
    /** @brief Method handling tapping of card.
     *  When executed method behaves in two directions depending on game state.
     *  If during attack, then call all anytime usable abilities and attack abilities otherwise call only anytime usable abilities.
     * @param state Parameter defining in which game state is currently game
     */
    void onUse(Game.gameState state){
        ArrayList<Game.cardProperties> abil = getAbilPerState(state);
        abil.addAll(abilPerBoostState(Game.boostUsabil.INSTANT));
        abilChoices(abil);
    }
    /** @brief Method processing all automatically evoked abilities during game state 
     * 
     * @param state State characterising actual phase of Magic the Gathering
     */
    public void gameStateEvokeAbil(Game.gameState state){
        ArrayList<Game.cardProperties> abil = getAbilPerState(state);
        CreatureDecorator cd;
        
        for (int i = 0; i < abil.size(); ++i) {
            cd = Game.propertyStorage.get(abil.get(i));
            assert (cd != null);
            if(cd.isForced)
                cd.visit(this);
        }
    }
    /**
     * I do not know how does it work....
     * @param g 
     */
    
    @Override
    public void paintComponent(Graphics g){
        if(this.isTapped){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if((this.getWidth()) < this.getHeight())
            this.setSize(new Dimension(this.getHeight(), this.getWidth()));   
        g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
        g2d.rotate(Math.PI/2);
        g2d.translate(-this.getHeight() / 2, -this.getWidth() / 2); 
        g2d.drawImage(new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB), 0, 0, null);
        }
        else
            super.paintComponent(g);
    }
    
    private ArrayList<Game.cardProperties> getAbilPerState(Game.gameState state){
        switch(state){
            case UNTAP:
                return abilPerBoostState(Game.boostUsabil.UNTAP);
            case UPKEEP:
                return abilPerBoostState(Game.boostUsabil.UPKEEP);
            case ATTACK:
                return abilPerBoostState(Game.boostUsabil.ATTACK);
            case DEFENSE:
                return abilPerBoostState(Game.boostUsabil.DEFENSE);
            default:
                return new ArrayList<>();
        }
    }
            
      /**
      * Method when card is cast into play.
      */
     void cardCast(){
        ArrayList<Game.cardProperties> abil;
        this.cardLoc = Game.cardLocation.IN_PLAY;
        abil = this.abilUse.get(Game.boostUsabil.COMES_INTO_PLAY);
        if (abil != null){
            for(int i = 0;i < abil.size();++i){
                Game.propertyStorage.get(abil.get(i)).visit(this);
            }
        }
        this.controller.inPlayCard.add(this);
        this.controller.hand.remove(this);
     }
    /** @brief Method testing presence of array assigned to key given as parameter.
     *  
     * @param state Parameter carries name of key, which is to be tested.
     * @return Method returns array assigned to key or new empty array. Never null.
     */
    ArrayList<Game.cardProperties> abilPerBoostState(Game.boostUsabil state){
        if(abilUse.containsKey(state)){
            return abilUse.get(state);
        }
        else 
            return new ArrayList<>();
    }
    
    /** @brief Method deciding what to do with usable methods of card.
     *  There are three possibilities what can happen.
     *  If zero abilities is present, then card cannot be used.
     *  If one, then use that ability.
     *  If more, then choose which one.
     * @param possibleProp Array of all found abilities, that can be used during this state of game.
     */
    void abilChoices(ArrayList<Game.cardProperties> possibleProp){
        switch(possibleProp.size()){
                    case 0:
                        OUtput.errCanotTap();
                        break;
                    case 1:
                        this.accept(Game.propertyStorage.get(possibleProp.get(0)));
                        break;
                    default:
                        OUtput.addChoices(possibleProp);
                        OUtput.showChoices();
                        break;
                }
    }
    public void refreshCard(){
        if(isTapAble)
            isTapped = false;
    }
}
