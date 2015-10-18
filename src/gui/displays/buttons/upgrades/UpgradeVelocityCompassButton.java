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
public class UpgradeVelocityCompassButton extends OneOffUpgradeButton {

    public UpgradeVelocityCompassButton(int X, int Y) {
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
        
        g2.setColor(Color.GRAY);
        g2.fillOval(x+4, y+4, w-8, h-8);
        g2.setColor(Color.BLACK);
        g2.drawOval(x+4, y+4, w-8, h-8);
        
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setColor(Color.red);
        g2.fillRect(x+w/2+1, y+5, 3, (h-10)/2);
        g2.setColor(Color.BLACK);
        g2.fillRect(x+w/2+1, y+h/2, 3, (h-10)/2);
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+Compass", x+h/2-45, y+h+15);
        g2.drawString("Cost: " + upgradeCost(), x+h/2-30-(int)((int)Math.log10(1+upgradeCost())*5.0), y+h+35);
        
    }

    @Override
    public int upgradeCost() {
        return 40;
    }

    @Override
    protected void applyUpgrade() {
        if(Scoreboard.XP() >= upgradeCost()){
            Scoreboard.modXP(-upgradeCost());
            Controller.getPlayer().setVelocityCompassState(true);
        }
    }

    @Override
    public void setUsed() {
        Controller.getPlayer().setVelocityCompassState(true);       
    }

    @Override
    public boolean wasUsed() {
        return Controller.getPlayer().velocityCompass();
    }
    
}
