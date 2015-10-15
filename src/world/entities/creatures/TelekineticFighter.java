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
    
    private final boolean THROW_PLAYER = false;
    private final boolean STOP_BULLETS = true;
    
    public TelekineticFighter(Coordinate pos){
        super(pos,1,25);
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
        
        double force = 12;
        
        if(energy > 0 && active && relDist < 20 && THROW_PLAYER){
            
            double vertAng = 0;
            
            if(p.getPosition().Y() < 2 || (p.getVelocity().getAngleY() > 0 && p.getPosition().Y() < 10))
                vertAng = Math.PI/2.0 + 0.2 * (Math.random()-0.5);
            else {
                vertAng = -Math.PI/2.0;
                force *= Math.sqrt(1 + Math.pow(energy/MAX_ENERGY,2));
            }
            
            p.getVelocity().addVectorToThis(new Vector(force*time, new Vector(getPosition(), p.getPosition()).getAngleXZ(), vertAng));
            energy -= 50*time;
            
            
            
        } else if(active) {
            active = false;
        } else {
            energy += 50 * time;
        }
        
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
        
        double speedDrain = drainMultiplier
                * Math.sqrt(1+Controller.getPlayer().getChargeCapacity())
                * time
                * Controller.getPlayer().getSpeedWarp()
                * Math.max(Math.pow(Controller.getPlayer().getChargeCapacity(), 3), Math.cbrt(Controller.getPlayer().getChargeCapacity())) 
                * Math.sqrt(1+Math.pow(Controller.getPlayer().getCharge(),2)) 
                / Math.pow(Coordinate.relativeDistance(Controller.getPlayer().getPosition(), getPosition()), 2);

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
        if(STOP_BULLETS && THROW_PLAYER)
            worth += 14;
        else if(STOP_BULLETS)
            worth += 4;
        else if(THROW_PLAYER)
            worth += 4;
        Scoreboard.modXP(Math.log10(worth*Scoreboard.wave()));
        super.killSelf();
    }
    
    
}
