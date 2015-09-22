/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world.entities;

import physics.Coordinate;
import physics.Vector;
import world.WorldManager;

/**
 *
 * @author Christopher Hittner
 */
public class Missile extends Entity{
    
    public final Coordinate TARGET;
    public final double SENSITIVITY;
    public double thrust = 20, fuel = 10;
    
    public Missile(Coordinate c, Vector start, Coordinate targ, double detRange) {
        super(c, 0.5);
        velocity = start;
        TARGET = targ;
        SENSITIVITY = detRange;
    }

    @Override
    protected void cycle(double time) {
        
        //Air resistance
        getVelocity().addVectorToThis(new Vector(getVelocity().unitVector(), time * (getThrust() - 0.001 * Math.pow(getVelocity().getMagnitude(), 2))));
        
        
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++){
            Entity e = WorldManager.getWorld().getEntities().get(i);
            
            if(e.equals(this))
                continue;
            
            if(Coordinate.relativeDistance(e.getPosition(), getPosition()) < getSize()+e.getSize()){
                killSelf();
                return;
            }
        }
        
        if(getPosition().Y() <= getSize() || Coordinate.relativeDistance(getPosition(), TARGET) <= SENSITIVITY){
            killSelf();
        } else if(fuel > 0){
            if(getVelocity().getMagnitudeY() < -Math.sqrt((thrust-9.81)*getPosition().Y()) || getPosition().Y()-getSize() < 0.5){
                Vector force = new Vector(thrust*time, 0, Math.PI/2.0);
                System.out.println("UP");
                getVelocity().addVectorToThis(force);
            } else {
                Vector dir = new Vector(getPosition(), TARGET);
                double cos = (Vector.cosOfAngleBetween(dir, getVelocity()));
                Vector diff = new Vector(getVelocity().unitVector(), -dir.getMagnitude() * (cos+1)/2);
                dir.addVectorToThis(diff);
                Vector force = new Vector(dir.unitVector(), thrust * time/5);
                getVelocity().addVectorToThis(force);
            }
            fuel -= time;
        }
        
    }
    
    public double getThrust(){
        if(fuel > 0)
            return thrust;
        else
            return 0;
    }
    
    @Override
    public void killSelf(){
        super.killSelf();
        WorldManager.getWorld().getEntities().add(new Explosion(getPosition(), Math.pow(10, 5), new Vector(getVelocity(),1)));
    }

}
