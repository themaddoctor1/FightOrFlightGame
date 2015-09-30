/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.Controller;
import gui.Interface3D;
import physics.*;
import world.WorldManager;
import world.entities.*;
import world.entities.creatures.*;

/**
 *
 * @author Christopher
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Interface3D.initialize("Fight Or Flight");
        
        WorldManager.getWorld().getEntities().add(Controller.getPlayer());
        
        //WorldManager.getWorld().getEntities().add(new HoverDrone(new Coordinate(10,1,0)));
        
        int enemies = 25;
        for(int i = 0; i < enemies; i++){
            //WorldManager.getWorld().getEntities().add(new FistFighter(new Coordinate(5*Math.sqrt(enemies)*Math.cos(2*Math.PI*i/enemies),1,5*Math.sqrt(enemies)*Math.sin(2*Math.PI*i/enemies))));
            //WorldManager.getWorld().getEntities().add(new Missile(new Coordinate(5*Math.sqrt(enemies)*Math.cos(2*Math.PI*i/enemies),100,5*Math.sqrt(enemies)*Math.sin(2*Math.PI*i/enemies)), new Vector(0.01, 0, -Math.PI/2.0), Controller.getPlayer().getPosition(), 2));
            //WorldManager.getWorld().getEntities().add(new Missile(new Coordinate(5*Math.sqrt(enemies)*Math.cos(2*Math.PI*i/enemies),10,5*Math.sqrt(enemies)*Math.sin(2*Math.PI*i/enemies)), new Vector(5, 0, Math.PI/2.0), Controller.getPlayer().getPosition(), 2));
        }        
        //WorldManager.getWorld().getEntities().add(new Gunman(new Coordinate(10,10,-5)));
        //WorldManager.getWorld().getEntities().add(new Explosion(new Coordinate(0,0,0), 100000));
        //WorldManager.getWorld().getEntities().add(new Missile(new Coordinate(0,100,0), new Vector(0.01, 0, -Math.PI/2.0), Controller.getPlayer().getPosition(), 2));
        
        WorldManager.startSimulation();
        
        while(true)
            Interface3D.getInterface3D().redraw();
        
    }
    
}
