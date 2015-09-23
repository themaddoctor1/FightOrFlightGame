/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.spawn;

import gui.Controller;
import static main.spawn.SpawnPattern.MAX_ENEMIES;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;
import world.entities.Missile;
import world.entities.creatures.HostileSpeedster;

/**
 *
 * @author Christopher
 */
public class SpeedsterPattern extends SpawnPattern {
    private boolean spawned = false;
    
    
    public SpeedsterPattern(){
        
    }
    
    @Override
    public void cycle(double time) {
        
        if(!spawned){
            spawned = true;
            double theta = 2.0*Math.PI * Math.random();
            double dist = 10.0 + 20.0 * Math.random();
            Vector v = new Vector(dist, theta, 0);
            Coordinate c = new Coordinate(Controller.getPlayer().getPosition().X(), 1, Controller.getPlayer().getPosition().Z());
            
            c.addVector(v);
            
            WorldManager.getWorld().getEntities().add(new HostileSpeedster(c));
        }
        
    }
    
    @Override
    public boolean roundOver(){
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++)
            if(WorldManager.getWorld().getEntities().get(i) instanceof HostileSpeedster)
                return false;
        return true;
    }
}
