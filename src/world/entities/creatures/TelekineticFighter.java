/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Controller;
import items.Fist;
import items.Gun;
import java.util.ArrayList;
import main.Scoreboard;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.Bullet;
import world.entities.Entity;

/**
 *
 * @author Christopher
 */
public class TelekineticFighter extends Humanoid{
    
    private final double MAX_ENERGY = 100;
    private double energy;
    private boolean active = false;
    private final boolean STOP_BULLETS = true;
    
    public TelekineticFighter(Coordinate pos){
        super(pos,1,20*Math.log10(10+Scoreboard.wave()));
        weapon = new Fist(10, 1);
        energy = MAX_ENERGY*(1-1/(Controller.getPlayer().getChargeCapacity()+1));
    }

    @Override
    protected void cycle(double time) {
        
        super.cycle(time);
        
        if(getPosition().Y() <= getSize()){
            Vector vel = new Vector((new Vector(getPosition(), Controller.getPlayer().getPosition())).unitVector(), 3);
            vel.addVectorToThis(new Vector(velocity.getMagnitudeY(), 0, Math.PI/2.0));
            this.velocity = vel;
        }
        
        if(energy >= MAX_ENERGY)
            active = true;
        
        
        Player p = Controller.getPlayer();
        
        double relDist = Coordinate.relativeDistance(pos, p.getPosition());
        
        if(energy > 0 && active && relDist < 20){
            
            double forceFactor = Math.sqrt(1/(Math.log((Math.sqrt(1+Coordinate.relativeDistance(p.getPosition(), pos)))))/2.0);
            
            System.out.println(forceFactor);
            
            //Stops the player
            Vector playerStop = new Vector(p.getVelocity().unitVector(), -0.9*time*p.getAcceleration() * forceFactor);
            playerStop = new Vector(playerStop.getMagnitudeXZ(), playerStop.getAngleXZ(), 0);
            p.getVelocity().addVectorToThis(playerStop);

            //Suspends the player in midair
            p.getVelocity().addVectorToThis(new Vector(9.81*time*forceFactor, 0, Math.PI/2.0));

            double alt = 0.5 + p.getSize();

            double altDisp = p.getPosition().Y() - alt;

            double vYdesired = -Math.sqrt(10*Math.abs(altDisp))*Math.signum(altDisp);

            if(p.getVelocity().getMagnitudeY() < vYdesired)
                p.getVelocity().addVectorToThis(new Vector(10*time * forceFactor, 0, Math.PI/2.0));
            else if(p.getVelocity().getMagnitudeY() > vYdesired)
                p.getVelocity().addVectorToThis(new Vector(-10*time * forceFactor, 0, Math.PI/2.0));
            
            energy -= 40*time;
            
        } else if(active && energy < 0) {
            active = false;
        } else {
            energy += 75 * time;
        }
        
        System.out.println("Energy: " + energy);
        
        
        
        
        if(STOP_BULLETS){
            ArrayList<Entity> bullets = new ArrayList<>(), allEntities = WorldManager.getWorld().getEntities();
            
            for(int i = 0; i < allEntities.size(); i++){
                Entity e = allEntities.get(i);
                if(e instanceof Bullet)
                    bullets.add(e);
            }
            
            for(int i = 0; i < bullets.size(); i++){
                Bullet b = (Bullet) bullets.get(i);
                Vector away = ((new Vector(getPosition(), b.getPosition())).unitVector());
                Vector relVel = new Vector(b.getVelocity(), 1);
                
                Vector atPlayer = new Vector(new Vector(b.getPosition(), p.getPosition()).unitVector(), 7500 * time);
                
                if(Vector.cosOfAngleBetween(away, relVel) < -0.4){
                
                    relVel.addVectorToThis(new Vector(getVelocity(), -1));

                    b.getVelocity().addVectorToThis(new Vector(away, 7500.0 * time * Math.max(0, -Vector.cosOfAngleBetween(away, relVel))));
                
                }else if(Vector.cosOfAngleBetween(away, atPlayer) >= 0)
                    b.getVelocity().addVectorToThis(atPlayer);
                
            }
            
        }
        
        if(Coordinate.relativeDistance(getPosition(), Controller.getPlayer().getPosition()) <= 1.01){
            weapon.use(time, this, Controller.getPlayer());
            this.velocity = new Vector(Controller.getPlayer().getVelocity().unitVector(), Math.min(Controller.getPlayer().getVelocity().getMagnitude(), 3));
        }
        
        double drainMultiplier = 10 * 0;
        
        double speedDrain = drainMultiplier*Math.sqrt(1+p.getChargeCapacity())*time*p.getSpeedWarp()*p.chargeRate()*Math.max(0, Math.min(p.speedChargeRegenTimer()-1, 2));
        
        Controller.getPlayer().modCharge(-Math.abs(speedDrain));
        
        energy += Math.pow((100*p.getChargeCapacity()/relDist), 2);
        
        energy = Math.min(energy, MAX_ENERGY);
        
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
        int worth = 1;
        if(STOP_BULLETS)
            worth += 4;
        Scoreboard.modXP(Math.log10(worth*Scoreboard.wave()));
        super.killSelf();
    }
    
    
}
