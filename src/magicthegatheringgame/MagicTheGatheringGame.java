/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import javax.swing.JFrame;

/**
 *
 * @author werit
 */
public class MagicTheGatheringGame {
    
    /**@brief Create main JFrame which will contain all components.
     * 
     */
    private static void createAndShowGui(){
        JFrame frame = new JFrame("Magic the Gathering");
        MainComponent jp = new MainComponent();
        
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
