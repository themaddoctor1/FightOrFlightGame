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
public class UpgradeMovementAimCompensationButton extends OneOffUpgradeButton {

    public UpgradeMovementAimCompensationButton(int X, int Y) {
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
        
        g2.drawLine(x+2*w/5, y+h/2, x+3*w/5, y+h/2);
        g2.drawLine(x+w/2, y+2*h/5, x+w/2, y+3*h/5);
        
        g2.drawLine(x+w/5, y+3*h/10, x+2*w/5, y+3*h/10);
        g2.drawLine(x+3*w/10, y+h/5, x+3*w/10, y + 2*h/5);
        
        
        if(selected){
            for(int i = 0; i <= 3; i++)
                g2.drawRect(x+i,y+i,w-2*i,h-2*i);
        }
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        g2.drawString("+Crosshair", x+h/2-45, y+h+15);
        g2.drawString("Cost: " + upgradeCost(), x+h/2-40-(int)((int)Math.log10(1+upgradeCost())*5.0), y+h+35);
        
    }

    @Override
    public int upgradeCost() {
        return 40;
    }

    @Override
    protected void applyUpgrade() {
        Controller.getPlayer().setShootingMoveCompState(true);
    }

    @Override
    public void setUsed() {
        Controller.getPlayer().setShootingMoveCompState(true);
    }

    @Override
    public boolean wasUsed() {
        return Controller.getPlayer().shootingMoveComp();
    }
    
}
