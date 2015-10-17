/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.spawn;

import gui.Controller;
import physics.*;
import world.WorldManager;
import world.entities.creatures.*;

/**
 *
 * @author Christopher Hittner
 */
public class DroneStrikePattern extends SpawnPattern{
    
    private final int count;
    private int spawned = 0;
    
    
    public DroneStrikePattern(int enemies){
        count = enemies;
    }
    
    @Override
    public void cycle(double time) {
        int enemyCount = WorldManager.getWorld().enemyCount();
        
        for(int i = 0; i < 2*MAX_ENEMIES-enemyCount && spawned < count; i++){
            spawned++;
            double theta = 2*Math.PI * Math.random();
            double dist = 10 + 20*Math.random();
            Vector v = new Vector(dist, theta, 0);
            Coordinate c = new Coordinate(Controller.getPlayer().getPosition().X(), 100, Controller.getPlayer().getPosition().Z());
            c.addVector(v);
            
            int type = (int)(3*Math.random());
            
            if(type == 0)
                WorldManager.getWorld().getEntities().add(new HoverDrone(c));
            else if(type == 1)
                WorldManager.getWorld().getEntities().add(new DrainDrone(c));
            else if(type == 2)
                WorldManager.getWorld().getEntities().add(new HealthDrainDrone(c));
            else
                spawned--;
            
        }
        
    }
    
    @Override
    public boolean roundOver(){
        return WorldManager.getWorld().enemyCount() <= 0 && spawned >= count;
    }

}
