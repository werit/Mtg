/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author msi
 */
public class MouseAdapterMod extends MouseAdapter {
@Override
public void mousePressed(MouseEvent e) {
    Card card = (Card)e.getSource();
    card.onTap(Game.state);
    System.out.println("klikkk");
   }
}