/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Controller;
import items.Fist;
import items.Gun;

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
    
    
    @Override
    public double faceXZ() {
        return Controller.getCamera().getXZ();
    }

    @Override
    public double faceY() {
        return Controller.getCamera().getY();
    }


    
}
