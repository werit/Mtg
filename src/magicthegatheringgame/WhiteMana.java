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
        card.controller.addPlain((byte)1);
        assert(card.isTapped == false);
        card.isTapped = true;
        JPanel jp = Game.GUIComposition.get(Game.composition.PLAIN_CP);
        jp.removeAll();
        jp.add(new JLabel(new Integer(card.owner.getPlainCount()).toString()));
    }
}
