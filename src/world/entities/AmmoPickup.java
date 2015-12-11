/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities;

import gui.Camera;
import gui.Controller;
import gui.Interface3D;
import items.Gun;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import physics.Coordinate;
import physics.Vector;

/**
 *
 * @author Christopher
 */
public class AmmoPickup extends Entity {
    public final int AMT;
    
    private double age = 0;
    
    public AmmoPickup(Coordinate c) {
        this(c, (int)(((Gun)Controller.getPlayer().getWeapons().get(1)).MAX_AMMO * Math.random()));
    }
    
    public AmmoPickup(Coordinate c, int amt) {
        super(c, 0.2);
        AMT = amt;
    }
    
    @Override
    protected void cycle(double time) {
        
        if(Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition()) < 2) {
            ((Gun)Controller.getPlayer().getWeapons().get(1)).giveAmmo(AMT);
            this.killSelf();
        }
        
        if (age > 15)
            this.killSelf();
        
        age += time * Controller.getPlayer().getSpeedWarp();
    }
    
    @Override
    public void draw(Graphics g, Camera c) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(new Color(0, 0, 0, (int)(255f * (Math.min(Math.max((18 - age) / 3, 0), 1)))));
        
        int[] posit = c.getPlanarCoordinate(getPosition());
        double radius = Interface3D.getInterface3D().getPixelsPerRadian()*Math.asin(getSize() / Coordinate.relativeDistance(c.getPosition(), getPosition()));
        double x = 0.25*radius;
        double y = radius * Math.cos(new Vector(c.getPosition(), getPosition()).getAngleY());
        g2.fillRect(posit[0] - (int)(x), posit[1] - (int)(y), (int)(2*x), (int)(2*y));
    }
    
}
