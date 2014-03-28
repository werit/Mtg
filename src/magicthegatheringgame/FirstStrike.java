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
        if((Game.state == Game.gameState.ATTACK || Game.state == Game.gameState.DEFENSE)&& card.isTapAble && !card.isTapped){
         if (Game.state.equals(Game.gameState.ATTACK) && ((card.controller.pos == Game.playerPosition.FIRST && Game.currentPlayer == 0)
                 ||(card.controller.pos == Game.playerPosition.SECOND && Game.currentPlayer == 1))){
             Battleground.fighters.put(card,Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER);
             card.isTapped = true;
         }
         else
             if(Game.state.equals(Game.gameState.DEFENSE) && ((card.controller.pos == Game.playerPosition.SECOND && Game.currentPlayer == 0)
                 ||(card.controller.pos == Game.playerPosition.FIRST && Game.currentPlayer == 1)))
                Battleground.fighters.put(card,Game.battlefieldStirikes.FIRST_STRIKE_DEFENDER);
         
        }
    }
}
