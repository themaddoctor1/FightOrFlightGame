/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons.upgrades;

import gui.Controller;
import items.Fist;
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
public class UpgradeFistDmgButton extends UpgradeButton {

    public UpgradeFistDmgButton(int X, int Y) {
        super(X, Y);
    }

    @Override
    public void drawLogo(Graphics g, boolean selected) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        if(selected) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, w, h);
        }
        
        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(x+((i+1)*w)/6,y+h/4,w/6,h/2);
            g2.setColor(Color.ORANGE);
            g2.fill(r);
            g2.setColor(Color.BLACK);
            g2.draw(r);
        }
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(x+w/3, y+h/2, w/2, h/4);
        g2.setColor(Color.BLACK);
        g2.drawRect(x+w/3, y+h/2, w/2, h/4);
        
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, w, h);
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+Damage", x+h/2-35, y+h+15);
        String costLine = "Cost: " + upgradeCost();
        g2.drawString(costLine, x+h/2-(5*costLine.length()), y+h+35);
    }

    @Override
    public int upgradeCost() {
        Fist f = ((Fist)Controller.getPlayer().getWeapons().get(0));
        return (int)(0.25*(f.POWER()/f.SPEED()));
    }

    @Override
    protected void applyUpgrade() {
        if(Scoreboard.XP() >= upgradeCost()){
            Scoreboard.modXP(-upgradeCost());
            Fist f = (Fist) Controller.getPlayer().getWeapons().get(0);
            double speed = f.SPEED();
            Fist result = new Fist(f.POWER() * 1.25, 1.0/speed);
            Controller.getPlayer().setWeapon(result);
            Controller.getPlayer().replaceWeapon(0, result);
        }
    }
    
}
