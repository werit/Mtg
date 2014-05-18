/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.util.ArrayList;

/** @brief Class serving for communication with player in case of multiple possibilities of abilities.
 * There is possibility, that card has more then one activated ability, that can player use at the time.
 * Method of this class handles those choices.
 * Currently not used due limit of single ability.
 * @author msi
 */
public class OUtput {
    private static ArrayList<Game.cardProperties> choices;
    /**
     * Method used for summing of usable choices of card.
     * @param choices Parameter represents set of choices from player can choose.
     */
    public static void addChoices(ArrayList<Game.cardProperties> choices){
        for(int i = 0; i < choices.size();++i){
            OUtput.choices.add(choices.get(i));
        }
        
    }
    
    /** @brief Method showing all possible choices of abilities.
     * Method show possible choices to player thru GUI.
     */
    public static void showChoices(){
        
    }
    /** @brief Method shows warning that card cannot be tapped.
     * Method created dialog, that warns player that he cannot tap/use card.
     */
    public static void errCanotTap(){
        
    }
}
