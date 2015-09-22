/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities;

import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.creatures.Speedster;

/**
 *
 * @author Christopher
 */
public class Bullet extends Entity{

    public Bullet(Coordinate c, Vector vel) {
        super(c, 0.05);
        velocity = vel;
    }
    
    
    @Override
    protected void cycle(double time) {
        
        if(velocity.getMagnitude() > 0){
            
            double magnitude = (0.001*Math.pow(velocity.getMagnitude(), 2));
            Vector frictionForce = new Vector(velocity.unitVector(),
                    
                    -time * magnitude);
            
            velocity.addVectorToThis(frictionForce);
        }
        
        for(int i = WorldManager.getWorld().getEntities().size()-1; i >= 0; i--){
            Entity victim = WorldManager.getWorld().getEntities().get(i);
            
            if(victim == null)
                continue;
            if(victim.equals(this))
                continue;
            
            if(Coordinate.relativeDistance(this.getPosition(), victim.getPosition()) < victim.getSize() + getSize()){
                if(victim instanceof HasHealth){
                    double multiplier = 1;
                    if(victim instanceof Speedster){
                        multiplier *= 1/((Speedster) victim).getSpeedWarp();
                    }
                    Vector dmg = new Vector(velocity.unitVector(),-velocity.getMagnitude());
                    dmg.addVectorToThis(victim.getVelocity());
                    ((HasHealth) victim).modHealth(-(dmg.getMagnitude())*multiplier / 10.0);
                }
                
                killSelf();
                break;
            }
        }
        
        if(getPosition().Y() <= getSize()+0.01 && getVelocity().getMagnitudeY() < 0){
            double yVel = getVelocity().getMagnitudeY();
            velocity.addVectorToThis(new Vector(-2*yVel, 0, Math.PI/2.0));
            velocity.multiplyMagnitude(0.5);
            velocity.addMagnitude(-Math.max(velocity.getMagnitude(), -yVel + velocity.getMagnitude() * Math.sin(velocity.getAngleY())));
            if(velocity.getMagnitude() < 5 || velocity.getAngleY() < -0.1)
                killSelf();
        }
            
        
    }
    
}
