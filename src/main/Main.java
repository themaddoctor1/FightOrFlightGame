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
        
        //WorldManager.getWorld().getEntities().add(Controller.getPlayer());
        
        //WorldManager.startSimulation();
        
        while(true)
            Interface3D.getInterface3D().redraw();
        
    }
    
}
