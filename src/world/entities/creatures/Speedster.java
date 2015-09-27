/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world.entities.creatures;

import gui.Controller;
import physics.Coordinate;
import physics.Vector;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Speedster extends Humanoid{
    
    protected double speedCharge = 25;
    private double regenTimer = 5;
    
    public Speedster(double hp) {
        super(1, hp);
    }
    
    public Speedster(Coordinate pos, double hp) {
        super(pos, 1, hp);
    }
    
    public double getSpeedWarp(){
        if(getHealth() > 0)
            return Math.sqrt(1 + Math.pow(speedCharge * velocity.getMagnitude() / ((5*(speedCharge+1))) , 2));
        else
            return 1;
    }
    
    public double getSpeedCharge(){ return speedCharge; }
    
    
    public double getAcceleration(){
        
        return (50 - 45/Math.pow(speedCharge+1,1/10)) * Math.cbrt((1 + Math.pow(getSpeedCharge(),2)) * (1 - Math.pow(getVelocity().getMagnitude()/getSpeedLimit(),2)));
    }
    
    public double getSpeedLimit(){
        return (100 - 97/Math.pow(speedCharge+1,1)) * (Math.log10(speedCharge + 10));
    }
    
    
    @Override
    protected void cycle(double time) {
        
        double perceivedTime = time * getSpeedWarp();
        regenTimer = Math.min(5, regenTimer + perceivedTime);
        
        super.cycle(perceivedTime);
        
        if(getHealth() > 0 && regenTimer == 5)
            modHealth(Math.sqrt(4 + Math.pow(speedCharge, 2)) * perceivedTime);
        
        //Kinetic Impulse is KE / m (I'm not certain of whether or not this is real or not)
        if(velocity.getMagnitude() > 0){
            double KI = Math.pow(velocity.getMagnitude(),2)/2.0;

            double chargeDerivative = Math.pow(10, -2.5)/(Math.pow(speedCharge+1,6));
            
            double change = chargeDerivative * KI * perceivedTime;
            
            speedCharge += change;
            
            double magnitude = (0.008*Math.pow(velocity.getMagnitude(), 2));
            if(getPosition().Y() <= getSize())
                magnitude += (0.5 * (2 - Vector.cosOfAngleBetween(velocity, new Vector(1, faceXZ(), faceY()))) / getSpeedWarp());
            
            Vector frictionForce = new Vector(velocity.unitVector(),
                    
                    -time * magnitude);
            
            velocity.addVectorToThis(frictionForce);
        }
        
    }
    
    @Override
    public void modHealth(double amt){
        if(amt < 0)
            regenTimer = 0;
        super.modHealth(amt);
    }
    
}
