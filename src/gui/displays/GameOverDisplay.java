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
public class GameOverDisplay extends Display{
    
    private final long TIMESTAMP;
    
    public GameOverDisplay(){
        TIMESTAMP = System.nanoTime();
    }

    public GameOverDisplay(int width, int height) {
        this();
    }
    
    @Override
    public void draw(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        Interface3D interf = Interface3D.getInterface3D();
        
        double age = (System.nanoTime()-TIMESTAMP)/Math.pow(10, 9);
        
        if(age > 5){
            interf.setDisplay(new MainMenuDisplay());
            Scoreboard.endGame();
        }
        
        Player p = Controller.getPlayer();
        double X = p.getPosition().X();
        double Z = p.getPosition().Z();
        
        Camera c = new Camera(
                new Coordinate(
                        X,
                        10*age,
                        Z
                ),
                0,
                -Math.PI/2.0
        );
        
        double dayLength = 90;
        double timeOfDay = 2*Math.PI*((WorldManager.getTime())%(dayLength))/dayLength;
        
        if(!Properties.DAY_NIGHT_CYCLE)
            timeOfDay = Math.PI/4;
        
        double multiplier = Math.max(0, Math.min(2*Math.sin(timeOfDay)+0.2, 1));
        
        Color sky = new Color((int) (180*multiplier), (int) (180*multiplier), (int) (255*multiplier));
        Color ground = new Color((int)(77*(((0.2+multiplier)/1.2))), (int)(120*(((0.2+multiplier)/1.2))), 0);
        
        int groundWidth = Math.abs((int)(-2*interf.getCenterX()/Math.sin(c.getY())));
        
        if(c.getY() < 0){
            g2.setColor(sky);
            g2.fillRect(0,0,interf.getWidth(), interf.getHeight());
            g2.setColor(ground);
            g2.fillOval((int)(interf.getCenterX() * (1 + 1/Math.sin(c.getY()))), (int)(interf.getCenterY() + c.getY() * interf.getPixelsPerRadian()), groundWidth, groundWidth);
        } else if(c.getY() > 0){
            g2.setColor(ground);
            g2.fillRect(0,0,interf.getWidth(), interf.getHeight());
            g2.setColor(sky);
            g2.fillOval((int)(interf.getCenterX() * (1 - 1/Math.sin(c.getY()))), (int)(interf.getCenterY() + c.getY() * interf.getPixelsPerRadian()-groundWidth), groundWidth, groundWidth);
        }
        
        int cells = 10;
        double width = 1;
        
        g2.setColor(Color.BLACK);
        
        /*
        for(double i = -cells - 0.5; i < cells+1; i++){
            Polygon3D.drawCurvedLine(g2, c, 20, new Coordinate(X+i*width,0, Z-(cells + 0.5)*width), new Coordinate(X+i*width,0, Z+(cells + 0.5)*width));
            Polygon3D.drawCurvedLine(g2, c, 20, new Coordinate(X-(cells+0.5)*width,0, Z+i*width), new Coordinate(X+(cells+0.5)*width,0, Z+i*width));
        }
        */
        
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++){
            Entity e = WorldManager.getWorld().getEntities().get(i);
            e.draw(g, c);
        }
        
        g2.setColor(new Color(0,0,0, (int) Math.min(255*age, 255)));
        g2.setFont(new Font("Courier New", Font.PLAIN, 36));
        g2.drawString(
                "You survived until wave " + (Scoreboard.wave()), 
                (int)(interf.getCenterX()-280-12*Math.log10(Scoreboard.wave())), 
                (int)(interf.getCenterY()*0.6));
        if(age <= 0.5){
            g2.setColor(new Color(255,0,0,(int)(192.0*(1.0-2*age))));
            g2.fillRect(0,0,interf.getWidth(), interf.getHeight());
        } else if(age >= 4){
            g2.setColor(new Color(255,255,255,(int)(255.0*(age-4))));
            g2.fillRect(0,0,interf.getWidth(), interf.getHeight());
        }
        
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
        Interface3D.getInterface3D().setDisplay(new MainMenuDisplay());
        Scoreboard.endGame();
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
