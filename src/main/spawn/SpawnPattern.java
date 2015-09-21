/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.spawn;

import world.WorldManager;

/**
 *
 * @author Christopher Hittner
 */
public abstract class SpawnPattern {
    
    public abstract void cycle(double time);


    public abstract boolean roundOver();
}
