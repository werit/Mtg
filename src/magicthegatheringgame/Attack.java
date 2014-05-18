/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

/** @brief redefinition of visitor pattern. 
 * This class accepts only creature and gives it ability to attack.
 * 
 * This class gives creatures ability to attack in attack phase.
 * For attack in first strike attack phase use ability FirstStrike.
 */
public class Attack extends AbilityDecorator{
    /**
     * Method of visitor pattern.
     * 
     * Overrides AbilityDecorator:visit method with parameter Creature, therefore usable on creatures.
     * 
     * This method gives creatures ability to attack in attack phase.
     * @param card Parameter is card with creature type. Card:type == Game.cardType.CREATURE
     */
    @Override
    public void visit(Creature card){
        // Check if attack is possible
        if((Game.state == Game.gameState.ATTACK || Game.state == Game.gameState.DEFENSE)&& card.isTapAble && !card.isTapped){
         if (!card.hasSummoningSickness() && Game.state.equals(Game.gameState.ATTACK) && ((card.controller.pos == Game.playerPosition.FIRST && Game.currentPlayer == 0)
                 ||(card.controller.pos == Game.playerPosition.SECOND && Game.currentPlayer == 1))){
             Battleground.fighters.put(card,Game.battlefieldStirikes.ATTACKER);
             card.isTapped = true;
         }
         else
             // check if defend is possible
             if(Game.state.equals(Game.gameState.DEFENSE) && ((card.controller.pos == Game.playerPosition.SECOND && Game.currentPlayer == 0)
                 ||(card.controller.pos == Game.playerPosition.FIRST && Game.currentPlayer == 1)))
                Battleground.fighters.put(card,Game.battlefieldStirikes.DEFENDER);
         
        }
    }    
}
