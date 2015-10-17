/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons.upgrades;

import gui.Controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class UpgradeSpeedCapacityButton extends UpgradeButton {

    public UpgradeSpeedCapacityButton(int X, int Y) {
        super(X, Y);
    }

    @Override
    public void drawLogo(Graphics g, boolean selected) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        if(selected) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, w, h);
        }
        
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, w, h);
        
        
        g2.drawLine(x+2*w/5, y+w/5, x+2*w/5, y+4*w/5);
        g2.drawLine(x+w/5, y+h/2, x+2*w/5, y+h/2);
        
        g2.drawLine(x+3*w/5, y+h/5, x+3*w/5, y+4*h/5);
        g2.drawLine(x+3*w/5, y+h/2, x+4*w/5, y+h/2);
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+Capacity", x+h/2-45, y+h+15);
        g2.drawString("Cost: " + upgradeCost(), x+h/2-35-(int)((int)Math.log10(1+upgradeCost())*5.0), y+h+35);
        
    }

    @Override
    public int upgradeCost() {
        return (int)(Math.sqrt(1+Controller.getPlayer().capacitanceUpgradeLevel()*(Controller.getPlayer().getChargeCapacity()+1)) * 20);
    }

    @Override
    protected void applyUpgrade() {
        
    }
    
}
