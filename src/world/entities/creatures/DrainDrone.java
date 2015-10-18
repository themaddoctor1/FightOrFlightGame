/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Camera;
import gui.Controller;
import gui.Interface3D;
import items.Gun;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import main.Scoreboard;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.Entity;

/**
 *
 * @author Christopher
 */
public class DrainDrone extends Creature{
    
    private double preferredAltitude = 4;
    
    public DrainDrone(Coordinate pos){
        super(pos,0.3,25);
        //pos.addVector(new Vector(preferredAltitude - pos.Y(), 0, Math.PI/2.0));
        weapon = null;
    }

    @Override
    protected void cycle(double time) {
        
        super.cycle(time);
        
        //Counters gravity
        //getVelocity().addVectorToThis(new Vector(9.81*time,0,Math.PI/2.0));
        
        Player p = Controller.getPlayer();
        
        //Moves towards the player
        double magnitude = 10 * time;
        Vector v = new Vector(getPosition(), p.getPosition());
        magnitude *= (1 - 0.5*Vector.cosOfAngleBetween(new Vector(1, getVelocity().getAngleXZ(), 0), v));
        v = new Vector(magnitude, v.getAngleXZ(), 0);
        getVelocity().addVectorToThis(v);
        
        double gH = Math.sqrt(64*Math.abs(getPosition().Y()-preferredAltitude));
        
        if(getPosition().Y() < preferredAltitude){
            if(getVelocity().getMagnitudeY() < gH)
                getVelocity().addVectorToThis(new Vector(50*time, 0, Math.PI/2.0));
            else {
                getVelocity().addVectorToThis(new Vector(-50*time, 0, Math.PI/2.0));
            }
        }else if(getPosition().Y() > preferredAltitude){
            if(-getVelocity().getMagnitudeY() > gH)
                getVelocity().addVectorToThis(new Vector(50*time, 0, Math.PI/2.0));
            else {
                getVelocity().addVectorToThis(new Vector(-50*time, 0, Math.PI/2.0));
            }
        } else {
            getVelocity().addVectorToThis(new Vector(2*time,0,Math.PI/2.0));
        }
        
        double drainers = 1;
        double drainMultiplier = 1;
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++){
            Entity e = WorldManager.getWorld().getEntities().get(i);
            if(this.equals(this))
                continue;
            else if(e instanceof DrainDrone){
                drainMultiplier += 1 / Coordinate.relativeDistance(getPosition(), e.getPosition());
                drainers++;
            }
        }
        
        drainMultiplier *= drainers;
        
        double speedDrain = drainMultiplier*Math.sqrt(1+p.getChargeCapacity())*time*p.getSpeedWarp()*p.chargeRate()*Math.max(0, Math.min(p.speedChargeRegenTimer()-1, 2));
        
        p.modCharge(-Math.abs(speedDrain));
        
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
    public void draw(Graphics g, Camera c) {
        int[] posit = c.getPlanarCoordinate(getPosition());
        double radius = Math.asin(getSize() / Coordinate.relativeDistance(c.getPosition(), getPosition()));
        radius *= Interface3D.getInterface3D().getPixelsPerRadian();
        g.setColor(new Color(24,24,64));
        ((Graphics2D) g).fillOval(posit[0] - (int) radius, posit[1] - (int) radius, (int)(2*radius), (int)(2*radius));
    }
    
    @Override
    public void killSelf(){
        Scoreboard.modXP(5*Math.log10(10*Scoreboard.wave()));
        super.killSelf();
    }
    
}
