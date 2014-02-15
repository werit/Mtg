/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

/** @brief Class of decorator design patter. Granting haste ability to creature.
 *  Haste class add creature ability to attack or use abilities this turn.
 */
public class Haste extends CreatureDecorator{
    /** @brief Method of visitor pattern handling haste ability of creature.
     *  Method calls method of player handling creatures which can use abilities immedietally.
     * @param card Card with this ability.
     */
    @Override
    public void visit(Creature card){
        card.controller.addAttackerAndDef(card);
    }
}
