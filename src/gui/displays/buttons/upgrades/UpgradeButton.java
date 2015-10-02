/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons.upgrades;

import gui.Interface3D;
import gui.displays.buttons.DisplayButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author Christopher
 */
public abstract class UpgradeButton extends DisplayButton{
    
    public UpgradeButton(int X, int Y) {
        super(X, Y, 96, 96);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setFont(new Font("Courier New", Font.PLAIN, 30));
        
        boolean select = isInButton(Interface3D.getInterface3D().mouseX(), Interface3D.getInterface3D().mouseY());
        
        drawLogo(g, select);
    }

    public abstract void drawLogo(Graphics g, boolean selected);
    
    @Override
    public void act(){
        applyUpgrade();
    }
    
    public abstract int upgradeCost();
    protected abstract void applyUpgrade();
    
}
