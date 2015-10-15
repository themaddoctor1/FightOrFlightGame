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
public class EnhancedEnemyPattern extends SpawnPattern{
    
    private final int count;
    private int spawned = 0;
    private boolean GHOSTS, TELEKINETIC;
    
    
    public EnhancedEnemyPattern(int enemies, boolean ghost, boolean tk){
        count = enemies;
        GHOSTS = ghost;
        TELEKINETIC = tk;
    }

    public EnhancedEnemyPattern(int enemyCount) {
        this(enemyCount, true, true);
    }
    
    @Override
    public void cycle(double time) {
        int enemyCount = WorldManager.getWorld().enemyCount();
        
        for(int i = 0; i < (MAX_ENEMIES)-enemyCount && spawned < count; i++){
            spawned++;
            double theta = 2*Math.PI * Math.random();
            double dist = 25 + 50*Math.random();
            Vector v = new Vector(dist, theta, 0);
            Coordinate c = new Coordinate(Controller.getPlayer().getPosition().X(), 1, Controller.getPlayer().getPosition().Z());
            c.addVector(v);
            
            int rand = (int)(2 * Math.random());
            
            if(rand == 0 && TELEKINETIC)
                WorldManager.getWorld().getEntities().add(new TelekineticFighter(c));
            else if(rand == 1 && GHOSTS){
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
