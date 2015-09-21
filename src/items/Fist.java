/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import gui.Camera;
import gui.shapes.Polygon3D;
import java.awt.Color;
import java.awt.Graphics;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.creatures.Creature;
import world.entities.Entity;
import world.entities.HasHealth;
import world.entities.creatures.Player;

/**
 *
 * @author Christopher
 */
public class Fist extends Weapon{
    
    private final double PUNCH_TIME;
    private double epoch = 0, XZ = 0, Y = 0;
    private final double POWER;
    
    public Fist(double pow, double speed){
        POWER = pow;
        PUNCH_TIME = 1/speed;
    }
    
    @Override
    public void cycle(double time, Entity user){
        epoch = Math.max(0, epoch - time);
        
        if(epoch == 0)
            return;
        
        XZ = ((Creature) user).faceXZ();
        Y = ((Creature) user).faceY();
        
        double angle = /*Math.PI/2 * */(epoch/PUNCH_TIME);
        double distance = 0.5 * Math.pow(1-angle,2) + user.getSize()/2.0;
        
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++){
            Entity e = WorldManager.getWorld().getEntities().get(i);
            if(e.equals(user) || !(e instanceof Creature))
                continue;
            Coordinate c = new Coordinate(user.getPosition().X(), user.getPosition().Y(), user.getPosition().Z());
            c.addVector(new Vector(distance, ((Creature) user).faceXZ(), ((Creature) user).faceY()));
            
            if(Coordinate.relativeDistance(c, e.getPosition()) < e.getSize()){
                Vector v = new Vector(2*angle, XZ, Y);
                v.addVectorToThis(user.getVelocity());
                ((Creature) e).modHealth(-POWER * Math.max(v.getMagnitude()-0.1, 0)*time);
            }
                
        }
        
    }
    
    @Override
    public boolean canUse(double time, Entity user, Object... params) {
        return (epoch <= 0);
    }

    @Override
    protected void execute(double time, Entity user, Object... params) {
        epoch = PUNCH_TIME;
    }
    
    public boolean isPunching(){ return epoch > 0; }
    
    @Override
    public void drawPerspective(Graphics g) {
        Camera c = new Camera(new Coordinate(0,0,0), 0, 0);
        
        g.setColor(Color.BLACK);
        
        Coordinate a, b;
        a = new Coordinate(0,-.2,-0.2);
        b = new Coordinate(0.3+0.5 * Math.sin(Math.max(0, epoch/PUNCH_TIME)*Math.PI),-.4 +0.3 * Math.sin(Math.max(0, epoch/PUNCH_TIME)*Math.PI),-0.2);
        Polygon3D.drawCurvedLine(g, c, 5, a, b);
        
        a = new Coordinate(0.6+1 * Math.sin(Math.max(0, epoch/PUNCH_TIME)*Math.PI),0,-0.2);
        Polygon3D.drawCurvedLine(g, c, 5, a, b);
        
        
    }
    
}
