/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import javax.swing.JLabel;
import javax.swing.JPanel;

/** @brief Class handling adding one white mana to mana pool of controller.
 * @author werit
 */
public class WhiteMana extends CreatureDecorator {
    
    /** @brief Method adding white mana.
     *  Method access controller of card and add one white mana to his mana pool and tap the card.
     * @param card Tapped card with ability of adding white mana.
     */
    @Override
    public void visit(Mana card){
        if(card.isTapAble && !card.isTapped){
            card.controller.addMana(Game.manaColours.PLAIN,1);
            assert(card.isTapped == false);
            card.isTapped = true;
            Game.composition destPlain;
            if(card.controller.pos == Game.playerPosition.FIRST){
                destPlain = Game.composition.PLAIN_CP;
            }
            else
                destPlain = Game.composition.PLAIN_OP;
            JPanel jp = Game.GUIComposition.get(destPlain);
            jp.removeAll();
            jp.add(new JLabel(new Integer(card.owner.getManaCount(Game.manaColours.PLAIN)).toString()));
        }
    }
}
