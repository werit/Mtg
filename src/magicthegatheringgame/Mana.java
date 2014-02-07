/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.util.ArrayList;
import java.util.Map;



/**
 *
 * @author werit
 */
public class Mana extends Card{
    /**
     * probably no need to create middle class Mana
     * nonetheless I made it to show that manas have some special rules applied to them
     */
    /*
     in case of creating new type of mana, simply inherit from mana and override void onTap(Player pl) method
     */
    Mana(){
        colorlessCost = 0;
        plainCost = 0;
        mountainCost = 0;
        islandCost = 0;
        swampCost = 0;
        forestCost = 0;
    }
}
