/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.util.ArrayList;
import java.util.Map;

/** @brief Interface covering all events of card.
 *  Card implements this interface.
 *  Card will describe behaviour during each of this events.
 */
abstract public class CardEvent {
    
    protected Player controller; /**< Variable containing information about controller of this card.*/
    protected Player owner; /**< Variable containing information about owner of this card.*/
    Game.cardType type; /**< Variable containing type of this card.*/
    
    /** @brief Comes into play event.
     *  Implementing card will make changes to the game depending
     *  on her properties when it comes into play.
     */
    abstract void comesIntoPlay();
    /** @brief Leaves the play event.
     *  Implementing card will make changes to the game depending
     *  on her properties when it leaves the play.
     */
    abstract void leavesThePlay();
    /** @brief Discarding card event.
     *  Implementing card will make changes to the game depending
     *  on her properties when it is discarded from the hand.
     */
    abstract void beingDiscarded();
    /** @brief Event when card is removed from the game.
     *  Implementing card will make changes to the game depending
     *  on her properties when it is removed from the game.
     */
    abstract void beingRFG();
    
    /** @brief Event when cards are untapped.
     *  Implementing class of decorator will define one action taking place during untap phase.
     */
    abstract void untapPhase();
    
    /** @brief Event during upkeep phase.
     *  Implementing class of decorator will define one action taking place during upkeep phase.
     */
    abstract void upkeepPhase();
    
    /** @brief Event when card is drawn.
     *  Implementing class of decorator will define one action taking place during draw phase..
     */
    abstract void drawPhase();
    
    /** @brief Action to be taken during attack phase.
     *  Implementing class of decorator will define one action taking place during attack phase.
     *  @param battlefield HashMap containing all attackers and defenders during this attack step.
     */    
    public abstract void attackPhase(Map<Game.battlefieldStirikes,ArrayList<CardEvent>> battlefield);
    
    /** @brief Action to be taken during discard phase.
     *  Implementing class of decorator will define one card action taking place during discard phase.
     */
    abstract void discardPhase();
    
    /** @brief Action to be taken during end of turn phase.
     *  Implementing class of decorator will define one card action taking place during end of turn phase.
     */
    abstract void endOfTurnPhase();
    
    /** @brief Action to be taken during card staying in the play.
     *  Implementing class of decorator will define one card action taking place during discard phase.
     */
    abstract void inPlay();
}
