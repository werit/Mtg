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
public class Attack extends GameState{
    /** @brief Accept method of Visitor pattern.
     * Method calls visit method of Card class or her successors.
     * @param c Successors of class implementing VISIT method.
     */
    @Override void accepts(Card c) {
        c.visit(this);
    }
}