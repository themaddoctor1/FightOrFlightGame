/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import main.spawn.*;

/**
 *
 * @author Christopher Hittner
 */
public class Scoreboard {
    private static double countdown = 5;
    private static int wave = 1;
    private static SpawnPattern spawning = null;
    
    public static int wave(){ return wave; }
    
    public static double  timer(){ return countdown; }
    
    
    public static void cycle(double time){
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
        int enemyCount = (int)Math.pow(4+2*wave, 0.75);
        if(wave <= 2){
            return new FistFighterPattern(enemyCount);
        } else {
            return new HybridPattern(enemyCount);
        }
    }
    
    public static void ready(){
        countdown = 5;
        wave++;
    }
    
}
