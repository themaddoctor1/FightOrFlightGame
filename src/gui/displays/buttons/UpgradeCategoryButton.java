/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons;

import gui.Controller;
import gui.Interface;
import gui.Interface3D;
import gui.displays.GameDisplay;
import gui.displays.UpgradeDisplay;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class UpgradeCategoryButton extends DisplayButton{

    private String menuName;
    
    public UpgradeCategoryButton(int X, int Y, String s) {
        super(X, Y, 100, 20);
        menuName = s;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
        
        for(int i = 1; i < 3 && ((UpgradeDisplay) Interface3D.getInterface3D().getDisplay()).getSubmenuName().equals(menuName); i++)
            g.drawRect(x+i, y+i, w-2*i, h-2*i);
        
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        
        boolean select = isInButton(Interface3D.getInterface3D().mouseX(), Interface3D.getInterface3D().mouseY());
        
        if(select)
            g.setColor(Color.WHITE);
        else
            g.setColor(Color.BLACK);
        
        g.drawString(menuName, x+w/2-5*menuName.length(), y+h-5);
    }

    @Override
    public void act() {
        ((UpgradeDisplay) Interface3D.getInterface3D().getDisplay()).setButtonCategory(menuName);
    }

    
}
