/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.displays;

import gui.*;
import gui.shapes.Polygon3D;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import main.*;
import physics.Coordinate;
import world.WorldManager;
import world.entities.Entity;
import world.entities.creatures.Player;

/**
 *
 * @author Christopher
 */
public class CompanyLogoDisplay extends Display{
    
    private final long TIMESTAMP;
    
    public CompanyLogoDisplay(){
        TIMESTAMP = System.nanoTime();
    }

    public CompanyLogoDisplay(int width, int height) {
        this();
    }
    
    @Override
    public void draw(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        Interface3D interf = Interface3D.getInterface3D();
        
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, interf.getWidth(), interf.getHeight());
        
        double age = (System.nanoTime()-TIMESTAMP)/Math.pow(10, 9);
        
        g2.setColor(Color.RED);
        g2.fillOval(interf.getCenterX()-12, interf.getCenterY()-12+100, 24, 24);
        
        g2.setColor(Color.YELLOW);
        g2.drawOval(interf.getCenterX()-100, interf.getCenterY()-100+100, 200, 200);
        
        double theta = Math.PI * age;
        
        g2.setColor(new Color(64, 64, 255));
        g2.fillOval(interf.getCenterX()-(int)(100*Math.cos(theta))-8, interf.getCenterY()+100 -(int)(100*Math.sin(theta))-8, 16, 16);
        
        g2.setColor(Color.WHITE);
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 48));
        g2.drawString(" FIGHT OR FLIGHT ", Interface3D.getInterface3D().getCenterX() - 250, (int)(Interface3D.getInterface3D().getCenterY()*0.7));
        
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.drawString("Developed by", interf.getCenterX() - 70, interf.getCenterY()-120);
        g2.drawString("Christopher Hittner", interf.getCenterX() - 115, interf.getCenterY()-100);
        
        if(age >= 4){
            g2.setColor(new Color(255, 255, 255, Math.min((int)(255 * (age-4)), 255)));
            g2.fillRect(0, 0, interf.getWidth(), interf.getHeight());
            g2.setColor(new Color(0, 0, 0, Math.min((int)(255 * (age-4)), 255)));
            g2.setFont(new Font("Courier New", Font.PLAIN, 48));
            g2.drawString(" FIGHT OR FLIGHT ", Interface3D.getInterface3D().getCenterX() - 250, (int)(Interface3D.getInterface3D().getCenterY()*0.7));
            
        }
        
        if(age > 5){
            interf.setDisplay(new MainMenuDisplay());
        }
        
        
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
