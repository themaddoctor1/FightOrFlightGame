/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.displays;

import gui.Interface3D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.*;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Display implements KeyListener, MouseListener, MouseMotionListener{
    
    public Display(){}
    public Display(int W, int H){}
    
    public abstract void draw(Graphics g);
    public abstract void cycle();
    
    public static final void drawCursor(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g;
        
        Polygon cursor = new Polygon();
        cursor.addPoint(Interface3D.getInterface3D().mouseX(), Interface3D.getInterface3D().mouseY());
        cursor.addPoint(Interface3D.getInterface3D().mouseX()+9, Interface3D.getInterface3D().mouseY()+12);
        cursor.addPoint(Interface3D.getInterface3D().mouseX(), Interface3D.getInterface3D().mouseY()+15);
        g2.setColor(Color.WHITE);
        g2.fill(cursor);
        g2.setColor(Color.BLACK);
        g2.draw(cursor);
    }
    
    public static final void drawControls(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        int disp = 250;
        g2.drawString("Controls:", 10, Interface3D.getInterface3D().getCenterY()+disp+0);
        g2.drawString("WASD / Arrow Keys: Move", 10, Interface3D.getInterface3D().getCenterY()+disp+20);
        g2.drawString("Mouse: Look", 10, Interface3D.getInterface3D().getCenterY()+disp+40);
        g2.drawString("LMB: Use Weapon", 10, Interface3D.getInterface3D().getCenterY()+disp+60);
        g2.drawString("SHIFT or B: Brakes", 10, Interface3D.getInterface3D().getCenterY()+disp+80);
        g2.drawString("P: Pause Game", 10, Interface3D.getInterface3D().getCenterY()+disp+100);
        g2.drawString("U: Access Upgrade Menu", 10, Interface3D.getInterface3D().getCenterY()+disp+120);
        g2.drawString("-/= or Q/E: Switch Weapon", 10, Interface3D.getInterface3D().getCenterY()+disp+140);
        g2.drawString("R: Reload", 10, Interface3D.getInterface3D().getCenterY()+disp+160);
        
    }
}
