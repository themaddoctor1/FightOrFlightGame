/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Controller;
import gui.Interface3D;
import items.Fist;
import items.Gun;
import main.Properties;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class Player extends Speedster{
    
    
    public Player() {
        super(100);
        weapon = 
                //new Gun(2);
                new Fist(10,2);
        
        this.chargeCapacity = Math.pow(10,-4);
    }
    
    @Override
    protected void cycle(double time){
        
        boolean accelerating = false;
        for(int i = 0; !accelerating && i < 8; i++)
            accelerating |= Interface3D.getInterface3D().getController().getState(i);
        
        Scoreboard.modXP(time*Math.sqrt(Math.log10(getSpeedWarp() + Math.pow(getVelocity().getMagnitude(), 1.5)))*0.1);
        
        if(accelerating && Properties.REQUIRE_SPEED_CHARGE){
            double chargeDecrease = 
                    4*Math.sqrt(1+chargeCapacity)*time*getSpeedWarp()*Math.max(Math.pow(chargeCapacity, 3), Math.cbrt(chargeCapacity));
                    //(time*getSpeedWarp())*Math.pow(10,3)*Math.log10(1+10*Math.pow(getChargeCapacity(),2.5)/(Math.pow(getChargeCapacity(),2)+10)) * Math.max(Math.pow((velocity.getMagnitude()) / getSpeedLimit(), 3), Math.cbrt(velocity.getMagnitude()) / getSpeedLimit());
            
            if(speedChargeRegenTimer >= 2 && getVelocity().getMagnitude() > 4)
                speedChargeRegenTimer = 0;
            
            charge -= chargeDecrease;
        }
        super.cycle(time);
    }
    
    
    @Override
    public double faceXZ() {
        return Controller.getCamera().getXZ();
    }

    @Override
    public double faceY() {
        return Controller.getCamera().getY();
    }
    
    @Override
    public double maxHealth(){
        return super.maxHealth() * Math.pow(1.2, hpLevel) + 10*Math.pow(hpLevel, 1.2);
    }

 
    private int hpLevel = 0;
    public void levelUpHP(){
        hpLevel++;
        healthRegenTimer = 5;
        modHealth(maxHealth());
        
    }
    public int getHpLevel(){ return hpLevel; }

    public void levelUpSpeed(int i) {
        chargeCapacity++;
    }
    
    
    
    
    
    
}
