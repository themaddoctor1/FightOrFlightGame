/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Camera;
import gui.Controller;
import gui.Interface3D;
import gui.shapes.Polygon3D;
import items.Gun;
import java.awt.Graphics;
import java.awt.Color;
import main.Scoreboard;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.AmmoPickup;

/**
 *
 * @author Christopher
 */
public class Gunman extends Humanoid{
    
    private double faceXZ = 0;
    private double faceY = 0;
    
    public Gunman(Coordinate pos){
        super(pos,1,35*Math.log10(10+Scoreboard.wave()));
        weapon = new Gun();
    }

    @Override
    protected void cycle(double time) {
        
        super.cycle(time);
        
        Vector displacement = new Vector(getPosition(), Controller.getPlayer().getPosition());
        
        double Yvar = displacement.getAngleY() - faceY;
        
        faceY += time * 2 * Math.signum(Yvar) * (Math.pow(Yvar, 2)+1);
        
        double XZvar = displacement.getAngleXZ() - faceXZ;
        while(XZvar > Math.PI)
            XZvar -= 2*Math.PI;
        while(XZvar < -Math.PI)
            XZvar += 2*Math.PI;
        
        faceXZ += 2 * time * Math.signum(XZvar) * (Math.pow(XZvar, 2)+1);
        
        if(getPosition().Y() <= getSize()){
            Vector vel = new Vector((displacement).unitVector(), 3);
            vel.addVectorToThis(new Vector(velocity.getMagnitudeY(), 0, Math.PI/2.0));
            this.velocity = vel;
        }
        
        if(Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition()) >= 2.5)
            weapon.use(time, this, Controller.getPlayer());
    }

    @Override
    public double faceXZ() {
        return faceXZ; //new Vector(getPosition(), Controller.getPlayer().getPosition()).getAngleXZ();
    }
    
    @Override
    public double faceY() {
        return faceY; //new Vector(getPosition(), Controller.getPlayer().getPosition()).getAngleY();
    }
    
    @Override
    public void draw(Graphics g, Camera c){
        
        g.setColor(Color.BLACK);
        
        Coordinate a,b;
        a = new Coordinate(getPosition().X(), getPosition().Y() - 0.2 * getSize(), getPosition().Z());
        b = new Coordinate(
                getPosition().X() + 0.2 * getSize() * Math.cos(faceXZ()+Math.PI/2.0), 
                getPosition().Y()-getSize(), 
                getPosition().Z() + 0.2 * getSize() * Math.sin(faceXZ()+Math.PI/2.0)
        );
        Polygon3D.drawCurvedLine(g, c, 10, a, b);
        
        b = new Coordinate(
                getPosition().X() - 0.2 * getSize() * Math.cos(faceXZ()+Math.PI/2.0), 
                getPosition().Y()-getSize(), 
                getPosition().Z() - 0.2 * getSize() * Math.sin(faceXZ()+Math.PI/2.0)
        );
        Polygon3D.drawCurvedLine(g, c, 10, a, b);
        
        b = new Coordinate(
                getPosition().X(), 
                getPosition().Y()+0.6*getSize(), 
                getPosition().Z()
        );
        Polygon3D.drawCurvedLine(g, c, 10, a, b);
        
        a = new Coordinate(
                getPosition().X() + 0.2 * getSize() * Math.cos(faceXZ()+Math.PI/2.0), 
                getPosition().Y() - 0.2 * getSize(), 
                getPosition().Z() + 0.2 * getSize() * Math.sin(faceXZ()+Math.PI/2.0)
        );
        Polygon3D.drawCurvedLine(g, c, 10, a, b);
        
        a = new Coordinate(
                getPosition().X() - 0.6 * getSize() * Math.cos(faceXZ()+11*Math.PI/12.0), 
                getPosition().Y() + 0.6 * getSize(), 
                getPosition().Z() - 0.8 * getSize() * Math.sin(faceXZ()+11*Math.PI/12.0)
        );
        Polygon3D.drawCurvedLine(g, c, 10, a, b);
        
        b = new Coordinate(
                getPosition().X(), 
                getPosition().Y()+0.8*getSize(), 
                getPosition().Z()
        );
        
        int[] headPos = c.getPlanarCoordinate(b);
        double radius = Math.asin(getSize() * 0.2 / Coordinate.relativeDistance(c.getPosition(), b));
        radius *= Interface3D.getInterface3D().getPixelsPerRadian();
        g.fillOval((int)(headPos[0]-radius), (int)(headPos[1]-radius), (int)(2*radius), (int)(2*radius));
    }
    
    
    @Override
    public void killSelf(){
        if(Math.random() < 0.25)
            WorldManager.getWorld().getEntities().add(new AmmoPickup(getPosition()));
        Scoreboard.modXP(2*Math.log10(10*Scoreboard.wave()));
        super.killSelf();
    }
    
}
