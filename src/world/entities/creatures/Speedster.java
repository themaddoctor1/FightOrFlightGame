/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world.entities.creatures;

import gui.Controller;
import main.Properties;
import physics.Coordinate;
import physics.Vector;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Speedster extends Humanoid{
    
    protected double chargeCapacity = 25;
    protected double charge = 0;
    private double regenTimer = 5;
    
    public Speedster(double hp) {
        super(1, hp);
    }
    
    public Speedster(Coordinate pos, double hp) {
        super(pos, 1, hp);
    }
    
    public double getSpeedWarp(){
        if(!Properties.REQUIRE_SPEED_CHARGE || charge > 0 && getHealth() > 0 && chargeCapacity != 0)
            return Math.sqrt(1 + Math.pow(chargeCapacity * velocity.getMagnitude() / ((5*(chargeCapacity+1))) , 2));
        else
            return 1;
    }
    
    public double getChargeCapacity(){ return chargeCapacity; }
    
    public double getCharge() { return charge; }
    
    public double getAcceleration(){
        if(!Properties.REQUIRE_SPEED_CHARGE || charge > 0)
            return (50 - 45/Math.pow(chargeCapacity+1,1/10)) * Math.cbrt((1 + Math.pow(getChargeCapacity(),2)) * (1 - Math.pow(getVelocity().getMagnitude()/getSpeedLimit(),2)));
        return 5;
    }
    
    public double getSpeedLimit(){
        if(!Properties.REQUIRE_SPEED_CHARGE || charge > 0)
            return (100 - 97/Math.pow(chargeCapacity+1,1)) * (Math.log10(chargeCapacity + 10));
        return 3;
    }
    
    
    @Override
    protected void cycle(double time) {
        
        double perceivedTime = time * getSpeedWarp();
        regenTimer = Math.min(5, regenTimer + perceivedTime);
        
        super.cycle(perceivedTime);
        
        if(getHealth() > 0 && regenTimer == 5)
            modHealth(Math.sqrt(4 + Math.pow(chargeCapacity, 2)) * perceivedTime);
        
        charge += Math.sqrt(1+chargeCapacity)*perceivedTime*Math.max(Math.pow(chargeCapacity, 3), Math.cbrt(chargeCapacity));
        
        //Kinetic Impulse is KE / m (I'm not certain of whether or not this is real or not)
        if(velocity.getMagnitude() > 0){
            double KI = Math.pow(velocity.getMagnitude(),2)/2.0;
            
            double chargeDerivative = Math.pow(10, -2.5)/(Math.pow(chargeCapacity+1,6));
            
            double change = chargeDerivative * KI * perceivedTime;
            
            chargeCapacity += change;
            charge -= Math.log10(1+9*Math.pow(chargeCapacity,2)/(Math.pow(chargeCapacity,2)+10))*Math.min(Math.pow((velocity.getMagnitude()) / getSpeedLimit(), 3), Math.cbrt(velocity.getMagnitude()) / getSpeedLimit());
            
            double magnitude = (0.008*Math.pow(velocity.getMagnitude(), 2));
            if(getPosition().Y() <= getSize())
                magnitude += (0.5 * (2 - Vector.cosOfAngleBetween(velocity, new Vector(1, faceXZ(), faceY()))) / getSpeedWarp());
            
            Vector frictionForce = new Vector(velocity.unitVector(),
                    
                    -time * magnitude);
            
            velocity.addVectorToThis(frictionForce);
        }
        
        charge = Math.max(0, Math.min(charge, 1 + 10*Math.max(Math.pow(chargeCapacity, 2), Math.sqrt(chargeCapacity))));
        
    }
    
    @Override
    public void modHealth(double amt){
        if(amt < 0)
            regenTimer = 0;
        super.modHealth(amt);
    }
    
}
