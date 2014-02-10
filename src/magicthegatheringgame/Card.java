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
public abstract class Card{
    Game.cardType type;
    protected Player owner;
    protected Player controller;
    boolean isTapAble;
    protected boolean isTapped = false;
    protected String cardName;
    
    public Map<Game.boostUsabil,ArrayList<Game.cardProperties>> abilUse;
    
    byte colorlessCost;
    byte plainCost;
    byte mountainCost;
    byte islandCost;
    byte swampCost;
    byte forestCost;
    
    void visit(Untap untap){}
    void visit(Upkeep up){}
    void visit(Draw dr){}
    void visit(MainPhase maPh){}
    void visit(Attack atck){}
    void visit(Discard dis){}
    void visit(EndOfTurn eot){}
    
    void accept(CreatureDecorator ability){
        ability.visit(this);
    }
    void accept(Haste ability){
        ability.visit(this);
    }
    void accept(FirstStrike ability){
        ability.visit(this);
    }
    
    abstract void onTap(Game.gameState state);
    JLabel fileSource;
}
