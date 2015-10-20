/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays;

import gui.Interface3D;
import gui.displays.buttons.DisplayButton;
import gui.displays.buttons.QuitGameButton;
import gui.displays.buttons.ResumeButton;
import gui.displays.buttons.UpgradeCategoryButton;
import gui.displays.buttons.upgrades.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class PauseDisplay extends Display{
    
    private boolean canExit = false;
    
    private DisplayButton[] buttons;
    
    public PauseDisplay(){
        
        Interface3D interf = Interface3D.getInterface3D();
        
        buttons = new DisplayButton[]{
            new ResumeButton(interf.getCenterX()-90, interf.getCenterY()),
            new QuitGameButton(Interface3D.getInterface3D().getCenterX()-80, Interface3D.getInterface3D().getCenterY()+60)
        };
        
    }
    
    @Override
    public void draw(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.BLACK);
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 80));
        g2.drawString("PAUSED", Interface3D.getInterface3D().getCenterX()-150, Interface3D.getInterface3D().getCenterY()/2);
        
        Display.drawControls(g);
        
        for(DisplayButton db : buttons)
            db.draw(g);
        
        Display.drawCursor(g);
        
    }

    @Override
    public void cycle() {
        
    }
    
    public void permitExit(){ canExit = true; }
    public boolean canExit(){ return canExit; }
    
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
