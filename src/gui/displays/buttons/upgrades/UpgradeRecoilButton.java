/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons.upgrades;

import gui.Controller;
import items.Gun;
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
public class UpgradeRecoilButton extends OneOffUpgradeButton {

    public UpgradeRecoilButton(int X, int Y) {
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
        
        for(int i = 1; i <= 3; i++){
            g2.drawLine(x+w/4, y+i*h/5, x+3*w/4, y+(2*i+1)*w/10);
            g2.drawLine(x+3*w/4, y+(2*i+1)*h/10, x+w/4, y+(i+1)*w/5);
        }
        
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("-Recoil", x+h/2-35, y+h+15);
        g2.drawString("Cost: " + upgradeCost(), x+h/2-40-(int)((int)Math.log10(1+upgradeCost())*5.0), y+h+35);
        
    }

    @Override
    public int upgradeCost() {
        return 40;
    }
    
    @Override
    protected void applyUpgrade() {
        if(Scoreboard.XP() >= upgradeCost()){
            Scoreboard.modXP(-upgradeCost());
            Gun g = (Gun) Controller.getPlayer().getWeapons().get(1);
            Controller.getPlayer().replaceWeapon(1, new Gun(g.fireRate(), g.MAX_AMMO, g.RELOAD_FACTOR, false));
        }
    }
    
    @Override
    public void setUsed() {
        
    }

    @Override
    public boolean wasUsed() {
        return !((Gun) Controller.getPlayer().getWeapons().get(1)).HAS_RECOIL;
    }
    
}
