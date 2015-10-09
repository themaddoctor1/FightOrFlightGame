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

/**
 *
 * @author Christopher
 */
public class HoverDrone extends Creature{
    
    private double preferredAltitude = 4;
    
    public HoverDrone(Coordinate pos){
        super(pos,0.3,25);
        //pos.addVector(new Vector(preferredAltitude - pos.Y(), 0, Math.PI/2.0));
        weapon = new Gun(100, -1, 1);
    }

    @Override
    protected void cycle(double time) {
        
        super.cycle(time);
        
        //Counters gravity
        //getVelocity().addVectorToThis(new Vector(9.81*time,0,Math.PI/2.0));
        
        //Moves towards the player
        double magnitude = 10 * time;
        Vector v = new Vector(getPosition(), Controller.getPlayer().getPosition());
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
    
    @Override
    public void draw(Graphics g, Camera c) {
        int[] posit = c.getPlanarCoordinate(getPosition());
        double radius = Math.asin(getSize() / Coordinate.relativeDistance(c.getPosition(), getPosition()));
        radius *= Interface3D.getInterface3D().getPixelsPerRadian();
        g.setColor(new Color(32,32,32));
        ((Graphics2D) g).fillOval(posit[0] - (int) radius, posit[1] - (int) radius, (int)(2*radius), (int)(2*radius));
    }
    
    @Override
    public void killSelf(){
        Scoreboard.modXP(5*Math.log10(10*Scoreboard.wave()));
        super.killSelf();
    }
    
}
