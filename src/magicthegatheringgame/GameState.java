/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

/** @brief Predecessor of all game states.
 *  Game states in game will work in Visitor pattern.
 *  Game states will implement ACCEPT methods and instances of Card will implement VISIT method.
 */
public abstract class GameState {
    /** @brief Accept method of Visitor pattern.
     * Guaranty that accept will be usable.
     * @param c Successors of class implementing VISIT method.
     */
    abstract void accepts(Card c);
}
