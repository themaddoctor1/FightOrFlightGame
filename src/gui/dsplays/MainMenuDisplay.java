/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.dsplays;

import gui.Interface;
import gui.Interface3D;
import gui.dsplays.buttons.DisplayButton;
import gui.dsplays.buttons.StartButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Christopher
 */
public class MainMenuDisplay extends Display{
    
    DisplayButton[] buttons;
    
    public MainMenuDisplay(){
        buttons = new DisplayButton[]{
            new StartButton(Interface3D.getInterface3D().getCenterX()-80, Interface3D.getInterface3D().getCenterY())
            
        };
    }
    
    public MainMenuDisplay(int W, int H){
        buttons = new DisplayButton[]{
            new StartButton(W/2-80, H/2)
            
        };
    }
    
    @Override
    public void draw(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        for(DisplayButton db : buttons)
            db.draw(g2);
        
        g2.setColor(Color.BLACK);
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 48));
        g2.drawString("SPEEDSTER SHOWDOWN", Interface3D.getInterface3D().getCenterX() - 250, (int)(Interface3D.getInterface3D().getCenterY()*0.7));
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        int disp = 250;
        g2.drawString("Controls:", 10, Interface3D.getInterface3D().getCenterY()+disp+0);
        g2.drawString("WASD / Arrow Keys: Move", 10, Interface3D.getInterface3D().getCenterY()+disp+20);
        g2.drawString("Mouse: Look", 10, Interface3D.getInterface3D().getCenterY()+disp+40);
        g2.drawString("LMB / E: Use Weapon", 10, Interface3D.getInterface3D().getCenterY()+disp+60);
        g2.drawString("SHIFT / B: Brakes", 10, Interface3D.getInterface3D().getCenterY()+disp+80);
        g2.drawString("ESC: Exit", 10, Interface3D.getInterface3D().getCenterY()+disp+100);
        
        Polygon cursor = new Polygon();
        cursor.addPoint(Interface3D.getInterface3D().mouseX(), Interface3D.getInterface3D().mouseY());
        cursor.addPoint(Interface3D.getInterface3D().mouseX()+9, Interface3D.getInterface3D().mouseY()+12);
        cursor.addPoint(Interface3D.getInterface3D().mouseX(), Interface3D.getInterface3D().mouseY()+15);
        g2.setColor(Color.WHITE);
        g2.fill(cursor);
        g2.setColor(Color.BLACK);
        g2.draw(cursor);
        
    }

    @Override
    public void cycle() {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(DisplayButton db : buttons)
            if(db.isInButton(e.getX(), e.getY()))
                db.act();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
}
