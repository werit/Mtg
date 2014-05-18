/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** @brief Class handling storing attacker and blockers and also their evaluating their fights.
 * Class containing all attackers and blockers depending on their strike phase abilities.
 * Class handles dealing with dead creatures.
 * @author msi
 */
public class Battleground {
    private static Player defPL;
    public static Map<Creature,Game.battlefieldStirikes> fighters; /*< Stores fighting creature and defines it's priority during attack.*/
    public static Map<Creature,ArrayList<Creature>> blockerList; /*< Storage for attacker and creatures blocking it. Key is attacker and ArrayList stores blockers.*/
    /** Method clears battleground for next battle.
     *  Method clears HashMap :fighters and 
     */
    public static void refresh(){
        assert (fighters != null);
        assert(blockerList != null);
        fighters.clear();
        blockerList.clear();
        defPL = null;
    }
    /**
     * Method returning all attacking creatures, which have to be removed from battleground.
     * If removing attackers, it is necessary to call method :deadBlockers, which returns dead blockers, first.
     * Otherwise you will lose link to those dead blockers and therefore you cannot remove them.
     * Method also removes dead creatures from list.
     * @return Returns array of attackers to be removed(dead attackers). Never return null.
     */
    public static ArrayList<Creature> deadAttackers(){
        ArrayList<Creature> att = new ArrayList<>();
        for(Creature crea :blockerList.keySet()){
            if((crea).getToughness() <= 0){ //dead attacker
                att.add(crea);
            }
        }
        for(Creature crea : att)
            blockerList.remove(crea);
        return att;
        
    }/**
     * Method returning list of all dead defending creatures.
     * Method also removes dead creatures from list.
     * @return returns always ArrayList of dead defending creatures, never null.
     */
    public static ArrayList<Creature> deadBlockers(){
        ArrayList<Creature> def = new ArrayList<>();
        for(ArrayList<Creature> val :blockerList.values()){
            for (int i = val.size()-1; i > -1 ; --i) {
                if(val.get(i).getToughness() <= 0){ // dead defender
                    def.add(val.get(i));
                    val.remove(val.get(i));
                }
            }
        }
        return def;
    }
    /**
     *  Method add attackers to blockerList and makes sure, there is ArrayList for each attacker.
     * @param attackers Array of attackers of current round.
     */
    public static void addAttackers(Creature[] attackers){
        for (int i = 0; i < attackers.length; ++i) {
            blockerList.put(attackers[i],new ArrayList<Creature>());
        }
    }
    /**
     * Method to set who defend. 
     * @param defendingPl Player who's creatures are defending.
     */
    public static void setDefPlayer(Player defendingPl){
        defPL = defendingPl;
    }
    /**
     * Method evaluating damage done by each creature to each other and in case of no blocker, then creature damage to player.
     * @param strike Can be FIRST_STRIKE_ATTACKER or ATTACKER, but method check only for FIRST_STRIKE_ATTACKER, 
     * 
     * if so then it is first strike phase otherwise it is normal strike phase.
     */
    public static void evaluateFight(Game.battlefieldStirikes strike){
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
            Creature attackingCreature = (Creature)pairs.getKey();
            ArrayList<Creature> blockers = (ArrayList<Creature>)pairs.getValue();
            if(fighters.get(attackingCreature).equals(att) && attackingCreature.getToughness() > 0){
                if(blockers.size() > 0){
                    int damageLeft = attackingCreature.getPower();
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
                    defPL.subtractLifes(attackingCreature.getPower());
                }
            }
            // iterate over blockers
            for(int i = 0; i < blockers.size(); ++i){
                // if it is blocker's time to block

                if(fighters.get(blockers.get(i)).equals(def)){
                    dealDamage(blockers.get(i).getPower(), attackingCreature);
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
