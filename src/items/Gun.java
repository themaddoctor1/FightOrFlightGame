/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import gui.Camera;
import gui.Controller;
import gui.Interface;
import gui.Interface3D;
import gui.shapes.Polygon3D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.Bullet;
import world.entities.creatures.Creature;
import world.entities.Entity;

/**
 *
 * @author Christopher
 */
public class Gun extends Weapon{
    
    private double fireRate;
    private double counter = 0.9/fireRate;
    private int ammoCount = -1;
    private final int MAX_AMMO;
    
    public Gun(double rate){
        fireRate = rate;
        MAX_AMMO = -1;
    }
    
    public Gun(){
        this(5);
    }
    
    @Override
    public void cycle(double time, Entity user){
        counter += time;
        counter = Math.min(counter, 1/fireRate);
    }
    
    
    @Override
    public boolean canUse(double time, Entity user, Object... params) {
        return counter >= 1/fireRate;
    }

    @Override
    protected void execute(double time, Entity user, Object... params){
        //If 0 ammo, return. Negative ammo count is meant to represent infinite ammo.
        if(ammoCount == 0)
            return;
        
        Vector dir = new Vector(1,((Creature) user).faceXZ(), ((Creature) user).faceY());
        

        Coordinate start = new Coordinate(user.getPosition().X(), user.getPosition().Y(), user.getPosition().Z());
        start.addVector(new Vector(dir,user.getSize()+0.2));

        Vector vel = new Vector(dir, 10);
        vel.addVectorToThis(user.getVelocity());
        
        WorldManager.getWorld().getEntities().add(new Bullet(start, new Vector(dir, 375)));
        
        counter = 0;
        //Prevents highly unlikely overflow issues.
        ammoCount = Math.max(ammoCount-1, -1);
    }

    @Override
    public void drawPerspective(Graphics g) {
        Camera c = new Camera(new Coordinate(0,0,0), 0, -Math.PI/4 * Math.sin(Math.max(0, counter*fireRate)*Math.PI));
        
        g.setColor(Color.BLACK);
        
        Coordinate a, b;
        a = new Coordinate(0,-.2,-0.2);
        b = new Coordinate(0.3,-.1,-0.2);
        Polygon3D.drawCurvedLine(g, c, 5, a, b);
        
        a = new Coordinate(0.6,0,-0.2);
        Polygon3D.drawCurvedLine(g, c, 5, a, b);
        
        int centX = Interface3D.getInterface3D().getCenterX(), centY = Interface3D.getInterface3D().getCenterY();
        int rad = (int)(2 * Math.log(Math.sqrt(Interface3D.getInterface3D().getWidth() * Interface3D.getInterface3D().getHeight())));
        
        g.drawLine(centX-rad, centY, centX+rad, centY);
        g.drawLine(centX, centY-rad, centX, centY+rad);
        
        
        
        
    }
        
    public int ammoLeft(){
        return ammoCount;
    }

    @Override
    public void drawInterface(Graphics g) {
        
        int wid = Interface.getInterface().getWidth();
        int hei = Interface.getInterface().getHeight();
        
        g.setColor(Color.BLACK);
        g.fillRect(150, hei-162, 10, 37);
        g.fillRect(100, hei-160, 65, 15);
        
        if(ammoCount >= 0){
            String counter = ammoCount + " / " + MAX_AMMO;
            g.setFont(new Font("Courier New", Font.PLAIN, 30));
            g.drawString(counter, 80, hei - 80);
        }
    }
    
    
}
