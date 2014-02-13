/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

/** @brief First game states.
 *  Class stores all methods and data needed during this state of game.
 */
public class EndOfTurn extends GameState{
    
    /** @brief Accept method of Visitor pattern.
     * Method calls visit method of Card class or her successors.
     * @param c Successors of class implementing VISIT method.
     */
    @Override void accepts(Card c) {
        c.visit(this);
    }
}

