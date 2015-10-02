/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays.buttons;

import gui.Interface3D;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.Color;

/**
 *
 * @author Christopher
 */
public abstract class DisplayButton {
    
    protected int x, y, w, h;
    
    public DisplayButton(int X, int Y, int W, int H){
        x = X;
        y = Y;
        w = W;
        h = H;
    }
    
    public void draw(Graphics g){
        boolean select = isInButton(Interface3D.getInterface3D().mouseX(), Interface3D.getInterface3D().mouseY());
        
        if(select)
            g.setColor(Color.BLACK);
        else
            g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        
    }
    public abstract void act();
    
    public boolean isInButton(int X, int Y){
        return 
                X > x && x+w > X &&
                Y > y && y+h > Y
                ;
    }
    
}
