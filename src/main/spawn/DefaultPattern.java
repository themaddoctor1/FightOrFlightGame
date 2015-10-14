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
public class DefaultPattern extends SpawnPattern{
    
    private final int count;
    private int spawned = 0;
    private boolean FIST, GUN, DRONE, ENHANCED;
    
    
    public DefaultPattern(int enemies, boolean fist, boolean gun, boolean drone, boolean powerEnemies){
        count = enemies;
        FIST = fist;
        GUN = gun;
        DRONE = drone;
        ENHANCED = powerEnemies;
    }

    public DefaultPattern(int enemyCount) {
        this(enemyCount, true, true, true, true);
    }
    
    @Override
    public void cycle(double time) {
        int enemyCount = WorldManager.getWorld().enemyCount();
        
        for(int i = 0; i < MAX_ENEMIES-enemyCount && spawned < count; i++){
            spawned++;
            double theta = 2*Math.PI * Math.random();
            double dist = 10 + 20*Math.random();
            Vector v = new Vector(dist, theta, 0);
            Coordinate c = new Coordinate(Controller.getPlayer().getPosition().X(), 1, Controller.getPlayer().getPosition().Z());
            c.addVector(v);
            
            int rand = (int)(4 * Math.random());
            
            if(rand == 0 && FIST)
                WorldManager.getWorld().getEntities().add(new FistFighter(c));
            else if(rand == 1 && GUN){
                double randNum = (Math.random()*100);
                    WorldManager.getWorld().getEntities().add(new Gunman(c));
            }else if(rand == 2 && DRONE){
                int type = (int)(2*Math.random());
                c.addVector(new Vector(20-1,0,Math.PI/2.0));
                if(type == 0)
                    WorldManager.getWorld().getEntities().add(new HoverDrone(c));
                else
                    WorldManager.getWorld().getEntities().add(new DrainDrone(c));
            } else if(rand == 3 && ENHANCED) {
                WorldManager.getWorld().getEntities().add(new GhostGunman(c));
            } else {
                spawned--;
            }
        }
        
    }
    
    @Override
    public boolean roundOver(){
        return WorldManager.getWorld().enemyCount() <= 0 && spawned >= count;
    }

}
