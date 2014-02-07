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
    static Map<Game.battlefieldStirikes,ArrayList<Creature>> fighters;
    static void refresh(){
        assert (fighters != null);
        assert (fighters.get(Game.battlefieldStirikes.ATTACKER) != null);
        assert (fighters.get(Game.battlefieldStirikes.DEFENDER) != null);
        assert (fighters.get(Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER) != null);
        assert (fighters.get(Game.battlefieldStirikes.FIRST_STRIKE_DEFENDER) != null);
        
        fighters.get(Game.battlefieldStirikes.ATTACKER).clear();
        fighters.get(Game.battlefieldStirikes.DEFENDER).clear();
        fighters.get(Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER).clear();
        fighters.get(Game.battlefieldStirikes.FIRST_STRIKE_DEFENDER).clear();
    }
    static{
        fighters = new HashMap<>();
        fighters.put(Game.battlefieldStirikes.ATTACKER, new ArrayList<Creature>());
        fighters.put(Game.battlefieldStirikes.DEFENDER, new ArrayList<Creature>());
        fighters.put(Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER, new ArrayList<Creature>());
        fighters.put(Game.battlefieldStirikes.FIRST_STRIKE_DEFENDER, new ArrayList<Creature>());
    }

    
}
