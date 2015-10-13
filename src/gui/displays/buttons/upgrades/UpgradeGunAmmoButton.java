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
public class UpgradeGunAmmoButton extends UpgradeButton {

    public UpgradeGunAmmoButton(int X, int Y) {
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
        
        for(int i = 1; i <= 4; i++){
            g2.drawOval(x+(2*i-1)*w/10, y+h/4, w/5, h/2);
            g2.fillRect(x+(2*i-1)*w/10-1, y+h/2, w/5+2, h/4+1);
            g2.fillRect(x+w/10, y+5*h/8, 4*w/5, h/12);
        }
        
        g2.drawRect(x, y, w, h);
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+Ammo", x+h/2-25, y+h+15);
        String costLine = "Cost: " + upgradeCost();
        g2.drawString(costLine, x+h/2-(5*costLine.length()), y+h+35);
    }

    @Override
    public int upgradeCost() {
        Gun g = ((Gun)Controller.getPlayer().getWeapons().get(1));
        return (int)(5*Math.log10(10*(g.MAX_AMMO/8.0))*Math.sqrt(g.MAX_AMMO/8.0));
    }

    @Override
    protected void applyUpgrade() {
        if(Scoreboard.XP() >= upgradeCost()){
            Scoreboard.modXP(-upgradeCost());
            Gun g = (Gun) Controller.getPlayer().getWeapons().get(1);
            Gun result = new Gun(g.fireRate(), (int)(g.MAX_AMMO*1.5), g.RELOAD_FACTOR, g.HAS_RECOIL);
            Controller.getPlayer().setWeapon(result);
            Controller.getPlayer().replaceWeapon(1, result);
        }
    }
    
}
