/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import gui.Camera;
import gui.Controller;
import gui.Interface;
import gui.Interface3D;
import gui.shapes.Polygon3D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import main.Properties;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.Bullet;
import world.entities.creatures.Creature;
import world.entities.Entity;
import world.entities.creatures.Speedster;

/**
 *
 * @author Christopher
 */
public class Gun extends Weapon{
    
    private double fireRate;
    private double counter = 0.9/fireRate;
    private int ammoCount, spareAmmo = -1;
    public final int MAX_AMMO;
    private double reloadTimer = -1;
    public final double RELOAD_FACTOR;
    public final boolean HAS_RECOIL;
    
    public Gun(double rate, int maxAmmo, int spareAmmo, double reloadFactor, boolean recoil){
        fireRate = rate;
        MAX_AMMO = maxAmmo;
        ammoCount = maxAmmo;
        RELOAD_FACTOR = reloadFactor;
        HAS_RECOIL = recoil;
        this.spareAmmo = spareAmmo;
    }
    
    public Gun(){
        this(5, -1, -1, 1, true);
    }
    
    @Override
    public void cycle(double time, Entity user){
        
        if(ammoCount == 0 && MAX_AMMO > 0 && spareAmmo != 0){
            
            if(reloadTimer == -1)
                reloadTimer = 0;
            else if(reloadTimer >= Math.log(Math.E+MAX_AMMO)/RELOAD_FACTOR){
                ammoCount = spareAmmo > 0 ? Math.min(MAX_AMMO, spareAmmo) : MAX_AMMO;
                spareAmmo = Math.max(-1, spareAmmo - ammoCount);
                reloadTimer = -1;
            } else {
                reloadTimer += time;
            }
        }
        
        counter = Math.min(counter+time, 1/fireRate);
    }
    
    
    @Override
    public boolean canUse(double time, Entity user, Object... params) {
        return counter >= 1/fireRate;
    }

    @Override
    protected void execute(double time, Entity user, Object... params){
        //If 0 ammo, return. Negative ammo count is meant to represent infinite ammo.
        
        if(ammoCount > 0 || MAX_AMMO <= 0) {
            
            Vector dir = new Vector(1,((Creature) user).faceXZ(), ((Creature) user).faceY());
            
            if(Properties.USE_RECOIL && HAS_RECOIL){
                double userSpeed = user.getVelocity().getMagnitude();
                
                if(user instanceof Speedster)
                    userSpeed /= ((Speedster) user).getSpeedWarp();
                
                if(userSpeed < 0.5)
                    userSpeed = 0;
                
                userSpeed *= (9 - 8*Math.pow(Vector.cosOfAngleBetween(user.getVelocity(), dir), 2))/9;
                
                double theta = Math.random()-0.5;
                double alpha = Math.random()*2*Math.PI;

                double wobbleMagnitude = Math.pow(userSpeed/(userSpeed+4), 2)/(2*Math.log(10*(1+counter)));

                Vector wobble = new Vector(wobbleMagnitude, theta, alpha);

                dir.addVectorToThis(wobble);

                dir = dir.unitVector();
            }
            
            Coordinate start = new Coordinate(user.getPosition().X(), user.getPosition().Y(), user.getPosition().Z());
            start.addVector(new Vector(dir,user.getSize()+0.2));
            
            Vector vel = new Vector(dir, 375);
            vel.addVectorToThis(user.getVelocity());

            WorldManager.getWorld().getEntities().add(new Bullet(start, vel));

            counter = 0;
            //Prevents highly unlikely overflow issues.
            ammoCount = Math.max(ammoCount-1, -1);
        }
    }

    @Override
    public void drawPerspective(Graphics g) {
        Camera c = new Camera(new Coordinate(0,0,0), 0, -Math.PI/4 * Math.sin(Math.max(0, counter*fireRate)*Math.PI));
        
        g.setColor(Color.BLACK);
        
        Coordinate a, b;
        a = new Coordinate(0,-.2,-0.2);
        b = new Coordinate(0.3,-.1,-0.2);
        Polygon3D.drawCurvedLine(g, c, 5, a, b);
        
        a = new Coordinate(0.6,0,-0.2);
        Polygon3D.drawCurvedLine(g, c, 5, a, b);
        
        int centX = Interface3D.getInterface3D().getCenterX(), centY = Interface3D.getInterface3D().getCenterY();
        int rad = (int)(2 * Math.log(Math.sqrt(Interface3D.getInterface3D().getWidth() * Interface3D.getInterface3D().getHeight())));
        
        g.drawLine(centX-rad, centY, centX+rad, centY);
        g.drawLine(centX, centY-rad, centX, centY+rad);
        
        if(Controller.getPlayer().shootingMoveComp()){
            Vector aim = new Vector(375, Controller.getPlayer().faceXZ(), Controller.getPlayer().faceY());
            aim.addVectorToThis(Controller.getPlayer().getVelocity());
            
            double xOff = Interface3D.getInterface3D().getPixelsPerRadian()*(aim.getAngleXZ() - Controller.getPlayer().faceXZ());
            double yOff = Interface3D.getInterface3D().getPixelsPerRadian()*(aim.getAngleY() - Controller.getPlayer().faceY());
            
            
            while(Math.abs(xOff) >= Math.PI*Interface3D.getInterface3D().getPixelsPerRadian())
                xOff -= Math.signum(xOff)*2*Math.PI*Interface3D.getInterface3D().getPixelsPerRadian();
            while(Math.abs(yOff) >= Math.PI*Interface3D.getInterface3D().getPixelsPerRadian())
                yOff -= Math.signum(yOff)*2*Math.PI*Interface3D.getInterface3D().getPixelsPerRadian();
            
            rad /= 2;
            
            g.drawLine(centX-rad-(int)(xOff), centY-rad-(int)(yOff), centX+rad-(int)(xOff), centY+rad-(int)(yOff));
            g.drawLine(centX-rad-(int)(xOff), centY+rad-(int)(yOff), centX+rad-(int)(xOff), centY-rad-(int)(yOff));
        }
        
        
        
        
    }
    
    public void addAmmo(int amt){
        ammoCount = Math.max(0, Math.min(amt+ammoCount, MAX_AMMO));
    }
        
    public int ammoLeft(){
        return ammoCount;
    }
    
    public int spareAmmo() {
        return spareAmmo;
    }
    
    public double fireRate(){
        return fireRate;
    }

    @Override
    public void drawInterface(Graphics g) {
        
        int wid = Interface.getInterface().getWidth();
        int hei = Interface.getInterface().getHeight();
        
        g.setColor(Color.BLACK);
        g.fillRect(150, hei-162, 10, 37);
        g.fillRect(100, hei-160, 65, 15);
        
        if(ammoCount >= 0){
            String counter = ammoCount + " / " + spareAmmo;
            g.setFont(new Font("Courier New", Font.PLAIN, 30));
            g.drawString(counter, 80, hei - 80);
        }
    }
    
    public void giveAmmo(int numBullets) {
        if(spareAmmo >= 0)
            spareAmmo += numBullets;
    }
    
    
}
