/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.displays;

import gui.Interface3D;
import java.awt.Color;
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
    
    public final void drawCursor(Graphics g){
        
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
}
