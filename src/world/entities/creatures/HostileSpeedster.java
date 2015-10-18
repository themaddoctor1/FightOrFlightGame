/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Controller;
import items.Fist;
import items.Gun;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;

/**
 *
 * @author Christopher
 */
public class HostileSpeedster extends Speedster{
    
    public HostileSpeedster(Coordinate pos){
        super(pos,100);
        weapon = new Fist(1, 1);
        double playerCapacity = Controller.getPlayer().chargeCapacity;
        chargeCapacity = playerCapacity - Math.log10(1+playerCapacity);
    }

    @Override
    protected void cycle(double time) {
        
        double perceivedTime = time * getSpeedWarp();
        
        super.cycle(time);
        if(getPosition().Y() <= getSize()){
            Vector acc = new Vector(0,0,0);
            
            double dist = Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition());
            
            if(getVelocity().getMagnitude() == 0){
                velocity.addVectorToThis(new Vector(new Vector(getPosition(), Controller.getPlayer().getPosition()).unitVector(), 0.01));
            }
            
            
            
            if(dist < 0.9){
                acc = new Vector(getVelocity(), -perceivedTime * getAcceleration() * (0.9-dist));
            } else {
                acc = new Vector(1, faceXZ(), faceY());
                acc.addVectorToThis(new Vector(new Vector(velocity, -1).unitVector(), 0.5));
                acc = new Vector(acc.unitVector(), getAcceleration() * perceivedTime);
            }
            
            acc.multiplyMagnitude(Math.pow(2 - Vector.cosOfAngleBetween(acc, getVelocity()),1.5));
            
            velocity.addVectorToThis(acc);
            
            
        }
        
        if(Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition()) <= 1.01){
                
            weapon.use(time, this, Controller.getPlayer());
        }
        
        
    }

    @Override
    public double faceXZ() {
        Vector face =  new Vector(getPosition(), Controller.getPlayer().getPosition());
        double dist = Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition());
        if(dist < 25 || dist > 100)
            return face.getAngleXZ();
        else return face.getAngleXZ()/* + Math.PI*/;
    }
    
    @Override
    public double faceY() {
        Vector face =  new Vector(getPosition(), Controller.getPlayer().getPosition());
        double dist = Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition());
        if(dist < 25 || dist > 100)
            return face.getAngleY();
        else
            return face.getAngleY();
    }
    
    @Override
    public void killSelf(){
        Player p = Controller.getPlayer();
        
        double newCapacity = Math.cbrt(Math.pow(p.getChargeCapacity(), 3) + Math.pow(getChargeCapacity(), 3));
        
        p.modChargeCapacity(newCapacity - p.getChargeCapacity());
        
        super.killSelf();
    }
    
}
