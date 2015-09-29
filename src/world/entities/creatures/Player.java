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

/**
 *
 * @author Christopher
 */
public class Player extends Speedster{
    
    
    public Player() {
        super(100);
        weapon = new Fist(10,2);
        this.chargeCapacity = Math.pow(10,-4);
    }
    
    protected void cycle(double time){
        
        boolean accelerating = false;
        for(int i = 0; !accelerating && i < 8; i++)
            accelerating |= Interface3D.getInterface3D().getController().getState(i);
        
        if(accelerating && Properties.REQUIRE_SPEED_CHARGE){
            double chargeDecrease = (time*getSpeedWarp())*Math.pow(10,3)*Math.log10(1+9*Math.pow(getChargeCapacity(),2)/(Math.pow(getChargeCapacity(),2)+10))*Math.min(Math.pow((velocity.getMagnitude()) / getSpeedLimit(), 3), Math.cbrt(velocity.getMagnitude()) / getSpeedLimit());
            
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

 
}
