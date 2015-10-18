/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.entities.creatures;

import gui.Controller;
import gui.Interface3D;
import items.Fist;
import items.Gun;
import items.Weapon;
import java.util.ArrayList;
import main.Properties;
import main.Scoreboard;

/**
 *
 * @author Christopher
 */
public class Player extends Speedster{
    private ArrayList<Weapon> weapons;
    
    public Player() {
        super(100);
        weapons = new ArrayList<>();
        Weapon[] add = {
            new Fist(10, 2),
            new Gun(3, 8, 1, true)
        };
        
        for(int i = 0; i < add.length; i++)
            weapons.add(add[i]);
        
        weapon = add[0];
        
        this.chargeCapacity = 0;
    }
    
    @Override
    protected void cycle(double time){
        
        boolean accelerating = false;
        for(int i = 0; !accelerating && i < 8; i++)
            accelerating |= Interface3D.getInterface3D().getController().getState(i);
        
        Scoreboard.modXP(time*Math.sqrt(Math.log10(getSpeedWarp() + Math.pow(getVelocity().getMagnitude(), 1.5)))*0.1);
        
        if(getPosition().Y() <= getSize() && accelerating && Properties.REQUIRE_SPEED_CHARGE){
            double chargeDecrease = 
                    10*time*getSpeedWarp() / Math.sqrt(1+getChargeCapacity());
                    //(time*getSpeedWarp())*Math.pow(10,3)*Math.log10(1+10*Math.pow(getChargeCapacity(),2.5)/(Math.pow(getChargeCapacity(),2)+10)) * Math.max(Math.pow((velocity.getMagnitude()) / getSpeedLimit(), 3), Math.cbrt(velocity.getMagnitude()) / getSpeedLimit());
            
            if(getVelocity().getMagnitude() > 3)
                speedChargeRegenTimer = 0;
            
            charge -= chargeDecrease;
        }
        super.cycle(time);
    }
    
    
    @Override
    public double faceXZ() {
        return Controller.getCamera().getXZ();
    }

    @Override
    public double faceY() {
        return Controller.getCamera().getY();
    }
    
    @Override
    public double maxHealth(){
        return super.maxHealth() * Math.pow(1.5, hpLevel) + 10*Math.pow(hpLevel, 1.5);
    }

    private int capacitanceLevel = 0;
    public int capacitanceUpgradeLevel(){
        return capacitanceLevel;
    }
    
    public void levelUpCapacitance(){ capacitanceLevel++; }
    
    @Override
    public double maxCharge(){
        return super.maxCharge()*Math.pow(1.1, capacitanceLevel);
    }
    
    private int hpLevel = 0;
    public void levelUpHP(){
        hpLevel++;
        healthRegenTimer = 5;
        modHealth(maxHealth());
        
    }
    public int getHpLevel(){ return hpLevel; }

    public void levelUpSpeed() {
        chargeCapacity *= 1.1;
    }
    
    public ArrayList<Weapon> getWeapons(){
        return weapons;
    }

    public void replaceWeapon(int i, Weapon result) {
        weapons.remove(i);
        weapons.add(i, result);
    }
    
    
    private boolean velocityCompassActive = false;
    public void setVelocityCompassState(boolean state){ velocityCompassActive = state; }
    public boolean velocityCompass(){ return velocityCompassActive; }
    
    private boolean shootingMoveCompActive = false;
    public void setShootingMoveCompState(boolean state){ shootingMoveCompActive = state; }
    public boolean shootingMoveComp(){ return shootingMoveCompActive; }
    
    
}
