/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world.entities;

import gui.Camera;
import gui.shapes.Polygon3D;
import java.awt.Graphics;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.creatures.Creature;

/**
 *
 * @author Christopher Hittner
 */
public class Explosion extends Entity{
    public final double SPEED_OF_SOUND = 340.43;
    private final double POWER;
    
    public Explosion(Coordinate c, double power){
        this(c, power, new Vector(0,0,0));
    }
    
    public Explosion(Coordinate c, double power, Vector velocity){
        super(c,Double.MIN_VALUE);
        POWER = power;
    }
    
    private double getIntensity(){
        return POWER / (4 * Math.PI * Math.pow(getSize(), 2));
    }
    
    @Override
    protected void cycle(double time) {
        size += time * SPEED_OF_SOUND;
        
        double intensity = getIntensity();
        
        for(int i = WorldManager.getWorld().getEntities().size()-1; i >= 0; i--){
            Entity e = WorldManager.getWorld().getEntities().get(i);
            
            double distance = Coordinate.relativeDistance(getPosition(), e.getPosition()) - getSize();
            if(Math.abs(distance/e.getSize()) > 1)
                continue;
            double angle = -Math.PI * distance/e.getSize();
            
            double factor = Math.sin(angle);
            
            Vector v = new Vector(new Vector(getPosition(),e.getPosition()).unitVector(), intensity * factor * time * Math.pow(e.getSize(), 3));
            
            e.getVelocity().addVectorToThis(v);
            
            if(e instanceof Creature)
                ((Creature) e).modHealth(-100 * factor * time * intensity);
            
        }
        
        if(intensity < 0.1)
            killSelf();
        
    }
    
    @Override
    public void draw(Graphics g, Camera c){
        
    }
    
    
}
