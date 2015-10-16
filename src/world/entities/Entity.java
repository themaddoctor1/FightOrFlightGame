/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities;

import gui.Camera;
import gui.Interface3D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import physics.*;
import world.WorldManager;

/**
 *
 * @author Christopher
 */
public abstract class Entity {
    
    protected Coordinate pos;
    protected Vector velocity = new Vector(0,0,0);
    protected double size;
    
    public Entity(double s){this(new Coordinate(0,s,0),s);}
    public Entity(Coordinate c, double s){
        size = s;
        pos = c;
    }
    
    public final void execute(double time){
        cycle(time);
        move(time);
    }
    
    protected void move(double time){
        if(pos.Y() > size)
            velocity.addVectorToThis(new Vector(9.81 * time, 0, -Math.PI/2.0));
        pos.addVector(new Vector(velocity, time));
    }
    
    protected abstract void cycle(double time);
    
    
    public void draw(Graphics g, Camera c) {
        
        g.setColor(Color.BLACK);
        
        int[] posit = c.getPlanarCoordinate(getPosition());
        double radius = Math.asin(getSize() / Coordinate.relativeDistance(c.getPosition(), getPosition()));
        radius *= Interface3D.getInterface3D().getPixelsPerRadian();
        ((Graphics2D) g).drawOval(posit[0] - (int) radius, posit[1] - (int) radius, (int)(2*radius), (int)(2*radius));
    }
    
    public final Coordinate getPosition(){ return pos; }
    public final double getSize(){ return size; }
    public final Vector getVelocity(){ return velocity; }
    
    
    public void killSelf(){
        WorldManager.getWorld().getEntities().remove(this);
    }
    
}
