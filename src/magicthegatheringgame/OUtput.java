/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.util.ArrayList;

/**
 *
 * @author msi
 */
public class OUtput {
    private static ArrayList<Game.cardProperties> choices;
    public static void addChoices(ArrayList<Game.cardProperties> choices){
        for(int i = 0; i < choices.size();++i){
            OUtput.choices.add(choices.get(i));
        }
        
    }
    
    /** @brief Method showing all possible choices of 
     * 
     */
    public static void showChoices(){
        
    }
    public static void errCanotTap(){
        
    }
    public static void lostTheGame(){
        
    }
    static {
        choices = new ArrayList<>();
    }
}
