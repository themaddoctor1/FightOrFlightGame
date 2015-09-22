/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.dsplays;

import gui.Camera;
import gui.Controller;
import gui.Interface;
import gui.Interface3D;
import gui.shapes.Polygon3D;
import gui.shapes.Rectangle3D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import main.Scoreboard;
import physics.Coordinate;
import world.WorldManager;
import world.entities.Entity;
import world.entities.creatures.Player;

/**
 *
 * @author Christopher Hittner
 */
public class GameDisplay extends Display{

    @Override
    public void draw(Graphics g) {
        Interface3D interf = Interface3D.getInterface3D();
        
        Graphics2D g2 = (Graphics2D) g;
        
        int X = (int) Controller.getPlayer().getPosition().X();
        int Z = (int) Controller.getPlayer().getPosition().Z();
        
        Player p = Controller.getPlayer();
        Camera c = Controller.getCamera();
        c = new Camera(new Coordinate(c.getPosition().X(), c.getPosition().Y() + p.getSize() * 0.8, c.getPosition().Z()), c.getXZ(), c.getY());
        
        
        int cells = 10;
        double width = 1;
        
        for(double i = -cells + 0.5; i < cells+1; i++){
            Polygon3D.drawCurvedLine(g2, c, 20, new Coordinate(X+i*width,0, Z-(cells - 0.5)*width), new Coordinate(X+i*width,0, Z+(cells - 0.5)*width));
            Polygon3D.drawCurvedLine(g2, c, 20, new Coordinate(X-(cells-0.5)*width,0, Z+i*width), new Coordinate(X+(cells-0.5)*width,0, Z+i*width));
        }
        
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++){
            Entity e = WorldManager.getWorld().getEntities().get(i);
            if(!e.equals(Controller.getPlayer()))
                e.draw(g, c);
        }
        
        p.getWeapon().drawPerspective(g);
        
        double healthLost = p.maxHealth() - p.getHealth();
        
        g2.setColor(new Color(255,0,0,(int)(192 * (1 - p.getHealth()/p.maxHealth()))));
        g2.fillRect((int)healthLost, (int)healthLost, interf.getFrame().getContentPane().getWidth()-2*(int)healthLost+1, interf.getFrame().getContentPane().getHeight()-2*(int)healthLost+1);
        
        
        
        for(int i = 0; i < healthLost; i++){
            Color col = new Color(255,0,0,(int)(255 - 63 * Math.pow(i/p.maxHealth(),0.5)));
            g2.setColor(col);
            g2.fillRect(i, i+1, 1, interf.getFrame().getContentPane().getHeight() - 2*i);
            g2.fillRect(i,i,interf.getFrame().getContentPane().getWidth()-2*i,1);
            g2.fillRect(interf.getFrame().getContentPane().getWidth()-i, i, 1, interf.getFrame().getContentPane().getHeight() - 2*i);
            g2.fillRect(i+1,interf.getFrame().getContentPane().getHeight()-i,interf.getFrame().getContentPane().getWidth()-2*i,1);
            //g2.fillRect(frame.getContentPane().getWidth()-1-i, 0, 1, frame.getContentPane().getHeight()-1-i);
            //g2.fillRect(0,frame.getContentPane().getHeight()-1-i,frame.getContentPane().getWidth()-1-i,1);
        }
        
        /*
        g2.setColor(Color.BLACK);
        g2.fillRect(10, 10, frame.getContentPane().getWidth()/5, 20);
        g2.setColor(Color.red);
        g2.fillRect(10, 10, (int)(p.getHealth()*frame.getContentPane().getWidth()/(5*p.maxHealth())), 20);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Courier New", Font.BOLD, 18));
        g2.drawString(((int)p.getHealth()) + " / " + ((int)p.maxHealth()), 15, 27);
        g2.drawRect(10, 10, frame.getContentPane().getWidth()/5, 20);
        */
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 12));
        g2.setColor(Color.BLACK);
        g2.drawString("Speed: " + (((int)(Controller.getPlayer().getVelocity().getMagnitude()*100)) / 100.0) + " m/s", 10, 60);
        g2.drawString("Perceived Speed: " + (((int)(Controller.getPlayer().getVelocity().getMagnitude()*100/Controller.getPlayer().getSpeedWarp())) / 100.0) + " m/s", 10, 75);
        g2.drawString("Speed Charge: " + (((int)(Controller.getPlayer().getSpeedCharge()*100)) / 100.0) + " Flux Units", 10, 90);
        
        
        double countdown = Scoreboard.timer();
        if(countdown > 0){
            g2.setFont(new Font("Courier New", Font.PLAIN, 36));
            g2.drawString("Round start in:", interf.getCenterX()-160, 30);
            g2.drawString("" + (int)(1+countdown), interf.getCenterX()-9, 80);
        } else {
            g2.setFont(new Font("Courier New", Font.PLAIN, 36));
            g2.drawString("Wave", interf.getCenterX()-40, 30);
            g2.drawString("" + (Scoreboard.wave()), interf.getCenterX()-9-12*(int)(Math.log10(Scoreboard.wave())), 80);
        }
        
    }

}
