/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Controller;
import items.Fist;
import items.Gun;
import main.Scoreboard;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.AmmoPickup;

/**
 *
 * @author Christopher
 */
public class FistFighter extends Humanoid{
    
    public FistFighter(Coordinate pos){
        super(pos,1,25*Math.log10(10+Scoreboard.wave()));
        weapon = new Fist(10, 1);
    }

    @Override
    protected void cycle(double time) {
        
        super.cycle(time);
        
        if(getPosition().Y() <= getSize()){
            Vector vel = new Vector((new Vector(getPosition(), Controller.getPlayer().getPosition())).unitVector(), 3);
            vel.addVectorToThis(new Vector(velocity.getMagnitudeY(), 0, Math.PI/2.0));
            this.velocity = vel;
        }
        
        if(Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition()) <= 1.01){
            weapon.use(time, this, Controller.getPlayer());
            this.velocity = new Vector(Controller.getPlayer().getVelocity().unitVector(), Math.min(Controller.getPlayer().getVelocity().getMagnitude(), 3));
        }
    }

    @Override
    public double faceXZ() {
        return new Vector(getPosition(), Controller.getPlayer().getPosition()).getAngleXZ();
    }
    
    @Override
    public double faceY() {
        return new Vector(getPosition(), Controller.getPlayer().getPosition()).getAngleY();
    }
    
    
    @Override
    public void killSelf(){
        Scoreboard.modXP(Math.log10(10*Scoreboard.wave()));
        super.killSelf();
    }
    
}
