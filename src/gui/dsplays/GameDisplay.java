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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Scoreboard;
import physics.Coordinate;
import physics.Vector;
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
        
        if(p.getHealth() <= 0)
            Interface3D.getInterface3D().setDisplay(new GameOverDisplay());
        
        Camera c = Controller.getCamera();
        c = new Camera(new Coordinate(c.getPosition().X(), c.getPosition().Y() + p.getSize() * 0.8, c.getPosition().Z()), c.getXZ(), c.getY());
        
        
        //Draws ground
        /*
        double sightWidth = Math.max(Math.PI, (interf.getCenterX()/interf.getPixelsPerRadian()));
        g2.setColor(new Color(120, 77, 0));
        for(int i = -5; i < 5; i++){
            double alpha = sightWidth*(i)/5.0 + c.getXZ() - 0.01 ;
            double beta = alpha+sightWidth/5.0 + 0.02;
            Coordinate[] slices = {
                new Coordinate(X,0,Z),
                new Coordinate(X+Math.pow(10,5)*Math.cos(alpha), 0, Z+Math.pow(10,5)*Math.sin(alpha)),
                new Coordinate(Z+Math.pow(10,5)*Math.cos(beta), 0, Z+Math.pow(10,5)*Math.sin(beta))
            };
            new Polygon3D(slices, 5).fillShape(g, c);
            
        }
        */
        double dayLength = 90;
        double timeOfDay = 2*Math.PI*((WorldManager.getTime())%(dayLength))/dayLength;
        
        if(false)
            timeOfDay = Math.PI/4;
        
        double multiplier = Math.max(0, Math.min(2*Math.sin(timeOfDay)+0.2, 1));
        
        Color sky = new Color((int) (180*multiplier), (int) (180*multiplier), (int) (255*multiplier));
        Color ground = new Color((int)(77*(((0.2+multiplier)/1.2))), (int)(120*(((0.2+multiplier)/1.2))), 0);
        
        int groundWidth = Math.abs((int)(-2*interf.getCenterX()/Math.sin(c.getY())));
        
        //Draws the background
        if(c.getY() < 0)
            g2.setColor(sky);
        else
            g2.setColor(ground);
        g2.fillRect(0,0,interf.getWidth(), interf.getHeight());
        
        //Draws the "foreground"
        if(c.getY() < 0)
            g2.setColor(ground);
        else
            g2.setColor(sky);
        g2.fillOval((int)(interf.getCenterX() * (1 - 1/Math.abs(Math.sin(c.getY())))), (int)(interf.getCenterY() + c.getY() * interf.getPixelsPerRadian() - groundWidth*(1+Math.signum(c.getY()))/2.0), groundWidth, groundWidth);
        
        double sunRadius = interf.getPixelsPerRadian()*Math.min(Math.abs(Math.asin(Math.sin(timeOfDay))), 10*Math.asin(696300.0/149597870));
        
        Coordinate sun = new Coordinate(c.getPosition().X(), c.getPosition().Y(), c.getPosition().Z());
        sun.addVector(new Vector(100, 0*Math.PI*(1+Math.signum(Math.sin(timeOfDay)))/2.0, timeOfDay + Math.PI * (1-Math.signum(Math.sin(timeOfDay)))/2.0));
        int[] sunPos = c.getPlanarCoordinate(sun);
        
        if(timeOfDay < Math.PI)
            g2.setColor(new Color(255,255,128));
        else
            g2.setColor(Color.WHITE);
        g2.fillOval((int)(sunPos[0]-sunRadius), (int)(sunPos[1]-sunRadius), (int)(2*sunRadius), (int)(2*sunRadius));
        
        g2.setColor(Color.BLACK);
        
        if(c.getPosition().Y() > 0){
            int shadowY = (int)(interf.getCenterY() + interf.getPixelsPerRadian()*(c.getY()+Math.PI/2.0));
            double radius = Math.atan(0.4/c.getPosition().Y()) * interf.getPixelsPerRadian();
            g2.setColor(new Color((int)(16*multiplier),(int)(16*multiplier),(int)(16*multiplier), 192));
            g2.fillOval((int)(interf.getCenterX()-radius), (int)(shadowY-radius), (int)(2*radius), (int)(2*radius));
        }
        
        g2.setColor(Color.BLACK);
        
        /*
        int cells = 5;
        double width = 1;
        
        for(double i = -cells - 0.5; i < cells+1; i++){
            Polygon3D.drawCurvedLine(g2, c, 20, new Coordinate(X+i*width,0, Z-(cells + 0.5)*width), new Coordinate(X+i*width,0, Z+(cells + 0.5)*width));
            Polygon3D.drawCurvedLine(g2, c, 20, new Coordinate(X-(cells+0.5)*width,0, Z+i*width), new Coordinate(X+(cells+0.5)*width,0, Z+i*width));
        }
        */
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++){
            Entity e = WorldManager.getWorld().getEntities().get(i);
            if(!e.equals(Controller.getPlayer()))
                e.draw(g, c);
        }
        
        p.getWeapon().drawPerspective(g);
        
        /*
        //Draws speed charging tint
        int stretch = (int)(0.1*Math.sqrt(interf.getWidth()*interf.getHeight())*(p.getVelocity().getMagnitudeXZ()/p.getSpeedLimit()));
        g2.setColor(new Color(255,255,0,(int)(128*p.getVelocity().getMagnitudeXZ()/p.getSpeedLimit())));
        g2.fillRect(0, 0, stretch, interf.getHeight());
        g2.fillRect(interf.getWidth()-stretch, 0, stretch, interf.getHeight());
        g2.fillRect(stretch, 0, interf.getWidth()-2*stretch, stretch);
        g2.fillRect(stretch, interf.getHeight()-stretch, interf.getWidth()-2*stretch, stretch);
        */
        double healthLost = p.maxHealth() - p.getHealth();
        
        g2.setColor(new Color(255,0,0,(int)(192 * (1 - p.getHealth()/p.maxHealth()))));
        g2.fillRect((int)healthLost, (int)healthLost, interf.getFrame().getContentPane().getWidth()-2*(int)healthLost+1, interf.getFrame().getContentPane().getHeight()-2*(int)healthLost+1);
        
        
        //Draws blood screen
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
        g2.drawString("Speed: " + (((int)(p.getVelocity().getMagnitude()*100)) / 100.0) + " m/s", 10, 60);
        g2.drawString("Perceived Speed: " + (((int)(p.getVelocity().getMagnitude()*100/Controller.getPlayer().getSpeedWarp())) / 100.0) + " m/s", 10, 75);
        g2.drawString("Charge Capacity: " + (((int)(p.getChargeCapacity()*100)) / 100.0) + " Flux Units", 10, 90);
        
        p.getWeapon().drawInterface(g2);
        
        g2.setColor(Color.BLACK);
        
        double countdown = Scoreboard.timer();
        if(countdown > 0){
            g2.setColor(new Color(255,255,255,128));
            g2.fillOval(interf.getCenterX()-200, -100, 400, 200);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Courier New", Font.PLAIN, 36));
            g2.drawString("Round start in:", interf.getCenterX()-160, 30);
            g2.drawString("" + (int)(1+countdown), interf.getCenterX()-9, 80);
        } else {
            g2.setColor(new Color(255,255,255,128));
            g2.fillOval(interf.getCenterX()-200, -100, 400, 200);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Courier New", Font.PLAIN, 36));
            g2.drawString("Wave", interf.getCenterX()-40, 30);
            g2.drawString("" + (Scoreboard.wave()), interf.getCenterX()-9-12*(int)(Math.log10(Scoreboard.wave())), 80);
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
