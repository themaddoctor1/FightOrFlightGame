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
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class UpgradeHpButton extends UpgradeButton {

    public UpgradeHpButton(int X, int Y) {
        super(X, Y);
    }

    @Override
    public void drawLogo(Graphics g, boolean selected) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        if(selected) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, w, h);
        }
        
        g2.setColor(Color.RED);
        g2.fillRect(x+w/3, y, w/3, h);
        g2.fillRect(x, y+h/3, w, h/3);
        
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, w/3, h/3-1);
        g2.drawRect(x, y+2*h/3, w/3, h/3);
        g2.drawRect(x+2*w/3, y, w/3, h/3);
        g2.drawRect(x+2*w/3, y+2*h/3, w/3, h/3);
        
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, w, h);
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+HP", x+h/2-15, y+h+15);
        g2.drawString("Cost: " + upgradeCost(), x+h/2-35-(int)((int)Math.log10(1+upgradeCost())*5.0), y+h+35);
        
    }

    @Override
    public int upgradeCost() {
        return 5 * (Controller.getPlayer().getHpLevel() + 2);
    }

    @Override
    protected void applyUpgrade() {
        if(Scoreboard.XP() >= upgradeCost()){
            Scoreboard.modXP(-upgradeCost());
            Controller.getPlayer().levelUpHP();
        }
    }
    
}
