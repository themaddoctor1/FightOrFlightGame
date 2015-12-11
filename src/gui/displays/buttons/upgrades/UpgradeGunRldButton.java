/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons.upgrades;

import gui.Controller;
import items.Fist;
import items.Gun;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class UpgradeGunRldButton extends UpgradeButton {

    public UpgradeGunRldButton(int X, int Y) {
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
        
        g2.drawLine(x+w/4, y+w/4, x+3*w/4, y+w/4);
        g2.drawLine(x+3*w/4, y+w/4, x+3*w/4, y+5*w/8);
        g2.drawLine(x+7*w/8, y+w/2, x+3*w/4, y+5*w/8);
        g2.drawLine(x+5*w/8, y+w/2, x+3*w/4, y+5*w/8);
        
        g2.drawLine(x+3*w/4, y+3*w/4, x+w/4, y+3*w/4);
        g2.drawLine(x+w/4, y+3*w/4, x+w/4, y+3*w/8);
        g2.drawLine(x+w/8, y+w/2, x+w/4, y+3*w/8);
        g2.drawLine(x+3*w/8, y+w/2, x+w/4, y+3*w/8);
        
        
        g2.drawRect(x, y, w, h);
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+Reload", x+h/2-35, y+h+15);
        String costLine = "Cost: " + upgradeCost();
        g2.drawString(costLine, x+h/2-(5*costLine.length()), y+h+35);
    }

    @Override
    public int upgradeCost() {
        Gun g = ((Gun)Controller.getPlayer().getWeapons().get(1));
        return (int)(5*Math.log10(10*g.RELOAD_FACTOR)*Math.sqrt(g.RELOAD_FACTOR));
    }

    @Override
    protected void applyUpgrade() {
        if(Scoreboard.XP() >= upgradeCost()){
            Scoreboard.modXP(-upgradeCost());
            Gun g = (Gun) Controller.getPlayer().getWeapons().get(1);
            Gun result = new Gun(g.fireRate(), g.spareAmmo(), g.MAX_AMMO, g.RELOAD_FACTOR+0.5, g.HAS_RECOIL);
            Controller.getPlayer().setWeapon(result);
            Controller.getPlayer().replaceWeapon(1, result);
        }
    }
    
}
