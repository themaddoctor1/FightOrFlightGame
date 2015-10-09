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
public class UpgradeGunSpdButton extends UpgradeButton {

    public UpgradeGunSpdButton(int X, int Y) {
        super(X, Y);
    }

    @Override
    public void drawLogo(Graphics g, boolean selected) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        if(selected) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, w, h);
        }
        
        for(int i = 1; i <= 3; i++){
            int b = x+(i+1)*w/8;
            int f = y+(2+i)*h/8;
            g2.setColor(Color.BLACK);
            g2.drawLine(b, f, b+3*w/8, f);
            g2.setColor(Color.ORANGE);
            g2.fillOval(b-2, f-2, 4, 4);
        }
        
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, w, h);
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+Fire Rate", x+h/2-50, y+h+15);
        String costLine = "Cost: " + upgradeCost();
        g2.drawString(costLine, x+h/2-(5*costLine.length()), y+h+35);
    }

    @Override
    public int upgradeCost() {
        Gun g = ((Gun)Controller.getPlayer().getWeapons().get(1));
        return (int)(5*Math.log10(10*(g.fireRate()-2))*Math.sqrt(g.fireRate()-2));
    }

    @Override
    protected void applyUpgrade() {
        if(Scoreboard.XP() >= upgradeCost()){
            Scoreboard.modXP(-upgradeCost());
            Gun g = (Gun) Controller.getPlayer().getWeapons().get(1);
            Gun result = new Gun(g.fireRate()+1, g.MAX_AMMO, g.RELOAD_FACTOR);
            Controller.getPlayer().setWeapon(result);
            Controller.getPlayer().replaceWeapon(1, result);
        }
    }
    
}
