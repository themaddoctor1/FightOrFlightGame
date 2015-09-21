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
    private static SpawnPattern spawning;
    
    public int wave(){ return wave; }
    
    public double  timer(){ return countdown; }
    
    
    public void cycle(double time){
        if(countdown <= 0)
            spawning.cycle(time);
        else{
            if (spawning != null)
                spawning = null;
            countdown -= time;
        }
    }
    
    public void ready(){
        spawning = new FistFighterPattern((int)Math.pow(wave, 0.75));
    }
    
}
