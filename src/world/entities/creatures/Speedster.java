/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world.entities.creatures;

import gui.Controller;
import main.Properties;
import main.Scoreboard;
import physics.Coordinate;
import physics.Vector;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Speedster extends Humanoid{
    
    protected double chargeCapacity = 25;
    protected double charge = 0;
    protected double healthRegenTimer = 5;
    protected double speedChargeRegenTimer = 5;
    
    public Speedster(double hp) {
        super(1, hp);
    }
    
    public Speedster(Coordinate pos, double hp) {
        super(pos, 1, hp);
    }
    
    public double getSpeedWarp(){
        if(!Properties.REQUIRE_SPEED_CHARGE || (charge > 0) && getHealth() > 0 && chargeCapacity != 0){
            double chargeWarpCoefficient = 0;
            if(Properties.CHARGE_CAUSES_WARP) chargeWarpCoefficient = Math.pow(Math.pow(chargeCapacity, 2)*Math.sin(charge*Math.PI/2.0), 4);
                return Math.sqrt(1 + Math.pow(chargeCapacity * velocity.getMagnitude() / ((5*(chargeCapacity+1))) , 2) + Math.pow(chargeCapacity*Math.sin(charge*Math.PI/2.0), 2) + chargeWarpCoefficient);
        }else
            return 1;
    }
    
    public double getChargeCapacity(){ return chargeCapacity; }
    
    public double getCharge() { return charge; }
    public double chargeRate(){ return 20*(1+Math.pow(getCharge()/maxCharge(), 2))*(Math.sqrt(1+chargeCapacity)); }
    public double maxCharge(){
        return 100*Math.pow((chargeCapacity+1)/(chargeCapacity+2), 2);
    }
    
    public double getAcceleration(){
        if(!Properties.REQUIRE_SPEED_CHARGE || (charge > 0))
            return Math.log((Math.E+10*chargeCapacity))*(50 - 45/Math.pow(chargeCapacity+1,1/9)) * Math.cbrt((1 + Math.pow(getChargeCapacity(),2)) * (1 - Math.pow(getVelocity().getMagnitude()/getSpeedLimit(),4)));
        return 5;
    }
    
    public double getSpeedLimit(){
        if(!Properties.REQUIRE_SPEED_CHARGE || (charge > 0))
            return (100 - 97/Math.pow(chargeCapacity+1,1)) * (Math.log10((chargeCapacity*10) + 10))*Math.log10(10+Math.pow(chargeCapacity,2)/5);
        return 3;
    }
    
    
    @Override
    protected void cycle(double time) {
        
        double perceivedTime = time * getSpeedWarp();
        healthRegenTimer = Math.min(5, healthRegenTimer + perceivedTime);
        
        super.cycle(perceivedTime);
        
        if(getHealth() > 0 && healthRegenTimer == 5)
            modHealth(25*Math.sqrt(4 + Math.pow(chargeCapacity*getCharge()/maxCharge(), 2)) * perceivedTime);
        
        
        if(Properties.REQUIRE_SPEED_CHARGE){
            
            double chargeIncrease = perceivedTime*chargeRate()*Math.max(0, Math.min(speedChargeRegenTimer-1, 1));
            charge += chargeIncrease;
            
            speedChargeRegenTimer = Math.max(0, Math.min(speedChargeRegenTimer+perceivedTime, 2));
        }else
            charge = chargeCapacity;
        
        //Kinetic Impulse is KE / m (I'm not certain of whether or not this is real or not)
        if(velocity.getMagnitude() > 0){
            double KI = Math.pow(velocity.getMagnitude(),2)/2.0;
            
            double chargeDerivative = Math.pow(10, -2.5)/(Math.pow(chargeCapacity+1,7));
            
            double change = chargeDerivative * KI * perceivedTime;
            
            if(getPosition().Y() <= getSize())
                chargeCapacity += change;
            
            double magnitude = (0.008*Math.pow(velocity.getMagnitude(), 2));
            if(getPosition().Y() <= getSize())
                magnitude += (0.5 * (2 - Vector.cosOfAngleBetween(velocity, new Vector(1, faceXZ(), faceY()))) / getSpeedWarp());
            
            Vector frictionForce = new Vector(velocity.unitVector(),
                    
                    -time * magnitude);
            
            velocity.addVectorToThis(frictionForce);
        }
        //Speedsters should get hurt if they go too fast
        if(Properties.DAMAGE_FROM_OVERSPEED && velocity.getMagnitude() > getSpeedLimit() && getPosition().Y() <= getSize()){
            double dmg = (Math.pow(velocity.getMagnitude(), 2) - Math.pow(getSpeedLimit(), 2))/Math.pow(Math.PI+Math.pow(chargeCapacity,2),2) * perceivedTime;
            modHealth(-dmg);
        }
        
        charge = Math.max(0, Math.min(charge, maxCharge()));
        
    }
    
    @Override
    public void modHealth(double amt){
        if(amt < 0)
            healthRegenTimer = 0;
        super.modHealth(amt);
    }
    
    @Override
    public void killSelf(){
        if(!(this instanceof Player)){
            super.killSelf();
            Scoreboard.modXP(20*Math.log10(10*Scoreboard.wave()));
        }
    }
    
    public void modCharge(double amt) {
        charge += amt;
    }
    
    public void modChargeCapacity(double amt) {
        chargeCapacity += amt;
    }
    
    public double speedChargeRegenTimer(){
        return speedChargeRegenTimer;
    }
    
}
