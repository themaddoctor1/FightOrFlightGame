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
import java.awt.Color;
import java.awt.Graphics;
import main.Scoreboard;
import physics.Coordinate;
import physics.Vector;

/**
 *
 * @author Christopher
 */
public class GhostGunman extends Gunman{

    public GhostGunman(Coordinate pos) {
        super(pos);
    }
    
    @Override
    public void draw(Graphics g, Camera c){
        
        g.setColor(new Color(0,0,0,(int)((Math.pow(Vector.cosOfAngleBetween(Controller.getCamera().getDirection(), new Vector(Controller.getCamera().getPosition(), getPosition()))+1, 2)/4.0) * (12 + 24.0 / Math.cbrt(Math.log10(10*(Coordinate.relativeDistance(pos, Controller.getPlayer().getPosition()))))))));
        
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
        Scoreboard.modXP(Math.log10(10*Scoreboard.wave()));
        super.killSelf();
    }
    
}
