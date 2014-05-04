/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

/**
 * This class gives creatures ability to attack in attack phase.
 * For attack in first strike attack phase use ability FirstStrike.
 */
public class Attack extends AbilityDecorator{
    @Override
    public void visit(Creature card){
        if((Game.state == Game.gameState.ATTACK || Game.state == Game.gameState.DEFENSE)&& card.isTapAble && !card.isTapped){
         if (!card.hasSummoningSickness() && Game.state.equals(Game.gameState.ATTACK) && ((card.controller.pos == Game.playerPosition.FIRST && Game.currentPlayer == 0)
                 ||(card.controller.pos == Game.playerPosition.SECOND && Game.currentPlayer == 1))){
             Battleground.fighters.put(card,Game.battlefieldStirikes.ATTACKER);
             card.isTapped = true;
         }
         else
             if(Game.state.equals(Game.gameState.DEFENSE) && ((card.controller.pos == Game.playerPosition.SECOND && Game.currentPlayer == 0)
                 ||(card.controller.pos == Game.playerPosition.FIRST && Game.currentPlayer == 1)))
                Battleground.fighters.put(card,Game.battlefieldStirikes.DEFENDER);
         
        }
    }    
}
