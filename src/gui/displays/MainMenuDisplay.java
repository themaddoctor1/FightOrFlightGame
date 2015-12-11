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
import main.Properties;
import world.WorldManager;

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
        
        double dayLength = 90;
        double timeOfDay = 2*Math.PI*((WorldManager.getTime())%(dayLength))/dayLength;
        
        if(!Properties.DAY_NIGHT_CYCLE)
            timeOfDay = Math.PI/4;
        
        double multiplier = Math.max(0, Math.min(2*Math.sin(timeOfDay)+0.2, 1));
        
        Color sky = new Color((int) (180*multiplier), (int) (180*multiplier), (int) (255*multiplier));
        Color ground = new Color((int)(77*(((0.2+multiplier)/1.2))), (int)(120*(((0.2+multiplier)/1.2))), 0);
        
        g2.setColor(sky);
        g2.fillRect(0, 0, Interface3D.getInterface3D().getWidth(), Interface3D.getInterface3D().getHeight()/2);
        g2.setColor(ground);
        g2.fillRect(0, Interface3D.getInterface3D().getHeight()/2, Interface3D.getInterface3D().getWidth(), Interface3D.getInterface3D().getHeight()/2);
        
        
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
