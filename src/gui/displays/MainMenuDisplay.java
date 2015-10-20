/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays;

import gui.Interface;
import gui.Interface3D;
import gui.displays.buttons.*;
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
            new StartButton(Interface3D.getInterface3D().getCenterX()-80, Interface3D.getInterface3D().getCenterY()),
            new ExitButton(Interface3D.getInterface3D().getCenterX()-80, Interface3D.getInterface3D().getCenterY()+60)
            
        };
    }
    
    public MainMenuDisplay(int W, int H){
        buttons = new DisplayButton[]{
            new StartButton(W/2-80, H/2),
            new ExitButton(W/2-80, H/2+60)
            
        };
    }
    
    @Override
    public void draw(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        for(int i = 0; i < buttons.length; i++)
            buttons[i].draw(g2);
        
        g2.setColor(Color.BLACK);
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 48));
        g2.drawString(" FIGHT OR FLIGHT ", Interface3D.getInterface3D().getCenterX() - 250, (int)(Interface3D.getInterface3D().getCenterY()*0.7));
        
        Display.drawControls(g);
        
        Display.drawCursor(g2);
        
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
