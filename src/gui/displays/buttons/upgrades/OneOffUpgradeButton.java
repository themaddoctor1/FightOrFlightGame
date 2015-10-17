/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons.upgrades;

import java.awt.Color;
import java.awt.Graphics;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public abstract class OneOffUpgradeButton extends UpgradeButton{

    public OneOffUpgradeButton(int X, int Y) {
        super(X, Y);
    }
    
    @Override
    public void draw(Graphics g){
        if(!wasUsed())
            super.draw(g);
        else {
            g.setColor(Color.GRAY);
            g.fillRect(x, y, w, h);
        }
    }
    
    @Override
    public void act(){
        if(!wasUsed() && Scoreboard.XP() >= upgradeCost()){
            super.act();
            if(wasUsed())
                setUsed();
        }
    }
    
    public abstract void setUsed();
    public abstract boolean wasUsed();
    
}
