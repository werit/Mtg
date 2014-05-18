/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** @brief Main class for game Magic: The Gathering.
 * contains main method which calls run method of new Runnable.
 * @author werit
 */
public class MagicTheGatheringGame {
    
    /** @brief Method called by run method of Runnable.
     * Method creates new frame where game will be held and creates arbiter class which handles all events of the game.
     */
    private static void createAndShowGui(){
        JFrame frame = new JFrame("Magic the Gathering");
        JPanel jp = new JPanel();
        
        frame.add(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Arbiter arb = new Arbiter(jp);
        Game.mousLis = arb;
        arb.arbitGame();
        frame.pack();
        // maximalize to whole screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        

        frame.revalidate();
        frame.repaint(); 
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                createAndShowGui();
            }
        });
    }
}
