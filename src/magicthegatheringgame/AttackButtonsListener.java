/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author msi
 */
public class AttackButtonsListener extends MouseAdapter{
    private int index;
    private ArrayList<Creature> blockers;
    private Creature[] attackers;
    private Map<Creature,JCheckBox> blockCheck;
    private final JButton contin;
    private JPanel panel;
    private JFrame oldFrame;
    public AttackButtonsListener(JFrame oldFrame){
        this.index = 0;
        this.oldFrame = oldFrame;
        blockCheck = new HashMap<>();
        this.contin = new JButton("Continue!");
        contin.addMouseListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e){
        for (Map.Entry pairs : blockCheck.entrySet()) {
            Creature c = (Creature)pairs.getKey();
            if(((JCheckBox)pairs.getValue()).isSelected()){
                blockers.remove(c);
                Battleground.blockerList.get(attackers[index]).add(c); 
                ArrayList<Game.cardProperties> abil;
                abil = c.abilUse.get(Game.boostUsabil.ATTACK);
                if (abil != null){
                    for(int i = 0;i < abil.size();++i){
                        c.accept(Game.propertyStorage.get(abil.get(i)));
                    }
                }
            }
        }
        for (int i = panel.getComponentCount() - 1; i > 1 ; --i) {
            panel.remove(i);
        }
        blockCheck.clear();
        ++index;

        if( blockers.isEmpty()){ 
            // no more blockers,evaluate damage of attackers
            index = attackers.length;
        }
        if(attackers.length == index){
            ((JFrame)SwingUtilities.getWindowAncestor(panel)).dispose();
            this.oldFrame.setEnabled(true);
            this.oldFrame.setVisible(true);
        }
        else
            // There were some blockers left, otherwise this phase would end
            chooseBlockers();
            
        panel.revalidate();
        panel.repaint();
    }
    public void refreshData(Creature[] attackers,ArrayList<Creature> blockers,JPanel panel){
        this.index = 0;
        this.blockers = blockers;
        this.attackers = attackers;
        this.panel = panel;
        Battleground.addAttackers(attackers);
    }
    public void chooseBlockers(){
        if (attackers.length > index){
            if(blockers.size()>0){
                // creature to be blocked
                panel.add(new JLabel("Who will you block '" + attackers[index].toString() + "'with ?"),BorderLayout.PAGE_START);   
                // possible blockers
                JCheckBox cb;
                for(Creature crea : blockers){
                    cb = new JCheckBox(crea.toString(), false);
                    blockCheck.put(crea, cb);
                    panel.add(cb);
                }
               panel.add(contin,BorderLayout.PAGE_END);
            }
        }
    }
}
