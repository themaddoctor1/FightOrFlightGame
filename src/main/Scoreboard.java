/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import gui.Controller;
import main.spawn.*;
import world.WorldManager;
import world.entities.creatures.Player;

/**
 *
 * @author Christopher Hittner
 */
public class Scoreboard {
    private static double countdown = 5;
    private static int wave = 1;
    private static SpawnPattern spawning = null;
    private static boolean running = false;
    
    public static int wave(){ return wave; }
    
    public static double  timer(){ return countdown; }
    
    
    public static void cycle(double time){
        
        if(!running)
            return;
        
        if(countdown <= 0){
            if(spawning == null)
                spawning = nextSpawner();
            spawning.cycle(time);
        }else{
            if (spawning != null)
                spawning = null;
            
            countdown -= time;
        }
        
        if(spawning != null) if(spawning.roundOver() && countdown <= 0)
            ready();
        
    }
    
    public static SpawnPattern nextSpawner(){
        int enemyCount = //(int)Math.pow(4+2*wave, 0.75);
                (int)(4*wave / Math.pow(Math.log10(wave+10),4));
        
        /*if(wave%10 == 0)
            return new SpeedsterPattern();*/
        if(wave <= 3){
            return new DefaultPattern(enemyCount, true, false, false);
        } else if(wave <= 7){
            return new DefaultPattern(enemyCount, true, true, false);
        } else {
            return new DefaultPattern(enemyCount, true, true, true);
        }
    }
    
    public static void ready(){
        countdown = 5;
        wave++;
    }

    public static void startGame() {
        WorldManager.stopSimulation();
        WorldManager.getWorld().getEntities().clear();
        Controller.setPlayer(new Player());
        WorldManager.getWorld().getEntities().add(Controller.getPlayer());
        running = true;
        countdown = 5;
        wave = 1;
        WorldManager.startSimulation();
    }
    
    public static void endGame(){
        running = false;
        WorldManager.stopSimulation();
    }
    
}
