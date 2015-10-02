/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Camera;
import gui.Interface3D;
import gui.shapes.Polygon3D;
import items.Weapon;
import java.awt.Graphics;
import physics.Coordinate;
import world.entities.Entity;
import world.entities.HasHealth;

/**
 *
 * @author Christopher
 */
public abstract class Creature extends Entity implements HasHealth{
    
    private final double MAX_HP;
    private double HP = 100;
    
    protected Weapon weapon = null;
    
    public Creature(double s, double hp) {
        this(new Coordinate(0,0,0), s, hp);
    }
    
    public Creature(Coordinate c, double s, double hp) {
        super(c, s);
        MAX_HP = (HP = hp);
    }
    
    @Override
    protected void cycle(double time){
        if(weapon != null)
            weapon.cycle(time, this);
    }
    
    @Override
    public double getHealth() {
        return HP;
    }

    @Override
    public double maxHealth() {
        return MAX_HP;
    }

    @Override
    public void modHealth(double amt) {
        HP = Math.max(0, Math.min(maxHealth(), HP+amt));
    }

    @Override
    public void setHealth(double amt) {
        modHealth(amt-getHealth());
    }
    
    public Weapon getWeapon(){ return weapon; }
    public void setWeapon(Weapon w){ weapon = w; }
    
    public abstract double faceXZ();
    public abstract double faceY();
    
}
