/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author msi
 */
public class Battleground {
    static Map<Creature,Game.battlefieldStirikes> fighters; /*< Stores fighting creature and defines it's priority during attack.*/
    static Map<Creature,ArrayList<Creature>> blockerList; /*< Storage for attacker and creatures blocking it. Key is attacker and ArrayList stores blockers.*/
    /** Method clears battleground for next battle.
     *  Method clears HashMap :fighters and 
     */
    static void refresh(){
        assert (fighters != null);
        fighters.clear();
        blockerList.clear();
    }/**
     *  Method add attackers to blockerList and makes sure, there is ArrayList for each attacker.
     * @param attackers Array of attackers of current round.
     */
    static void addAttackers(Creature[] attackers){
        for (int i = 0; i < attackers.length; ++i) {
            blockerList.put(attackers[i],new ArrayList<Creature>());
        }
    }
    /**
     *  Method evaluating damage done by each creature to each other and in case of no blocker, then creature damage to player.
     */
    static void evaluateFight(){
        
        Iterator it = fighters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            Creature c = (Creature)pairs.getKey();
            if(((Game.battlefieldStirikes)pairs.getValue()) == Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER){
                
            }
        }

    }
    static{
        fighters = new HashMap<>();
        blockerList = new HashMap<>();
    }

    
}
