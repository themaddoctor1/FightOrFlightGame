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
public class UpgradeSpeedButton extends UpgradeButton {

    public UpgradeSpeedButton(int X, int Y) {
        super(X, Y);
    }

    @Override
    public void drawLogo(Graphics g, boolean selected) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        if(selected) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, w, h);
        }
        
        for(int i = 0; i < 2; i++){
            Polygon p = new Polygon();
            p.addPoint(x+w/2 + i*w/3, y+w/2);
            p.addPoint(x+w/6 + i*w/3, y+w/3);
            p.addPoint(x+w/6 + i*w/3, y+2*w/3);
            g2.setColor(Color.YELLOW);
            g2.fillPolygon(p);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(p);
        }
        
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, w, h);
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+Speed", x+h/2-30, y+h+15);
        g2.drawString("Cost: " + upgradeCost(), x+h/2-35-(int)((int)Math.log10(1+upgradeCost())*5.0), y+h+35);
        
    }

    @Override
    public int upgradeCost() {
        
        double factor = 1 + Controller.getPlayer().getChargeCapacity();
        
        return (int)(50 * (Math.cbrt(factor)*Math.log10(10*factor)));
    }

    @Override
    protected void applyUpgrade() {
        if(Scoreboard.XP() >= upgradeCost()){
            Scoreboard.modXP(-upgradeCost());
            Controller.getPlayer().levelUpSpeed(1);
        }
    }
    
}
