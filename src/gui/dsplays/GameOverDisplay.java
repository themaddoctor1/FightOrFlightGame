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
import gui.dsplays.buttons.DisplayButton;
import gui.dsplays.buttons.StartButton;
import gui.shapes.Polygon3D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Scoreboard;
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
        
        double age = (System.nanoTime()-TIMESTAMP)/Math.pow(10, 9);
        
        if(age > 5){
            Interface3D.getInterface3D().setDisplay(new MainMenuDisplay());
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
        
        int cells = 10;
        double width = 1;
        
        for(double i = -cells - 0.5; i < cells+1; i++){
            Polygon3D.drawCurvedLine(g2, c, 20, new Coordinate(X+i*width,0, Z-(cells + 0.5)*width), new Coordinate(X+i*width,0, Z+(cells + 0.5)*width));
            Polygon3D.drawCurvedLine(g2, c, 20, new Coordinate(X-(cells+0.5)*width,0, Z+i*width), new Coordinate(X+(cells+0.5)*width,0, Z+i*width));
        }
        
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++){
            Entity e = WorldManager.getWorld().getEntities().get(i);
            e.draw(g, c);
        }
        
        g2.setColor(new Color(0,0,0, (int) Math.min(255*age, 255)));
        g2.setFont(new Font("Courier New", Font.PLAIN, 36));
        g2.drawString(
                "You survived until wave " + (Scoreboard.wave()), 
                (int)(Interface3D.getInterface3D().getCenterX()-280-12*Math.log10(Scoreboard.wave())), 
                (int)(Interface3D.getInterface3D().getCenterY()*0.6));
        
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
