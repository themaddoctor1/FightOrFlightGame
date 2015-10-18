/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import gui.Controller;
import main.spawn.*;
import world.World;
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
    
    private static double XP = 0;
    
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
        
        int startSpecWave = 13;
        int specWave = (int)((9.0)*Math.random());
        
        /*if(wave%10 == 0)
            return new SpeedsterPattern();*/
        if(wave <= 3){
            return new DefaultPattern(enemyCount, true, false, false, false);
        } else if(wave <= 7){
            return new DefaultPattern(enemyCount, true, true, false, false);
        } else if(wave <= 10){
            return new DefaultPattern(enemyCount, true, true, true, false);
        } else if(specWave == 0 && wave >= startSpecWave){
            return new EnhancedEnemyPattern(enemyCount);  //Enhanced Enemy Wave
        } else if(specWave == 1 && wave >= startSpecWave){
            return new DroneStrikePattern(enemyCount);  //Death From Above
        } else if(specWave == 2 && wave >= startSpecWave){
            return new SpeedsterPattern();  //Superspeed Enemies
        }
        
        return new DefaultPattern(enemyCount);
    }
    
    public static void ready(){
        modXP(0.5*(wave + Math.pow(wave++,2)));
        countdown = 5;
    }

    public static void startGame() {
        WorldManager.stopSimulation();
        WorldManager.setWorld(new World());
        Controller.setPlayer(new Player());
        WorldManager.getWorld().getEntities().add(Controller.getPlayer());
        running = true;
        countdown = 5;
        wave = 1;
        XP = 0;
        WorldManager.startSimulation();
    }
    
    public static void endGame(){
        running = false;
        WorldManager.stopSimulation();
    }
    
    public static void resumeGame(){
        running = true;
        WorldManager.startSimulation();
    }
    
    public static void pauseGame() {
        running = false;
        WorldManager.stopSimulation();
    }
    
    public static void modXP(double amt){
        XP += amt;
    }
    
    public static double XP() {
        return XP;
    }
    
}
