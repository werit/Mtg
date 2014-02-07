/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.util.ArrayList;
import java.util.Map;

/** @brief Add/move attacker to array of first strikers.
     *  First strike grants creature ability to strike earlier then creature without first strike.
     *  For this reason there is specially designed array contained in class Player. 
     * 
     * Player#creatures with Game::creatureBattlePossib::FIRSTSTRIKE
*/
public class FirstStrike extends CreatureDecorator{
    @Override
    public void visit(Creature card){
         assert(Game.currentPlayer != null);
         if (card.controller == Game.currentPlayer)
             Battleground.fighters.get(Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER).add(card);
         else
             Battleground.fighters.get(Game.battlefieldStirikes.FIRST_STRIKE_DEFENDER).add(card);
    }
}
