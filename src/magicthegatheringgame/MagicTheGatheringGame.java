/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicthegatheringgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author werit
 */
public class MagicTheGatheringGame {

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    
    static JLabel myHp = new JLabel("Your lifes : 20");
    static JLabel enemyHp = new JLabel("Enymy lifes :20");
    static JLabel colorlessMana = new JLabel("colorless mana");
    static JLabel plainMana = new JLabel("plain mana");
    static JLabel mountainMana = new JLabel("mountain mana");
    static JLabel islandMana = new JLabel("island mana");
    static JLabel swampMana = new JLabel("swamp mana");
    static JLabel forestMana = new JLabel("forest mana");

    
    /**@brief Create main JFrame which will contain all components.
     * 
     */
    private static void createAndShowGui(){
        JFrame frame = new JFrame("Magic the Gathering");
        MainComponent jp = new MainComponent();
        
        frame.add(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Data data = new Data();
        //data.addComponentsToPane(frame.getContentPane());
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
            public void run(){
                createAndShowGui();
            }
        });
    }
}
