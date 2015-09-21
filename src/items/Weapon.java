/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import gui.Camera;
import java.awt.Graphics;
import world.entities.Entity;

/**
 *
 * @author Christopher
 */
public abstract class Weapon {
    
    public abstract void cycle(double time, Entity user);
    
    public final void use(double time, Entity user, Object... params){
        if(canUse(time, user, params))
            execute(time, user, params);
    }
    
    public abstract boolean canUse(double time, Entity user, Object... params);
    protected abstract void execute(double time, Entity user, Object... params);
    public abstract void drawPerspective(Graphics g);
    
}
