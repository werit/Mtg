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
    private static Player defPL;
    static Map<Creature,Game.battlefieldStirikes> fighters; /*< Stores fighting creature and defines it's priority during attack.*/
    static Map<Creature,ArrayList<Creature>> blockerList; /*< Storage for attacker and creatures blocking it. Key is attacker and ArrayList stores blockers.*/
    /** Method clears battleground for next battle.
     *  Method clears HashMap :fighters and 
     */
    static void refresh(){
        assert (fighters != null);
        assert(blockerList != null);
        fighters.clear();
        blockerList.clear();
        defPL = null;
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
     * Method to set who defend. 
     * @param defendingPl Player who's creatures are defending.
     */
    static void setDefPlayer(Player defendingPl){
        defPL = defendingPl;
    }
    /**
     * Method evaluating damage done by each creature to each other and in case of no blocker, then creature damage to player.
     * @param strike Can be FIRST_STRIKE_ATTACKER or ATTACKER, but method check only for FIRST_STRIKE_ATTACKER, 
     * 
     * if so then it is first strike phase otherwise it is normal strike phase.
     */
    static void evaluateFight(Game.battlefieldStirikes strike){
        assert(defPL != null);
        Game.battlefieldStirikes att;
        Game.battlefieldStirikes def;
        if(strike == Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER){
            att = Game.battlefieldStirikes.FIRST_STRIKE_ATTACKER;
            def = Game.battlefieldStirikes.FIRST_STRIKE_DEFENDER;
        }
        else{
            att = Game.battlefieldStirikes.ATTACKER;
            def = Game.battlefieldStirikes.DEFENDER;
        }
        for (Map.Entry pairs : blockerList.entrySet()) {
            Creature c = (Creature)pairs.getKey();
            ArrayList<Creature> blockers = (ArrayList<Creature>)pairs.getValue();
            if(fighters.get(c).equals(att) && c.getToughness() > 0){
                if(blockers.size() > 0){
                    int damageLeft = c.getPower();
                    int indexDef = 0;
                    while(damageLeft > 0){
                        if(indexDef < blockers.size()){
                            damageLeft = dealDamage(damageLeft,blockers.get(indexDef));
                            ++indexDef;
                        }
                        else
                            break;
                    }  
                }
                else{
                    defPL.subtractLifes(c.getPower());
                }
            }
            
            for(int i = 0; i < blockers.size(); ++i){
                if(fighters.get(blockers.get(i)).equals(def) && blockers.get(i).getToughness() > 0){
                    dealDamage(blockers.get(i).getPower(), c);
                }
            }
        }
    }
    static{
        fighters = new HashMap<>();
        blockerList = new HashMap<>();
    }
    /**
     * Method processing damage dealt to another creature
     * @param attactkerPower Power of attacking creature
     * @param defender Creature that defend versus attacker's power.
     * @return Damage left to deal to another creatures. It is damage that left after killing defending creature.
     */
    private static  int dealDamage(int attactkerPower,Creature defender){
        if(attactkerPower >= defender.getToughness()){
            int toughnesBeforDeath = defender.getToughness();
            defender.damageReceived(defender.getToughness());
           return attactkerPower - toughnesBeforDeath;
        }
        else
        {
            defender.damageReceived(attactkerPower);
            return 0;
        }
    } 
}
