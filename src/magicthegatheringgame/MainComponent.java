/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author msi
 */
public class MainComponent extends JPanel{
    List<Shape> linesList = new ArrayList<>();
            Shape line = null;
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setPaint(Color.BLUE);
    	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        for(Shape content : linesList){
	            g2d.draw(content);
	        }
	      }
}
