/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

/**
 *
 * @author msi
 */
public abstract class AbilityDecorator {

    /** @brief Variable indicating, if ability is forced or optional.
     *
     */
    public boolean isForced;

    public AbilityDecorator() {
        this.isForced = false;
    }
    public void visit(Creature card){
        
    }
    public void visit(Card card){
        
    }
    public void visit(Mana card){
        
    }

}
