/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.spawn;

import gui.Controller;
import physics.*;
import world.WorldManager;
import world.entities.creatures.FistFighter;

/**
 *
 * @author Christopher Hittner
 */
public class FistFighterPattern extends SpawnPattern{
    
    int count;
    int spawned = 0;
    
    public FistFighterPattern(int enemies){
        count = enemies;
    }
    
    @Override
    public void cycle(double time) {
        int enemyCount = WorldManager.getWorld().enemyCount();
        
        for(int i = 0; i < MAX_ENEMIES-enemyCount && spawned < count; i++){
            spawned++;
            double theta = 2*Math.PI * Math.random();
            Vector v = new Vector(20, theta, 0);
            Coordinate c = new Coordinate(Controller.getPlayer().getPosition().X(), 1, Controller.getPlayer().getPosition().Z());
            c.addVector(v);
            WorldManager.getWorld().getEntities().add(new FistFighter(c));
        }
        
    }
    
    @Override
    public boolean roundOver(){
        return WorldManager.getWorld().enemyCount() <= 0 && spawned >= count;
    }

}
