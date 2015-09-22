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

/**
 *
 * @author Christopher
 */
public class MissileStrikePattern extends SpawnPattern {
    
    private int count;
    private int spawned = 0;
    
    
    public MissileStrikePattern(int missiles){
        count = missiles;
    }
    
    @Override
    public void cycle(double time) {
        int enemyCount = WorldManager.getWorld().enemyCount();
        
        for(int i = 0; i < MAX_ENEMIES-enemyCount && spawned < count; i++){
            spawned++;
            double theta = 2*Math.PI * Math.random();
            Vector v = new Vector(20, theta, 0);
            Coordinate c = new Coordinate(Controller.getPlayer().getPosition().X(), 100, Controller.getPlayer().getPosition().Z());
            c.addVector(v);
            
            double ang = (2*Math.PI * Math.random());
            double dist = 10 + 40 * Math.random();
            
            c.addVector(new Vector(50, ang, 0));
            
            WorldManager.getWorld().getEntities().add(new Missile(c, new Vector(new Vector(c, Controller.getPlayer().getPosition()).unitVector(), 1), Controller.getPlayer().getPosition(), 3));
        }
        
    }
    
    @Override
    public boolean roundOver(){
        for(int i = 0; i < WorldManager.getWorld().getEntities().size(); i++)
            if(WorldManager.getWorld().getEntities().get(i) instanceof Missile)
                return false;
        return true;
    }
}
