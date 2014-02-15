/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

/** @brief Add/move attacker to array of first strikers.
     *  First strike grants creature ability to strike earlier then creature without first strike.
     *  For this reason there is specially designed array contained in class Player. 
     * 
     * Player#creatures with Game::creatureBattlePossib::FIRSTSTRIKE
*/
public class FirstStrike extends CreatureDecorator{
    @Override
    public void visit(Creature card){
        assert(card.isTapAble == true && card.isTapped == false);
         if (Game.state.equals(Game.gameState.ATTACK))
             Battleground.fighters.get(Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER).add(card);
         else
             Battleground.fighters.get(Game.battlefieldStirikes.FIRST_STRIKE_DEFENDER).add(card);
         card.isTapped = true;
    }
}
