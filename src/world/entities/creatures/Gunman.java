/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Controller;
import items.Gun;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;

/**
 *
 * @author Christopher
 */
public class Gunman extends Humanoid{
    
    public Gunman(Coordinate pos){
        super(pos,1,25);
        weapon = new Gun();
    }

    @Override
    protected void cycle(double time) {
        
        super.cycle(time);
        
        if(getPosition().Y() <= getSize()){
            Vector vel = new Vector((new Vector(getPosition(), Controller.getPlayer().getPosition())).unitVector(), 3);
            vel.addVectorToThis(new Vector(velocity.getMagnitudeY(), 0, Math.PI/2.0));
            this.velocity = vel;
        }
        
        if(Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition()) >= 2.5)
            weapon.use(time, this, Controller.getPlayer());
    }

    @Override
    public double faceXZ() {
        return new Vector(getPosition(), Controller.getPlayer().getPosition()).getAngleXZ();
    }
    
    @Override
    public double faceY() {
        return new Vector(getPosition(), Controller.getPlayer().getPosition()).getAngleY();
    }
    
}
