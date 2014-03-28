/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author msi
 */
public class Battleground {
    static Map<Creature,Game.battlefieldStirikes> fighters;
    static void refresh(){
        assert (fighters != null);
        fighters.clear();
    }
    static{
        fighters = new HashMap<>();
    }

    
}
